package com.run.blog.common.cache;

import com.alibaba.fastjson.JSON;
import com.run.blog.vo.ErrorCode;
import com.run.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author AruNi_Lu
 * @data 2022/4/14
 */

@Aspect
@Component
@Slf4j
public class CacheAspect {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 起点
    @Pointcut("@annotation(com.run.blog.common.cache.Cache)")
    public void pt(){}

    // 环绕通知
    @Around("pt()")
    public Object around(ProceedingJoinPoint joinPoint){
        try {
            Signature signature = joinPoint.getSignature();
            // 类名
            String className = joinPoint.getTarget().getClass().getSimpleName();
            // 调用的方法名
            String methodName = signature.getName();


            Class[] parameterTypes = new Class[joinPoint.getArgs().length];
            Object[] args = joinPoint.getArgs();
            // 获取参数
            String params = "";
            for(int i=0; i<args.length; i++) {
                if(args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                }else {
                    parameterTypes[i] = null;
                }
            }
            if (StringUtils.isNotEmpty(params)) {
                //加密 以防出现key过长以及字符转义获取不到的情况
                params = DigestUtils.md5Hex(params);
            }
            // 获取方法
            Method method = joinPoint.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            // 获取Cache注解
            Cache annotation = method.getAnnotation(Cache.class);
            // 缓存过期时间
            long expire = annotation.expire();
            // 缓存名称
            String name = annotation.name();
            // 先从redis获取key
            String redisKey = name + "::" + className + "::" + methodName + "::" + params;
            String redisValue = redisTemplate.opsForValue().get(redisKey);
            if (StringUtils.isNotEmpty(redisValue)){
                log.info("进了缓存的方法有：{}:: {}", className, methodName);
                return JSON.parseObject(redisValue, Result.class);
            }
            Object proceed = joinPoint.proceed();
            // 存入Redis中
            redisTemplate.opsForValue().set(redisKey, JSON.toJSONString(proceed), Duration.ofMillis(expire));
            log.info("存入缓存：{}:: {}",className, methodName);
            return proceed;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return Result.fail(ErrorCode.SYSTEM_ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getMsg());
    }

}
