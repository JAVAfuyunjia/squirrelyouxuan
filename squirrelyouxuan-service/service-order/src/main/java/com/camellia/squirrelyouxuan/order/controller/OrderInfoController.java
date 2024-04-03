package com.camellia.squirrelyouxuan.order.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.camellia.squirrelyouxuan.common.auth.AuthContextHolder;
import com.camellia.squirrelyouxuan.common.result.Result;
import com.camellia.squirrelyouxuan.model.order.OrderInfo;
import com.camellia.squirrelyouxuan.order.service.IOrderInfoService;
import com.camellia.squirrelyouxuan.vo.order.OrderConfirmVo;
import com.camellia.squirrelyouxuan.vo.order.OrderCountVo;
import com.camellia.squirrelyouxuan.vo.order.OrderSubmitVo;
import com.camellia.squirrelyouxuan.vo.order.OrderUserQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-25
 */
@RestController
@RequestMapping(value="/api/order")
public class OrderInfoController {

    @Autowired
    private IOrderInfoService orderInfoService;

    /**
     * 订单查询
     * @param page
     * @param limit
     * @param orderUserQueryVo
     * @return
     */
    @GetMapping("auth/findUserOrderPage/{page}/{limit}")
    public Result findUserOrderPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "orderVo", value = "查询对象", required = false)
            OrderUserQueryVo orderUserQueryVo) {
        //获取userId
        Long userId = AuthContextHolder.getUserId();
        orderUserQueryVo.setUserId(userId);

        //分页查询条件
        Page<OrderInfo> pageParam = new Page<>(page,limit);
        IPage<OrderInfo> pageModel = orderInfoService.getOrderInfoByUserIdPage(pageParam,orderUserQueryVo);
        return Result.ok(pageModel);
    }
    @ApiOperation("确认订单")
    @GetMapping("auth/confirmOrder")
    public Result confirm() {
        OrderConfirmVo orderConfirmVo = orderInfoService.confirmOrder();
        return Result.ok(orderConfirmVo);
    }

    @ApiOperation("生成订单")
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderSubmitVo orderParamVo) {
        Long orderId = orderInfoService.submitOrder(orderParamVo);
        return Result.ok(orderId);
    }

    @ApiOperation("获取订单详情")
    @GetMapping("auth/getOrderInfoById/{orderId}")
    public Result getOrderInfoById(@PathVariable("orderId") Long orderId){
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
        return Result.ok(orderInfo);
    }

    /**
     * 根据orderNo查询订单信息
     * @param orderNo
     * @return
     */
    @GetMapping("inner/getOrderInfo/{orderNo}")
    public OrderInfo getOrderInfo(@PathVariable("orderNo") String orderNo) {
        OrderInfo orderInfo = orderInfoService.getOrderInfoByOrderNo(orderNo);
        return orderInfo;
    }

    /**
     * 获取订单数量和日期
     */
    @ApiOperation("获取订单数量和日期")
    @GetMapping("inner/order/count")
    public List<OrderCountVo> orderCountForChart() {
        return orderInfoService.orderCountForChart();
    }

    /**
     * 获取订单总数量
     */
    @ApiOperation("获取订单总数量")
    @GetMapping("inner/getOrderAllCount")
    public Integer getOrderAllCount() {
        return orderInfoService.count();
    }
}

