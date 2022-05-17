package com.run.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */

/**
 * 统一结果返回
 * success：成功与否
 * code：状态码
 * msg：返回信息
 * data：返回的数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean success;

    private int code;

    private String msg;

    private Object data;

    public static Result success(Object data) {
        return new Result(true, 200, "success", data);
    }

    public static Result fail(int  code, String msg) {
        return new Result(false, code, msg, null);
    }

}