package com.camellia.squirrelyouxuan.home.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.camellia.squirrelyouxuan.home.mapper.UserAddressMapper;
import com.camellia.squirrelyouxuan.home.service.IUserAddressService;
import com.camellia.squirrelyouxuan.model.user.UserAddress;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author fuyunjia
 * @since 2024-02-26
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressService {

}
