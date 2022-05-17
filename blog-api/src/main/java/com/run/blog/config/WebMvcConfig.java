package com.run.blog.config;

import com.run.blog.Handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 跨域配置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 跨域配置，让前端ip能访问本项目的所有接口
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }


    /**
     * 拦截器配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截接口, 除了登录注册的所有接口，这里用/test测试一下
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")    // 登录后才可以评论
                .addPathPatterns("/articles/publish");       // 拦截写文章
    }
}
