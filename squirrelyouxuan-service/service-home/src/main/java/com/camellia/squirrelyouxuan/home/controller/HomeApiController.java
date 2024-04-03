package com.camellia.squirrelyouxuan.home.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.camellia.squirrelyouxuan.common.auth.AuthContextHolder;
import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.home.service.IHomeService;
import com.camellia.squirrelyouxuan.home.service.IUserAddressService;
import com.camellia.squirrelyouxuan.model.user.UserAddress;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author fuyunjia
 * @Date 2023-11-19 16:26
 */
@Api(tags = "首页接口")
@RestController
@RequestMapping("api/home")
public class HomeApiController {

    @Resource
    private IHomeService homeService;

    @Resource
    private IUserAddressService userAddressService;

    @ApiOperation("首页数据显示接口")
    @GetMapping("index")
    public Result index(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId();
        Map<String,Object> map = homeService.homeData(userId);
        return Result.ok(map);
    }

    @ApiOperation("添加会员收货地址接口")
    @PostMapping ("addAddress")
    public Result addAddress(@RequestBody UserAddress userAddress) {
        userAddress.setUserId(AuthContextHolder.getUserId());
        userAddressService.saveOrUpdate(userAddress,
                new LambdaQueryWrapper<UserAddress>()
                        .eq(UserAddress::getUserId,userAddress.getUserId()));

        return Result.ok(null);
    }






}
