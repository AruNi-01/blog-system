package com.run.blog.controller;

import com.run.blog.dao.mapper.TagMapper;
import com.run.blog.service.TagService;
import com.run.blog.vo.Result;
import com.run.blog.vo.TagVo;
import javafx.scene.control.Toggle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@RestController
@RequestMapping("tags")
public class TagsController {

    @Autowired
    private TagService tagService;

    /**
     * 首页--最热标签
     * @return
     */
    @GetMapping("/hot")
    public Result hot() {
        // 查询前6个hotTags
        int limit = 6;
        return tagService.hots(limit);
    }

    /**
     * 在发布文章时候需要tag的id和name
     * @return
     */
    @GetMapping
    public Result findAll() {
        return tagService.findAll();
    }

    /**
     * 导航栏--所有标签
     * @return
     */
    @GetMapping("detail")
    public Result findAllDetail() {
        return tagService.finAllDetail();
    }

    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id") Long id) {
        return tagService.findDetailById(id);
    }
}
