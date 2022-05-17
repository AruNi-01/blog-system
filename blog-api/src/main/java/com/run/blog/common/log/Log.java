package com.run.blog.common.log;

import java.lang.annotation.*;
/**
 * @author AruNi_Lu
 * @data 2022/4/12
 */

/**
 * 自定义日志注解，在需要的方法上添加上此注解即可
 */
@Target({ElementType.METHOD})       // 来修饰"方法"的注解
@Retention(RetentionPolicy.RUNTIME)     // 编译器会将该注解信息保留在.class文件中，并且能被虚拟机读取
@Documented     // 表示它在缺省情况下可以出现在javadoc中
public @interface Log {

    String module() default "";

    String operator() default "";

}
