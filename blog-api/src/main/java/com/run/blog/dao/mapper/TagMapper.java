package com.run.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.run.blog.dao.pojo.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 根据文章id查询对应的标签列表
     * @param articleId
     * @return
     */
    List<Tag> findTagsByArticleId(Long articleId);

    /**
     * 查询前limit条最热标签
     * @param limit
     * @return
     */
    List<Long> findHotsTagIds(int limit);

    /**
     * 通过热门tag的id查询对应的tag
     * @param tagIds
     * @return
     */
    List<Tag> findTagsByTagIds(List<Long> tagIds);
}
