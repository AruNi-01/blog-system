package com.run.blog.service;

import com.run.blog.vo.Result;
import com.run.blog.vo.TagVo;

import java.util.List;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    Result findAll();

    Result finAllDetail();

    Result findDetailById(Long id);
}
