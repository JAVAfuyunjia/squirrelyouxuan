package com.camellia.squirrelyouxuan.common.auth;

import com.camellia.squirrelyouxuan.vo.user.UserLoginVo;

/**
 * @Author fuyunjia
 * @Date 2023-11-18 12:39
 */
public class AuthContextHolder {

    /**
     * 会员用户id
     */
    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();
    /**
     * 会员基本信息
     */
    private static ThreadLocal<UserLoginVo> userLoginVo = new ThreadLocal<>();

    public static Long getUserId(){
        return userId.get();
    }

    public static void setUserId(Long _userId){
        userId.set(_userId);
    }

    public static void setUserLoginVo(UserLoginVo _userLoginVo) {
        userLoginVo.set(_userLoginVo);
    }

}
