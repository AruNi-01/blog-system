package com.run.blog.common.cache;

import java.lang.annotation.*;

/**
 * @author AruNi_Lu
 * @data 2022/4/14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    // 缓存过期时间 1min
    long expire() default 1 * 60 * 1000;

    // 缓存标识 key
    String name() default "";
}
