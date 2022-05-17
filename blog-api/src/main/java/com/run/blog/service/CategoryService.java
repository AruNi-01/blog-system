package com.run.blog.service;

import com.run.blog.vo.CategoryVo;
import com.run.blog.vo.Result;

import java.util.List;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
