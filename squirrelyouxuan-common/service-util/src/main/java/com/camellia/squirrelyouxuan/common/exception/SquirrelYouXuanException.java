package com.camellia.squirrelyouxuan.common.exception;

import com.camellia.squirrelyouxuan.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @Author fuyunjia
 * @Date 2023-11-03 23:07
 */
@Data
public class SquirrelYouXuanException extends RuntimeException {
    /**
     * 异常状态码
     */
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message
     * @param code
     */
    public SquirrelYouXuanException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public SquirrelYouXuanException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "Exception{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
