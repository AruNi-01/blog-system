package com.run.blog.controller;

import com.run.blog.service.LoginService;
import com.run.blog.vo.Result;
import com.run.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    // 注册功能直接写在LoginService里面，项目比较小
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {
        // sso单点登录，后期如果把登录注册功能提出去（单独的服务，可以独立提供接口服务）
        return loginService.register(loginParams);
    }
}
