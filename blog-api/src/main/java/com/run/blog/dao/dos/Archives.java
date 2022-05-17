package com.run.blog.dao.dos;

import lombok.Data;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */

/**
 * 因为文章归档返回的数据不是数据库的直接数据, 临时使用, 不属于pojo对象
 * 所以创建dos包存放非持久化数据
 */
@Data
public class Archives {

    private Integer year;

    private Integer month;

    private Long count;
}