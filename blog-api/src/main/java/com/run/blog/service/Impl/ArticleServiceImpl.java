package com.run.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.blog.dao.dos.Archives;
import com.run.blog.dao.mapper.ArticleBodyMapper;
import com.run.blog.dao.mapper.ArticleMapper;
import com.run.blog.dao.mapper.ArticleTagMapper;
import com.run.blog.dao.pojo.*;
import com.run.blog.service.ArticleService;
import com.run.blog.service.CategoryService;
import com.run.blog.service.SysUserService;
import com.run.blog.service.TagService;
import com.run.blog.utils.UserThreadLocal;
import com.run.blog.vo.ArticleBodyVo;
import com.run.blog.vo.ArticleVo;
import com.run.blog.vo.Result;
import com.run.blog.vo.TagVo;
import com.run.blog.vo.params.ArticleParam;
import com.run.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TagService tagService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadPoolService threadPoolService;

    @Autowired
    private ArticleTagMapper articleTagMapper;


    /**
     * 分页查询文章，有文章归档功能后用此方法
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        // 自己写SQL，将所有参数传过去
        IPage<Article> articleIPage = articleMapper.listArticle(
                page,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth());

        List<Article> records = articleIPage.getRecords();
        return Result.success(copyList(records, true, true));
    }

    /**
     * 分页查询文章，没有文章归档功能时用此方法
     * 注意：因为文章归档功能需要查询时间，所以需要自定义SQL，不能使用MP的
     *       所以将此方法注释掉，使用ArticleMapper.xml
     */
//    @Override
//    public Result listArticle(PageParams pageParams) {
//        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//
//        // 动态SQL，满足条件则拼接上: and category_id = #{categoryId}
//        // 因为有些时候是从分类中展示文章列表
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId, pageParams.getCategoryId());
//        }
//
//        // 同上，有时候从tag中展示文章
//        List<Long> articleIdList = new ArrayList<>();
//
//        if (pageParams.getTagId() != null) {
//            // article表中没有tag字段，一篇文章有多个tag
//            // article_tag表中一个articleId对应多个tagId
//            // categoryId在article表中，文章和分类是1对1的关系，所以不用额外添加一个动态表
//            // 为什么不把tagId放入article表中？ 正是因为一篇文章对应多个tag，一个tag对应多篇文章
//            // 多对多的需要单独添加一个动态表
//            LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            articleTagLambdaQueryWrapper.eq(ArticleTag::getTagId, pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
//            // 将属于该标签的所有articleId存入List，用于获取该tag下的所有文章
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() > 0) {
//                // 动态拼接SQL: and articleId in( 1, 2, 3 ...(articleIdList) )
//                // 查询文章id在articleIdList中的文章
//                queryWrapper.in(Article::getId, articleIdList);
//            }
//        }
//
//        // 先按置顶排序(weight为置顶权重)，再按时间倒序排序
//        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);
//
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//        // 不能直接返回该records
//
//        // 将Article转为ArticleVo(与前端数据进行交互)
//        // 因为页面展示出来的数据不一定和数据库一样，所有我们要做一个转换
//        // 将在查出数据库的数组复制到articleVo中实现解耦合,vo和页面数据交互
//        List<ArticleVo> articleVoList = copyList(records, true, true);
//
//        return Result.success(articleVoList);
//    }


    /**
     * 通过文章id获取文章，编辑功能需要
     * @param id
     * @return
     */
    @Override
    public Result getArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        return Result.success(copy(article, true, true, true, true));
    }

    /**
     * 首页 最热文章
     * @param limit
     * @return
     */
    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 只需要根据浏览量倒序排序
        queryWrapper.orderByDesc(Article::getViewCounts);
        // 只需要id和title数据
        queryWrapper.select(Article::getId, Article::getTitle);
        // 在后面加一个limit限制条数
        queryWrapper.last("limit " + limit);

        // SQL: select id,title from article order by view_counts desc limit 5
        List<Article> articles = articleMapper.selectList(queryWrapper);
        // 返回Vo
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 首页 最新文章
     * @param limit
     * @return
     */
    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article>  queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false));
    }

    /**
     * 首页 文章归档
     * @return
     */
    @Override
    public Result listArchives() {
        /*
         * 因为文章归档返回的数据不是数据库的直接数据, 临时使用, 不属于pojo对象
         * 所以创建dos包存放非持久化数据
         */
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    /**
     * 根据id查询文章的内容
     * @param bodyId
     * @return
     */
    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }

    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    @Override
    public Result findArticleById(Long articleId) {
        /**
         * 1. 根据id查看文章信息
         * 2. 根据body_id和category_id做关联查询
         */
        Article article = articleMapper.selectById(articleId);
        // 通过copy函数对文章的body，category进行赋值
        ArticleVo articleVo = copy(article, true, true, true, true);

        // 找到文章内容，代表前端已经在阅读此文章了，需要将阅读数量+1
        // 但是如果在这里做一个更新文章阅读量的操作，更新时会加写锁，阻塞其他的读操作，性能较慢
        // 而且更新时若出现问题，则不能影响其他操作
        // 用线程池解决，可以把更新操作扔到线程池中去执行，和主线程不相干
        threadPoolService.updateArticleViewCount(articleMapper, article);

        return Result.success(articleVo);

    }

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {
        /**
         * 1.发布文章 目的构建Article对象
         * 2. 作者id 当前登陆用户
         * 3. 标签 将标签加入关联表中
         * 4. body内容存储 article bodyId
         * @param articleParam
         * @return
         * 此接口要加入登陆拦截中
         */
        // 写文章必须要用户登录才能获取该用户，是登录拦截器中把User放入到ThreadLocal中的，否则get不到，去WebMvcConfig中添加此url
        SysUser sysUser = UserThreadLocal.get();

        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(0);
        article.setTitle(articleParam.getTitle());
        article.setSummary(articleParam.getSummary());
        article.setCommentCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(articleParam.getCategory().getId());
        // 插入后会自动生成文章id
        articleMapper.insert(article);

        // 设置文章tag，将数据插入到article_tag表中
        List<TagVo> tags = articleParam.getTags();
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articleTag = new ArticleTag();
                articleTag.setTagId(tag.getId());
                articleTag.setArticleId(article.getId());
                articleTagMapper.insert(articleTag);
            }
        }

        // 设置文章body，将数据插入到article_body表中
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        // 将bodyId设置进文章表
        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        // 将id转换为String 放入map(文章发布完成后需要将文章id传给前端，前端根据id展示该文章内容)
        Map<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());

        return Result.success(map);
    }

    /**
     * 通过文章标题搜索文章（模糊查询）
     * @param articleTitle
     * @return
     */
    @Override
    public Result searchArticleByTitle(String articleTitle) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Article::getTitle, articleTitle);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articleList, true, true));
    }

    // 首页的文章信息，带Tag和Author
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, false, false));
        }
        return articleVoList;
    }

    // 重载copyList，文章详情页还需要带有文章文章Body和类型Category
    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record, isTag, isAuthor, isBody, isCategory));
        }
        return articleVoList;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();

        // BeanUtils是Spring提供的拷贝工具类，拷贝Article和articleVo相等的字段
        BeanUtils.copyProperties(article, articleVo);

        // 不相等的字段单独赋值
        // createDate数据类型不一样
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));

        // 并不是所有的接口，都需要标签和作者信息；
        if (isTag) {
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody) {
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory) {
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

}