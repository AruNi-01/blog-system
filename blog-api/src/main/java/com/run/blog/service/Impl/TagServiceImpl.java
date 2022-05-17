package com.run.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.run.blog.dao.mapper.TagMapper;
import com.run.blog.dao.pojo.Tag;
import com.run.blog.service.TagService;
import com.run.blog.vo.Result;
import com.run.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 根据文章id查询它的tag
     * @param articleId
     * @return
     */
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        // MybatisPlus 无法进行多表查询，自己写SQL
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);

        // 同样的需要将Tag转换为TagVo
        return copyList(tags);
    }

    /**
     * 首页，最热tag
     * @param limit
     * @return
     */
    @Override
    public Result hots(int limit) {
        /**
         * 1.最热标签：标签所拥有的文章数量最多
         * 2.根据tag_id分组 计数，排序，取前limit个
         */
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds)) {
            return Result.success(Collections.emptyList());
        }

        // 需要的是tagId和tagName Tag对象
        // SQL: select * from tag where id = (1, 2, 3, 4, 5, 6);
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);

        return Result.success(tagList);
    }

    /**
     * 在发布文章时候需要tag的id和name
     * @return
     */
    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId, Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    /**
     * 导航栏--所有标签
     * @return
     */
    @Override
    public Result finAllDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tags));
    }

    /**
     * 根据标签的id获取该标签的信息
     * @param id
     * @return
     */
    @Override
    public Result findDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    public List<TagVo> copyList(List<Tag> tagList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

}
