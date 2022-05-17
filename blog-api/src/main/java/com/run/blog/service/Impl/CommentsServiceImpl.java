package com.run.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.run.blog.dao.mapper.ArticleMapper;
import com.run.blog.dao.mapper.CommentMapper;
import com.run.blog.dao.pojo.Article;
import com.run.blog.dao.pojo.Comment;
import com.run.blog.dao.pojo.SysUser;
import com.run.blog.service.ArticleService;
import com.run.blog.service.CommentsService;
import com.run.blog.service.SysUserService;
import com.run.blog.utils.UserThreadLocal;
import com.run.blog.vo.CommentVo;
import com.run.blog.vo.Result;
import com.run.blog.vo.UserVo;
import com.run.blog.vo.params.CommentParam;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ParameterMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ThreadPoolService threadPoolService;




    /**
     * 添加评论功能
     * @param commentParam
     * @return
     */
    @Override
    public Result comment(CommentParam commentParam) {
        // 通过ThreadLocal直接获取该用户信息
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());

        Long parent = commentParam.getParent();
        // 如果父id为空，则父评论，否则子评论
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);

        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        commentMapper.insert(comment);

        // 更新评论数量
        Long articleId = commentParam.getArticleId();
        Article article = articleMapper.selectById(articleId);
        threadPoolService.updateArticleCommentCount(articleMapper, article);

        return Result.success(null);
    }

    /**
     * 通过文章id查询评论
     * @param articleId
     * @return
     */
    @Override
    public Result commentsByArticleId(Long articleId) {
         /**
          * 1.根据文章id查询评论列表,从 comment 中查询
          * 2.根据作者id查询作者信息
          * 3.如果 level=1,查询有没有子评论
          * 4.如果有  根据评论id进行查询
         */
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, articleId);
        // 先查询出等级为1的评论，再根据评论是否有子评论在后面进行二次查询
        queryWrapper.eq(Comment::getLevel, 1);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        List<CommentVo> commentVoList = copyList(comments);
        return Result.success(commentVoList);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentVoList.add(copy(comment));
        }
        return commentVoList;
    }

    private CommentVo copy(Comment comment) {
        CommentVo commentVo = new CommentVo();
        // 拷贝相同类型的属性到commentVo中
        BeanUtils.copyProperties(comment, commentVo);
        // 时间格式化
        commentVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        // 作者信息
        Long authorId = comment.getAuthorId();
        UserVo userVo = sysUserService.findUserVoById(authorId);
        // author的类型就是UserVo
        commentVo.setAuthor(userVo);

        // 子评论
        Integer level = comment.getLevel();
        // level=1 才有子评论
        if (level == 1) {
            Long id = comment.getId();
            // 通过父评论id获取所有子评论id
            List<CommentVo> commentVoList = findCommentsByParentId(id);
            commentVo.setChildren(commentVoList);
        }

        //toUser 向谁评论
        if (level > 1) {
            Long toUid = comment.getToUid();
            UserVo toUserVo = sysUserService.findUserVoById(toUid);
            commentVo.setToUser(toUserVo);
        }

        return commentVo;
    }

    // 子评论查询
    private List<CommentVo> findCommentsByParentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, 2);

        return copyList(commentMapper.selectList(queryWrapper));
    }
}
