package com.camellia.squirrelyouxuan.vo.order;

import com.camellia.squirrelyouxuan.model.order.CartInfo;
import com.camellia.squirrelyouxuan.vo.user.UserAddressVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author fuyunjia
 * @Date 2024-02-27 14:50
 */
@Data
public class OrderConfirmVo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "预生产订单号")
	private String orderNo;

	@ApiModelProperty(value = "用户对应的收货地址")
	private UserAddressVo userAddressVo;

	@ApiModelProperty(value = "购物项列表")
	private List<CartInfo> cartInfoList;

	@ApiModelProperty(value = "购物车原始总金额")
	private BigDecimal originalTotalAmount;


}

