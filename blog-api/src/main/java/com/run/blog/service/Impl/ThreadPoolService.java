package com.run.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.run.blog.dao.mapper.ArticleMapper;
import com.run.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@Component
public class ThreadPoolService {

    // 期望此操作在线程池中执行 不会影响主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        // 重新new一个Article对象，更新的时候只用扔一个带有viewCounts的对象进参数一
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);

        LambdaQueryWrapper<Article> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        // 乐观锁，在修改的时候查询一下当前的viewCounts与数据库中的是否一样
        // 若被其他线程抢先改动了，则此线程修改失败，等待下一次修改
        updateWrapper.eq(Article::getViewCounts, viewCounts);
        // SQL: update article set view_count = 100 where view_count =99 and id = 1
        articleMapper.update(articleUpdate, updateWrapper);

        try {
            Thread.sleep(2000);
            System.out.println("浏览量更新完成！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Async("taskExecutor")
    public void updateArticleCommentCount(ArticleMapper articleMapper, Article article) {
        int commentCounts = article.getCommentCounts();
        Article articleUpdate = new Article();
        articleUpdate.setCommentCounts(commentCounts + 1);

        LambdaQueryWrapper<Article> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        // 乐观锁，在修改的时候查询一下当前的viewCounts与数据库中的是否一样
        // 若被其他线程抢先改动了，则此线程修改失败，等待下一次修改
        updateWrapper.eq(Article::getCommentCounts, commentCounts);
        // SQL: update article set view_count = 100 where view_count =99 and id = 1
        articleMapper.update(articleUpdate, updateWrapper);

        try {
            Thread.sleep(2000);
            System.out.println("评论数量更新完成！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
