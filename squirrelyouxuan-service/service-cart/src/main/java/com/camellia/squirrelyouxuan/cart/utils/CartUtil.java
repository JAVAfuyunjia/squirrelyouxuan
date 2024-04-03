package com.camellia.squirrelyouxuan.cart.utils;


import com.camellia.squirrelyouxuan.common.redisconst.RedisConst;

/**
 * @Author fuyunjia
 * @Date 2023-11-22 12:13
 */
public class CartUtil {

    /**
     * 返回购物车在redis的key
     * @param userId
     * @return
     */
    public static String getCartKey(Long userId) {
        // user:userId:cart
        return RedisConst.USER_KEY_PREFIX + userId + RedisConst.USER_CART_KEY_SUFFIX;
    }



}
