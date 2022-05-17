package com.run.blog.vo.params;

import lombok.Data;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@Data
/**
 * 页面展示参数
 * page：当前页
 * pageSize：页面大小
 *
 * 以下参数用于导航栏--文章分类 标签：
 * categoryId: 分类id
 * tagId: 标签id
 *
 * 以下参数用于导航栏--文章归档：
 * year: 年份
 * month: 月份
 */
public class PageParams {
    private int page = 1;
    private int pageSize = 10;

    private Long categoryId;

    private Long tagId;

    private String year;

    private String month;

    // 月份需要两位：01 02 03 ...，因为将时间戳转为Date日期格式的时候，个位的数据是两位数
    public String getMonth() {
        if (this.month != null && this.month.length() == 1) {
            return "0" + this.month;
        }
        return this.month;
    }
}
