package com.run.blog.vo.params;

import lombok.Data;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@Data
public class LoginParams {

    private String account;

    private String password;

    // 注册时需要nickName，直接在此添加，实现复用
    private String nickname;
}
