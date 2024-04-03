package com.camellia.squirrelyouxuan.mq.constant;

/**
 * @Author fuyunjia
 * @Date 2023-11-10 14:49
 */
public class MqConst {

    /**
     * 商品上下架
     */
    public static final String EXCHANGE_GOODS_DIRECT = "squirrelyouxuan.goods.direct";
    public static final String ROUTING_GOODS_UPPER = "squirrelyouxuan.goods.upper";
    public static final String ROUTING_GOODS_LOWER = "squirrelyouxuan.goods.lower";

    public static final String QUEUE_GOODS_UPPER  = "squirrelyouxuan.goods.upper";
    public static final String QUEUE_GOODS_LOWER  = "squirrelyouxuan.goods.lower";

    /**
     * 删除商品
     */

    public static final String ROUTING_GOODS_DELETE = "squirrelyouxuan.goods.delete";

    public static final String QUEUE_GOODS_DELETE  = "squirrelyouxuan.goods.delete";



    //订单
    public static final String EXCHANGE_ORDER_DIRECT = "squirrelyouxuan.order.direct";
    public static final String ROUTING_MINUS_STOCK = "squirrelyouxuan.minus.stock";
    public static final String ROUTING_DELETE_CART = "squirrelyouxuan.delete.cart";
    public static final String QUEUE_MINUS_STOCK = "squirrelyouxuan.minus.stock";
    public static final String QUEUE_DELETE_CART = "squirrelyouxuan.delete.cart";

    /**
     * 支付
     */
    public static final String EXCHANGE_PAY_DIRECT = "squirrelyouxuan.pay.direct";
    public static final String ROUTING_PAY_SUCCESS = "squirrelyouxuan.pay.success";
    public static final String QUEUE_ORDER_PAY  = "squirrelyouxuan.order.pay";

}