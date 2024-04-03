package com.camellia.squirrelyouxuan.user.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.camellia.squirrelyouxuan.model.user.User;
import com.camellia.squirrelyouxuan.model.user.UserAddress;
import com.camellia.squirrelyouxuan.user.mapper.UserMapper;
import com.camellia.squirrelyouxuan.user.service.IUserService;
import com.camellia.squirrelyouxuan.vo.user.UserAddressVo;
import com.camellia.squirrelyouxuan.vo.user.UserLoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author fuyunjia
 * @Date 2023-11-17 15:36
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {



    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserByOpenId(String openid) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenId,openid);
        User user = baseMapper.selectOne(wrapper);
        return user;
    }
    @Override
    public UserLoginVo getUserLoginVo(Long id) {
        User user = baseMapper.selectById(id);
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserId(id);
        userLoginVo.setNickName(user.getNickName());
        userLoginVo.setPhotoUrl(user.getPhotoUrl());
        userLoginVo.setOpenId(user.getOpenId());

        return userLoginVo;
    }

    /**
     *  根据userId查询收货地址信息
     * @param id
     * @return
     */
    @Override
    public UserAddressVo getAddressByUserId(Long id) {
        UserAddress userAddress = userMapper.selectAddressByUserId(id);
        if (userAddress == null){
            return null;
        }


        UserAddressVo userAddressVo = new UserAddressVo();
        BeanUtils.copyProperties(userAddress, userAddressVo);
        return userAddressVo;
    }

}
