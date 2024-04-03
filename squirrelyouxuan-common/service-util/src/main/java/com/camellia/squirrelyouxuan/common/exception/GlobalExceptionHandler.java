package com.camellia.squirrelyouxuan.common.exception;

import com.camellia.squirrelyouxuan.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author fuyunjia
 * @Date 2023-11-03 23:02
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理异常
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail(null);
    }

    /**
     * 处理自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(SquirrelYouXuanException.class)
    @ResponseBody
    public Result error(SquirrelYouXuanException e){
        e.printStackTrace();
        return Result.build(null,e.getCode(),e.getMessage());
    }
}
