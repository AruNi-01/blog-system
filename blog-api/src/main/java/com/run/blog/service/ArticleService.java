package com.run.blog.service;

import com.run.blog.vo.Result;
import com.run.blog.vo.params.ArticleParam;
import com.run.blog.vo.params.PageParams;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
public interface ArticleService {

    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    Result listArchives();

    Result findArticleById(Long articleId);

    Result publish(ArticleParam articleParam);

    Result searchArticleByTitle(String articleTitle);

    Result getArticleById(Long id);
}
