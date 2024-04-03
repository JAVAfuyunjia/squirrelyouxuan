package com.camellia.squirrelyouxuan.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.user.User;
import com.camellia.squirrelyouxuan.vo.user.UserAddressVo;
import com.camellia.squirrelyouxuan.vo.user.UserLoginVo;

/**
 * @Author fuyunjia
 * @Date 2023-11-17 15:16
 */
public interface IUserService extends IService<User> {
    /**
     * 获取user对象
     * @param openid
     * @return
     */
    User getUserByOpenId(String openid);


    /**
     * 封装user信息
     * @param id
     * @return
     */
    UserLoginVo getUserLoginVo(Long id);

    /**
     * 获取用户收货地址
     * @param UserId
     * @return
     */
    UserAddressVo getAddressByUserId(Long UserId);


}
