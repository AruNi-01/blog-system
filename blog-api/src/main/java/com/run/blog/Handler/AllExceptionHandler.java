package com.run.blog.Handler;

import com.run.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author AruNi_Lu
 * @data 2022/4/10
 */
// 对加了@Controller注解的方法进行拦截处理，AOP实现
@ControllerAdvice
public class AllExceptionHandler {

    // 进行异常处理，处理Exception.class的异常(全部异常)
    @ExceptionHandler(Exception.class)
    @ResponseBody   // 返回json数据
    public Result doException(Exception e) {
        e.printStackTrace();
        return Result.fail(-999, "系统异常");
    }
}
