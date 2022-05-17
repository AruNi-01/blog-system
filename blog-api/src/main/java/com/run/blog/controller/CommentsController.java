package com.run.blog.controller;

import com.run.blog.service.CommentsService;
import com.run.blog.vo.Result;
import com.run.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@RestController
@RequestMapping("comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long articleId) {
        return commentsService.commentsByArticleId(articleId);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam) {
        return commentsService.comment(commentParam);
    }

}
