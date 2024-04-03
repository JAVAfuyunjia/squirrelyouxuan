package com.camellia.squirrelyouxuan.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.camellia.squirrelyouxuan.model.user.User;
import com.camellia.squirrelyouxuan.model.user.UserAddress;
import org.springframework.stereotype.Repository;

/**
 * @Author fuyunjia
 * @Date 2023-11-17 15:38
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户id查询地址
     * @param id
     * @return
     */
    UserAddress selectAddressByUserId(Long id);
}
