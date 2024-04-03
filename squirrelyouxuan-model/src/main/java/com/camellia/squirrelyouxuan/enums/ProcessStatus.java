package com.camellia.squirrelyouxuan.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;


@Getter
public enum ProcessStatus {

    UNPAID(1, OrderStatus.UNPAID),
    WAITING_DELEVER(2, OrderStatus.WAITING_DELEVER),
    WAITING_USER_TAKE(3, OrderStatus.WAITING_TAKE),
    FINISHED(4, OrderStatus.FINISHED),
    CANCEL(-1, OrderStatus.CANCEL),
    PAY_FAIL(-2, OrderStatus.UNPAID);

    @EnumValue
    private Integer code ;

    private OrderStatus orderStatus;

    ProcessStatus(Integer code, OrderStatus orderStatus){
        this.code=code;
        this.orderStatus=orderStatus;
    }

}
