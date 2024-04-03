package com.camellia.squirrelyouxuan.acl.controller;

import com.camellia.squirrelyouxuan.acl.service.IAdminService;
import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.model.acl.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author fuyunjia
 * @Date 2023-11-04 15:48
 */

@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/acl/index/")
public class IndexController {

    @Autowired
    private IAdminService adminService;

    /**
     * 登录
     * @return
     */
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody Admin admin){
        // 验证用户名和密码
        String token = adminService.login(admin);

        HashMap<String, String> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    /**
     * 获取信息
     * @return
     */
    @ApiOperation("获取信息")
    @GetMapping("/info")
    public Result info(){
        HashMap<String, String> map = new HashMap<>();
        map.put("name","欢迎管理");
        map.put("avatar","https://squirrelyouxuan-camellia.oss-cn-chengdu.aliyuncs.com/%E5%8A%A8%E7%89%A9%E7%85%A7.png");
        return Result.ok(map);
    }

    /**
     * 退出登录
     * @return
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public Result logout(){
        return Result.ok(null);
    }

    /**
     * 获取首页信息
     */
    @ApiOperation("获取首页订单数量信息")
    @GetMapping("/dashboard")
    public Result dashboard(){
        HashMap<String, Object>  result = adminService.getDashboard();
        return Result.ok(result);
    }
}
