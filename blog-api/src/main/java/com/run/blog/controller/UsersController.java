package com.run.blog.controller;

import com.run.blog.service.SysUserService;
import com.run.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("currentUser")
    // Authorization是前端传过来的token头部信息
    public Result currentUser(@RequestHeader("Authorization") String token) {
        return sysUserService.findUserByToken(token);

    }
}
