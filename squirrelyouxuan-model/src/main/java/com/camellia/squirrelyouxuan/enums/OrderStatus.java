package com.camellia.squirrelyouxuan.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @Author fuyunjia
 * @Date 2024-03-04 22:54
 */
@Getter
public enum OrderStatus {
    //订单状态【0->待付款；1->待发货；2->待收货；3->已完成；-1->已取消】
    UNPAID(0,"待支付"),
    WAITING_DELEVER(1,"待发货"),
    WAITING_TAKE(2,"待收货"),
    FINISHED(3,"已完结"),
    CANCEL(-1,"已取消");

    @EnumValue
    private Integer code ;

    private String comment ;

    OrderStatus(Integer code, String comment ){
        this.code = code;
        this.comment=comment;
    }
}