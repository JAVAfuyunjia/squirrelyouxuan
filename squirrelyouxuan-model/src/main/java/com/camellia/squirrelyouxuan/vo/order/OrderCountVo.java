package com.camellia.squirrelyouxuan.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author fuyunjia
 * @Date 2024-04-02 17:06
 */
@Data
public class OrderCountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单数量")
    private Integer orderCount;

    @ApiModelProperty(value = "日期")
    private String orderDate;





}
