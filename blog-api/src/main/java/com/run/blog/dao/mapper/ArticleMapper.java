package com.run.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.run.blog.dao.dos.Archives;
import com.run.blog.dao.pojo.Article;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();

    // article分页列表查询，自己实现，不使用MP的
    IPage<Article> listArticle(
            Page<Article> page,
            Long categoryId,
            Long tagId,
            String year,
            String month);

}
