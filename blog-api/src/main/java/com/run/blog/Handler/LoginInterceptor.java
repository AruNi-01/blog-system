package com.run.blog.Handler;

import com.alibaba.fastjson.JSON;
import com.run.blog.dao.pojo.SysUser;
import com.run.blog.service.LoginService;
import com.run.blog.utils.UserThreadLocal;
import com.run.blog.vo.ErrorCode;
import com.run.blog.vo.Result;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 1.需要判断,请求的接口路径是否为HandleMethod(controller方法)
         * 2.判断token是否为空,为空未登录
         * 3.不为空,登录验证 loginService.checkToken()
         * 4.如果认证成功放行
         */

        // 若controller方法不是HandleMethod，则直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从前端拿token请求头
        String token = request.getHeader("Authorization");

        // 日志打印到控制台
        log.info("===============request start===============");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("===============request end===============");

        // token为空，拦截
        if (StringUtils.isBlank(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            // 告诉浏览器返回的类型为json和字符编码
            response.setContentType("application/json;charset=utf-8");
            // 用fastjson将result转为json类型返回
            response.getWriter().print(JSON.toJSONString(result));
            // 拦截
            return false;
        }

        // 用户为空，拦截
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            // 拦截
            return false;
        }

        // 登录验证成功，放行
        // 将该用户存入ThreadLocal中，在controller中通过UserThreadLocal.get直接获取即可
        UserThreadLocal.put(sysUser);
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 用完了如果不删除ThreadLocal中的信息, 会有内训泄露的风险
        UserThreadLocal.remove();
    }
}
