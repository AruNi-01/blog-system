package com.run.blog.service;

import com.run.blog.dao.pojo.SysUser;
import com.run.blog.vo.Result;
import com.run.blog.vo.params.LoginParams;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
public interface LoginService {

    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParams loginParams);
}
