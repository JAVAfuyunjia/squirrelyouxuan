package com.camellia.squirrelyouxuan.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.camellia.squirrelyouxuan.acl.mapper.AdminMapper;
import com.camellia.squirrelyouxuan.acl.service.IAdminService;
import com.camellia.squirrelyouxuan.common.exception.SquirrelYouXuanException;
import com.camellia.squirrelyouxuan.common.result.ResultCodeEnum;
import com.camellia.squirrelyouxuan.common.utils.JwtHelper;
import com.camellia.squirrelyouxuan.feign.order.OrderFeignClient;
import com.camellia.squirrelyouxuan.feign.product.ProductFeignClient;
import com.camellia.squirrelyouxuan.feign.user.UserFeignClient;
import com.camellia.squirrelyouxuan.model.acl.Admin;
import com.camellia.squirrelyouxuan.vo.order.OrderCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author fuyunjia
 * @Date 2023-11-05 14:13
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {


    @Autowired
    private IAdminService adminService;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;




    /**
     * 获取首页信息
     * @return
     */
    @Override
    public HashMap<String, Object> getDashboard() {


        HashMap<String, Object> result = new HashMap<>();
        // 获取订单数量信息
        List<OrderCountVo> orderCountForChart = orderFeignClient.orderCountForChart();

        // 获取用户数量
        Integer userCount = userFeignClient.getUserCount();
        // 获取订单总数量
        Integer orderAllCount = orderFeignClient.getOrderAllCount();
        // 获取商品数量
        Integer skuCount = productFeignClient.getSkuCount();
        result.put("orderCountForChart", orderCountForChart);
        result.put("userCount", userCount);
        result.put("orderAllCount", orderAllCount);
        result.put("skuCount", skuCount);


        return result;
    }

    /**
     * 验证登录
     * @param admin
     * @return
     */
    @Override
    public String login(Admin admin) {
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(Admin::getUsername, admin.getUsername()).eq(Admin::getPassword, admin.getPassword());

        Admin one = adminService.getOne(adminLambdaQueryWrapper);
        if(one == null){
            throw new SquirrelYouXuanException(ResultCodeEnum.USER_NAME_OR_PASSWORD_ERROR);
        }
        String token = JwtHelper.createToken(admin.getId(), admin.getUsername());
        return token;
    }



}
