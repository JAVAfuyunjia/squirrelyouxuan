package com.camellia.squirrelyouxuan.common.result;

import lombok.Data;

/**
 * @Author fuyunjia
 * @Date 2023-11-03 22:27
 */
@Data
public class Result<T> {


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 私有化构造器
     */
    private Result() {
    }


    /**
     * 设置数据,返回对象的方法
     *
     * @param data
     * @param resultCodeEnum
     * @param <R>
     * @return
     */
    public static <R> Result<R> build(R data, ResultCodeEnum resultCodeEnum) {
        // 创建Result对象，设置值，返回对象
        Result<R> result = new Result<>();
        //判断返回结果中是否需要数据
        if (data != null) {
            //设置数据到result对象
            result.setData(data);
        }
        // 设置其他值
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        // 返回设置值之后的对象
        return result;
    }

    public static <R> Result<R> build(R data, Integer code,String message) {
        // 创建Result对象，设置值，返回对象
        Result<R> result = new Result<>();
        //判断返回结果中是否需要数据
        if (data != null) {
            //设置数据到result对象
            result.setData(data);
        }
        // 设置其他值
        result.setCode(code);
        result.setMessage(message);
        // 返回设置值之后的对象
        return result;
    }


    /**
     * 成功的方法
     *
     * @param data
     * @param <R>
     * @return
     */
    public static <R> Result<R> ok(R data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }


    /**
     * 失败的方法
     *
     * @param data
     * @param <R>
     * @return
     */
    public static <R> Result<R> fail(R data) {
        return build(data, ResultCodeEnum.FAIL);
    }

    /**
     * 失败的方法
     * @return
     */
    public static Result fail() {
        return fail(null);
    }
}
