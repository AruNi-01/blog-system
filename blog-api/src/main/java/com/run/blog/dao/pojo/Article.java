package com.run.blog.dao.pojo;

import lombok.Data;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@Data
public class Article {
    public static final Integer Article_TOP = 1;

    public static final Integer Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    private Long createDate;
}
