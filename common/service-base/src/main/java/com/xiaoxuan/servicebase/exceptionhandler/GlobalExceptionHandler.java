package com.xiaoxuan.servicebase.exceptionhandler;


import com.xiaoxuan.utils.ExceptionUtil;
import com.xiaoxuan.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice//注解定义全局异常处理类
@Slf4j//使用logback日志
public class GlobalExceptionHandler {

    //声明异常处理方法,指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理。。。");
    }
    //处理自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody//返回json
    public R error(GuliException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return R.error().message(e.getMsg());
    }
}
