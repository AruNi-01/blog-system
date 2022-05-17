package com.run.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@Data
public class CategoryVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    // 文章类型的图片
    private String avatar;

    private String categoryName;

    private String description;

}