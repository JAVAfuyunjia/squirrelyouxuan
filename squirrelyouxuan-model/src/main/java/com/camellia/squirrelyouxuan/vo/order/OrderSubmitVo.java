package com.camellia.squirrelyouxuan.vo.order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author fuyunjia
 * @Date 2024-03-25 17:00
 */
@Data
public class OrderSubmitVo {

	@ApiModelProperty(value = "使用预生产订单号防重")
	private String orderNo;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "收货人姓名")
	private String receiverName;

	@ApiModelProperty(value = "收货人电话")
	private String receiverPhone;
	
}

