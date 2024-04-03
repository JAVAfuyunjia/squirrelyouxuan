package com.camellia.squirrelyouxuan.feign.user;

import com.camellia.squirrelyouxuan.vo.user.UserAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-user")
public interface UserFeignClient {

    /**
     * 根据userId查询地址信息
     * @param userId
     * @return
     */
    @GetMapping("/api/user/address/inner/getUserAddressByUserId/{userId}")
    public UserAddressVo getUserAddressByUserId(@PathVariable("userId") Long userId);


    /**
     * 获取用户数量
     * @return
     */
    @GetMapping("/api/user/address/inner/getUserCount")
    public Integer getUserCount();
}

