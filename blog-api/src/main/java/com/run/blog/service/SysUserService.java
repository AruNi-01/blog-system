package com.run.blog.service;

import com.run.blog.dao.pojo.SysUser;
import com.run.blog.vo.Result;
import com.run.blog.vo.UserVo;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
public interface SysUserService {

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long authorId);
}
