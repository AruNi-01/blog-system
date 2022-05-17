package com.run.blog.service.Impl;

import com.alibaba.fastjson.JSON;
import com.run.blog.dao.pojo.SysUser;
import com.run.blog.service.LoginService;
import com.run.blog.service.SysUserService;
import com.run.blog.utils.JWTUtils;
import com.run.blog.vo.ErrorCode;
import com.run.blog.vo.Result;
import com.run.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 加密盐
    private static final String salt = "run!@#";

    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1. 检查参数是否合法
         * 2. 根据用户名和密码取user表中查询 是否存在
         * 3. 不存在  登录失败
         * 4. 存在   使用jwt 生成token  返回给前端
         * 5. token放入redis中，redis token：user信息  设置过期时间
         *    (登录认证的时候，先认证token字符串是否合法，去redis认证是否存在)
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        // 判断是否为空
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        // md5 盐值 加密 (需要引入依赖：commons-codec)
        password = DigestUtils.md5Hex(password + salt);

        SysUser sysUser = sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        // 用户存在，创建token
        String token = JWTUtils.createToken(sysUser.getId());

        // 将用户的token存入redis，过期时间为1天
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);

    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        // 解析token
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) {
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)) {
            return null;
        }

        // 校验成功，将json解析为SysUser对象返回
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);

        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    @Override
    public Result register(LoginParams loginParams) {
        /**
         * 1. 判断参数是否合法
         * 2. 判断账户是否存在，存在则返回账户已经被注册
         * 3. 不存在，注册
         * 4. 生成token
         * 5. 存入redis 并返回
         * 6. 注意：加上事务，一旦出现任何问题，注册的用户直接回滚
         */
        // 1
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if (StringUtils.isBlank(account)
                ||StringUtils.isBlank(nickname)
                ||StringUtils.isBlank(password)
        ) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }

        // 2
        SysUser sysUser = sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        // 3
        sysUser=new SysUser();
        sysUser.setAccount(account);                                   //账户名
        sysUser.setNickname(nickname);                                  //昵称
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));  //密码加盐md5
        sysUser.setCreateDate(System.currentTimeMillis());              //创建时间
        sysUser.setLastLogin(System.currentTimeMillis());               //最后登录时间
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");              //头像
        sysUser.setAdmin(1);                                             //管理员权限
        sysUser.setDeleted(0);                                             //假删除
        sysUser.setSalt("");                                                //盐
        sysUser.setStatus("");                                              //状态
        sysUser.setEmail("");                                               //邮箱
        sysUserService.save(sysUser);

        // 4
        String token = JWTUtils.createToken(sysUser.getId());
        // 5
        redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);

        // 6 该类已加上事务注解，自动开启了事务

        // 返回token
        return Result.success(token);
    }

}
