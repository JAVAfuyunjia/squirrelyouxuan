package com.camellia.squirrelyouxuan.feign.order;

import com.camellia.squirrelyouxuan.model.order.OrderInfo;
import com.camellia.squirrelyouxuan.vo.order.OrderCountVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("service-order")
public interface OrderFeignClient {

    @GetMapping("/api/order/inner/getOrderInfo/{orderNo}")
    public OrderInfo getOrderInfo(@PathVariable("orderNo") String orderNo);


    /**
     * 获取订单数量信息（某天的订单数量）
     * @return
     */
    @GetMapping("/api/order/inner/order/count")
    public List<OrderCountVo> orderCountForChart();

    /**
     * 获取订单总数
     * @return
     */
    @GetMapping("/api/order/inner/getOrderAllCount")
    public Integer getOrderAllCount();
}
