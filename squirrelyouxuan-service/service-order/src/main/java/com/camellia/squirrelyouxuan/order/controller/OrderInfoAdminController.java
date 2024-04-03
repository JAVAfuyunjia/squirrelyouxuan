package com.camellia.squirrelyouxuan.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.model.order.OrderInfo;
import com.camellia.squirrelyouxuan.order.service.IOrderInfoService;
import com.camellia.squirrelyouxuan.vo.order.OrderInfoVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author fuyunjia
 * @Date 2024-03-05 9:39
 */

@RestController
@RequestMapping(value = "/admin/order/orderInfo")
public class OrderInfoAdminController {

    @Autowired
    public IOrderInfoService orderInfoService;

    @ApiOperation("订单列表")
    @GetMapping("{current}/{limit}")
    public Result list(@PathVariable Long current,
                       @PathVariable Long limit,
                       OrderInfoVo orderInfoVo) {
        Page<OrderInfo> pageParam = new Page<>(current, limit);
        IPage<OrderInfo> pageModel = orderInfoService.selectPageOrder(pageParam,orderInfoVo);
        return Result.ok(pageModel);
    }

    /**
     * 更新订单状态
     * @param orderInfoVo
     * @return
     */
    @ApiOperation("更新订单状态")
    @PutMapping ("/update")
    public Result updateOrderStatus(@RequestBody OrderInfoVo orderInfoVo) {
        OrderInfo orderInfo = orderInfoService.getByOrderId(orderInfoVo.getOrderNo());
        orderInfo.setOrderStatus(orderInfoVo.getOrderStatus());
        orderInfoService.updateById(orderInfo);
        return Result.ok(null);
    }



}
