package com.camellia.squirrelyouxuan.common.interceptor;

import com.camellia.squirrelyouxuan.common.auth.AuthContextHolder;
import com.camellia.squirrelyouxuan.common.redisconst.RedisConst;
import com.camellia.squirrelyouxuan.common.utils.JwtHelper;
import com.camellia.squirrelyouxuan.vo.user.UserLoginVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author fuyunjia
 * @Date 2023-11-18 13:02
 */

public class UserLoginInterceptor implements HandlerInterceptor {
    private RedisTemplate redisTemplate;

    public UserLoginInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        this.initUserLoginVo(request);
        return true;
    }

    private void initUserLoginVo(HttpServletRequest request){
        //从请求头获取token
        String token = request.getHeader("token");
        if (!StringUtils.isEmpty(token)) {
            Long userId = JwtHelper.getUserId(token);
            UserLoginVo userLoginVo = (UserLoginVo)redisTemplate.opsForValue().get(RedisConst.USER_LOGIN_KEY_PREFIX + userId);
            if(userLoginVo != null) {
                //将UserInfo放入上下文中
                AuthContextHolder.setUserId(userLoginVo.getUserId());
                AuthContextHolder.setUserLoginVo(userLoginVo);
            }
            // todo 如果过期了，返回重新登录
            // todo 本次请求完成应该remove这些数据，防止内存泄露
        }
    }
}
