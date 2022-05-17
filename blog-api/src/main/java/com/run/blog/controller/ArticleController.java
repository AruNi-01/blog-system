package com.run.blog.controller;

import com.run.blog.common.cache.Cache;
import com.run.blog.common.log.Log;
import com.run.blog.service.ArticleService;
import com.run.blog.vo.Result;
import com.run.blog.vo.params.ArticleParam;
import com.run.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    @Log(module = "文章", operator = "获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams) {
        return articleService.listArticle(pageParams);
    }

    /**
     * 通过文章id获取文章，编辑功能需要
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    public Result getArticleById(@PathVariable("id") Long id) {
        return articleService.getArticleById(id);
    }

    /**
     * 首页 文章归档
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives() {
        return articleService.listArchives();
    }

    /**
     * 首页 最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticle() {
        int limit = 3;
        return articleService.newArticle(limit);
    }

    /**
     * 首页 最热文章
     * @return
     */
    // @Cache(expire = 5 * 60 * 1000, name = "hot_article")     // 将最热文章存入缓存，过期时间为6min
    @PostMapping("hot")
    public Result hotArticle() {
        int limit = 5;
        return articleService.hotArticle(limit);
    }


    /**
     * 查看文章详情
     * @param articleId
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long articleId) {
        return articleService.findArticleById(articleId);
    }

    /**
     * 文章发布
     * @param articleParam
     * @return
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return articleService.publish(articleParam);
    }


    /**
     * 通过文章标题搜索文章
     * @param articleTitle
     * @return
     */
    @PostMapping("search")
    public Result searchArticle(@RequestParam("title") String articleTitle) {
        return articleService.searchArticleByTitle(articleTitle);
    }

}
