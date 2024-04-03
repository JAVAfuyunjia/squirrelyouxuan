package com.camellia.squirrelyouxuan.user.api;

import com.camellia.squirrelyouxuan.user.service.IUserService;
import com.camellia.squirrelyouxuan.vo.user.UserAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/address")
public class UserAddressApiController {

    @Autowired
    private IUserService userService;

    /**
     * 根据userId地址
     * @param userId
     * @return
     */
    @GetMapping("/inner/getUserAddressByUserId/{userId}")
    public UserAddressVo getUserAddressByUserId(@PathVariable("userId") Long userId) {
        UserAddressVo userAddressVo = userService.getAddressByUserId(userId);
        return userAddressVo;
    }

    /**
     * 获取用户数量
     */
    @GetMapping("/inner/getUserCount")
    public Integer getUserCount() {
        return userService.count();
    }
}
