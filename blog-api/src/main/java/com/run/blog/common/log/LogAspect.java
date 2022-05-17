package com.run.blog.common.log;

import com.alibaba.fastjson.JSON;
import com.run.blog.utils.HttpContextUtil;
import com.run.blog.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author AruNi_Lu
 * @data 2022/4/12
 */
@Component
@Aspect     // 切面：定义了通知和切点的关系
@Slf4j
public class LogAspect {

    // 切点
    @Pointcut("@annotation(com.run.blog.common.log.Log)")
    public void pt() {}

    // 环绕通知
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = joinPoint.proceed();
        // 执行时长
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        recordLog(joinPoint, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        LogAspect.log.info("=====================log start================================");
        LogAspect.log.info("module:{}", log.module());
        LogAspect.log.info("operation:{}", log.operator());

        // 请求的类名和方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        LogAspect.log.info("request method:{}",className + "." + methodName + "()");

        // 请求的参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        LogAspect.log.info("params:{}",params);

        // 获取request 设置IP地址
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        LogAspect.log.info("ip:{}", IpUtil.getIpAddr(request));

        // 执行时间
        LogAspect.log.info("execute time : {} ms",time);
        LogAspect.log.info("=====================log end================================");

    }

}
