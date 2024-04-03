package com.camellia.squirrelyouxuan.vo.order;

import com.camellia.squirrelyouxuan.enums.OrderStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author fuyunjia
 * @Date 2024-03-25 17:00
 */

@Data
public class OrderUserQueryVo {

	private Long userId;

	@ApiModelProperty(value = "订单状态")
	private OrderStatus orderStatus;

}

