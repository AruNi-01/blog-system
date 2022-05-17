package com.run.blog.controller;

import com.run.blog.dao.pojo.SysUser;
import com.run.blog.utils.UserThreadLocal;
import com.run.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@RestController
@RequestMapping("test")
public class TestController {

    /**
     * 测试拦截器
     */
    @RequestMapping
    public Result test() {
        // 拦截器中存了sysUser，这里直接获取即可
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}
