package com.camellia.squirrelyouxuan.order;

import com.camellia.squirrelyouxuan.order.service.IOrderInfoService;
import com.camellia.squirrelyouxuan.vo.order.OrderCountVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author fuyunjia
 * @Date 2024-04-02 17:23
 */

@SpringBootTest

public class testOrderCount {

    @Autowired
    private IOrderInfoService orderInfoService;

    @Test
    public void testOrderCount() {
        List<OrderCountVo> orderCount = orderInfoService.orderCountForChart();
        System.out.println(orderCount);
    }
}
