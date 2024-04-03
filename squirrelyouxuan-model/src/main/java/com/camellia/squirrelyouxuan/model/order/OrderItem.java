package com.camellia.squirrelyouxuan.model.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.camellia.squirrelyouxuan.enums.SkuType;
import com.camellia.squirrelyouxuan.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(description = "OrderItem")
@TableName("order_item")
public class OrderItem extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "order_id")
	@TableField("order_id")
	private Long orderId;

	@ApiModelProperty(value = "商品分类id")
	@TableField("category_id")
	private Long categoryId;

	@ApiModelProperty(value = "商品类型：0->普通商品 1->秒杀商品")
	@TableField("sku_type")
	private SkuType skuType;

	@ApiModelProperty(value = "商品skuID")
	@TableField("sku_id")
	private Long skuId;

	@ApiModelProperty(value = "商品sku名字")
	@TableField("sku_name")
	private String skuName;

	@ApiModelProperty(value = "商品sku图片")
	@TableField("img_url")
	private String imgUrl;

	@ApiModelProperty(value = "商品sku价格")
	@TableField("sku_price")
	private BigDecimal skuPrice;

	@ApiModelProperty(value = "商品购买的数量")
	@TableField("sku_num")
	private Integer skuNum;

	@ApiModelProperty(value = "该商品金额")
	@TableField("total_amount")
	private BigDecimal totalAmount;


}