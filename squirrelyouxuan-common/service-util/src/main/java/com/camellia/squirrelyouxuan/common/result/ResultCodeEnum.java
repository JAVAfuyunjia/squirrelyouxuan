package com.camellia.squirrelyouxuan.common.result;

import lombok.Getter;

/**
 * @Author fuyunjia
 * @Date 2023-11-03 22:23
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(2012, "服务异常"),
    DATA_ERROR(204, "数据异常"),
    ILLEGAL_REQUEST(205, "非法请求"),
    REPEAT_SUBMIT(206, "重复提交"),

    LOGIN_AUTH(208, "未登陆"),
    PERMISSION(209, "没有权限"),


    ORDER_PRICE_ERROR(210, "订单商品价格变化"),
    ORDER_STOCK_FALL(204, "订单库存锁定失败"),
    CREATE_ORDER_FAIL(210, "创建订单失败"),

    URL_ENCODE_ERROR( 216, "URL编码失败"),
    ILLEGAL_CALLBACK_REQUEST_ERROR( 217, "非法回调请求"),
    FETCH_ACCESSTOKEN_FAILD( 218, "获取accessToken失败"),
    FETCH_OPENID_SESSIONKEY_FAILD(215,"获取openID和sessionKey失败"),
    FETCH_USERINFO_ERROR( 219, "获取用户信息失败"),


    SKU_LIMIT_ERROR(230, "购买个数不能大于限购个数"),
    PAYMENT_WAITING(242, "订单支付中"),
    PAYMENT_SUCCESS(241, "订单支付成功"),
    PAYMENT_FAIL(243, "订单支付失败"),

    /**
     * 获取收货地址异常
     */
    ADDRESS_ERROR(250, "获取收货地址异常"),

    USER_NAME_OR_PASSWORD_ERROR(251, "用户名或密码错误"),

    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
