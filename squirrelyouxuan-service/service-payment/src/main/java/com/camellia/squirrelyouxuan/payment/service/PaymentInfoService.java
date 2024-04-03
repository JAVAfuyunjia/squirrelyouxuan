package com.camellia.squirrelyouxuan.payment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.camellia.squirrelyouxuan.model.order.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService extends IService<PaymentInfo> {

    /**
     * 根据orderNo查询支付记录
     * @param orderNo
     * @return
     */
    PaymentInfo getPaymentInfoByOrderNo(String orderNo);

    /**
     * 添加支付记录
     * @param orderNo
     * @return
     */
    PaymentInfo savePaymentInfo(String orderNo);

    /**
     * 支付成功，修改支付记录表状态：已经支付
     * 支付成功，修改订单记录已经支付，库存扣减
     * @param out_trade_no 对外订单号（和订单号一致）
     * @param resultMap
     */
    void paySuccess(String out_trade_no, Map<String, String> resultMap);
}
