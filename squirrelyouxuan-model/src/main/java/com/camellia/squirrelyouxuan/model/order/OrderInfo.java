package com.camellia.squirrelyouxuan.model.order;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.camellia.squirrelyouxuan.enums.OrderStatus;
import com.camellia.squirrelyouxuan.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(description = "OrderInfo")
@TableName("order_info")
public class OrderInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "微信用户_id")
	@TableField("user_id")
	private Long userId;


	@ApiModelProperty(value = "订单号")
	@TableField("order_no")
	private String orderNo;

	@ApiModelProperty(value = "原价金额")
	@TableField("original_total_amount")
	private BigDecimal originalTotalAmount;

	@ApiModelProperty(value = "订单状态【0->待付款；1->待发货；2->待收货；3->已完成；-1->已取消】")
	@TableField("order_status")
	private OrderStatus orderStatus;

	@ApiModelProperty(value = "收货地址")
	@TableField("take_name")
	private String takeName;

	@ApiModelProperty(value = "收货人姓名")
	@TableField("receiver_name")
	private String receiverName;

	@ApiModelProperty(value = "收货人电话")
	@TableField("receiver_phone")
	private String receiverPhone;

	@ApiModelProperty(value = "详细地址")
	@TableField("receiver_address")
	private String receiverAddress;

	@ApiModelProperty(value = "订单项列表")
	@TableField(exist = false)
	private List<OrderItem> orderItemList;

}