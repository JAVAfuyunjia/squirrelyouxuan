package com.camellia.squirrelyouxuan.common.redisconst;

/**
 * @Author fuyunjia
 * @Date 2023-11-16 20:19
 * description:常量类
 */

public class RedisConst {


    public static final String SKUKEY_PREFIX = "sku:";
    public static final String HOR_SCORE = "hotScore";


    public static final String USER_KEY_PREFIX = "user:";
    public static final String USER_CART_KEY_SUFFIX = ":cart";
    public static final long USER_CART_EXPIRE = 60 * 60 * 24 * 7;
    public static final String SROCK_INFO = "stock:info:";
    public static final String ORDER_REPEAT = "order:repeat:";

    /**
     * 用户登录
     */
    public static final String USER_LOGIN_KEY_PREFIX = "user:login:";
    public static final int USERKEY_TIMEOUT = 365;
}
