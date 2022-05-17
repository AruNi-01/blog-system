package com.run.blog.vo;

import lombok.Data;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@Data
public class TagVo {

    private Long id;

    private String tagName;

    // 标签的图片
    private String avatar;

}
