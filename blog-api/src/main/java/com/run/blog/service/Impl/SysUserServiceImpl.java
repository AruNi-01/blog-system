package com.run.blog.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.run.blog.dao.mapper.SysUserMapper;
import com.run.blog.dao.pojo.SysUser;
import com.run.blog.service.LoginService;
import com.run.blog.service.SysUserService;
import com.run.blog.vo.ErrorCode;
import com.run.blog.vo.LoginUserVo;
import com.run.blog.vo.Result;
import com.run.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private LoginService loginService;

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        // 防止测试数据为空
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setNickname("AruNi");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.eq(SysUser::getPassword, password);
        queryWrapper.select(SysUser::getAccount, SysUser::getId, SysUser::getAvatar, SysUser::getNickname);
        queryWrapper.last("limit 1");

        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1. token合法性校验(是否为空，解析是否成功，redis是否存在)
         * 2. 校验失败  返回错误
         * 3. 校验成功  返回对应结果 LoginUserVo
         */
        // 理论上应该写TokenService处理，此处简单处理，直接使用LoginService
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();

        // 拷贝sysUser和loginUserVo相等的字段
        BeanUtils.copyProperties(sysUser, loginUserVo);

        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount, account);
        queryWrapper.last("limit 1");
        sysUserMapper.selectOne(queryWrapper);
        return null;
    }

    @Override
    public void save(SysUser sysUser) {
        // 保存用户id会自动生成
        // mybatis-plus默认生成分布式id, 采用雪花算法
        sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        // 防止测试数据为空
        if (sysUser == null) {
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c9.png");
            sysUser.setNickname("AruNi123");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser, userVo);
        return userVo;
    }
}
