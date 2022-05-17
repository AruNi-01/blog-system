package com.run.blog.controller;

import com.run.blog.service.LoginService;
import com.run.blog.service.SysUserService;
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
@RequestMapping("login")
public class LoginController {


    // 不建议直接使用SysUserService, 每个Service都有单独的业务
    // LoginService就做登录的业务，SysUserService就做用户相关的业务
    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParams loginParams) {
        return loginService.login(loginParams);
    }
}
