package com.camellia.squirrelyouxuan.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.order.OrderInfo;
import com.camellia.squirrelyouxuan.vo.order.*;

import java.util.List;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author fuyunjia
 * @since 2023-11-25
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    /**
     * 确认订单
     * @return
     */
    OrderConfirmVo confirmOrder();

    /**
     * 生成订单
     * @param orderParamVo
     * @return
     */
    Long submitOrder(OrderSubmitVo orderParamVo);

    /**
     * 获取订单详情通过订单ID
     * @param orderId
     * @return
     */
    OrderInfo getOrderInfoById(Long orderId);

    /**
     * 获取订单详情通过订单号
     * @param orderNo
     * @return
     */
    OrderInfo getOrderInfoByOrderNo(String orderNo);

    /**
     * 订单支付成功，更新订单状态，扣减库存
     * @param orderNo
     */
    void orderPay(String orderNo);

    /**
     * 订单查询
     * @param pageParam
     * @param orderUserQueryVo
     * @return
     */
    IPage<OrderInfo> getOrderInfoByUserIdPage(Page<OrderInfo> pageParam, OrderUserQueryVo orderUserQueryVo);

    /**
     * 后台订单查询
     * @param pageParam
     * @param orderInfoVo
     * @return
     *
     * @param pageParam
     * @param orderInfoVo
     * @return
     */
    IPage<OrderInfo> selectPageOrder(Page<OrderInfo> pageParam, OrderInfoVo orderInfoVo);

    /**
     *  根据订单号获取订单详情
     * @param orderNo
     * @return
     */
    OrderInfo getByOrderId(String orderNo);

    List<OrderCountVo> orderCountForChart();
}
