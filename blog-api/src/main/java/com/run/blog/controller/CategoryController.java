package com.run.blog.controller;

import com.run.blog.service.CategoryService;
import com.run.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;

/**
 * @author AruNi_Lu
 * @data 2022/4/12
 */
@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 在发布文章时候需要文章分类的id和name
     * @return
     */
    @GetMapping
    public Result categories() {
        return categoryService.findAll();
    }

    /**
     * 导航栏--文章分类
     * @return
     */
    @GetMapping("detail")
    public Result categoriesDetail() {
        return categoryService.findAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result categoryDetailById(@PathVariable("id") Long id) {
        return categoryService.categoryDetailById(id);
    }
}
