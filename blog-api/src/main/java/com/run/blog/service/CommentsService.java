package com.run.blog.service;

import com.run.blog.vo.Result;
import com.run.blog.vo.params.CommentParam;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
public interface CommentsService {

    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);

}
