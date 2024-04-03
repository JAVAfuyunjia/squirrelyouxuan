package com.camellia.squirrelyouxuan.vo.order;

import com.baomidou.mybatisplus.annotation.TableField;
import com.camellia.squirrelyouxuan.enums.OrderStatus;
import com.camellia.squirrelyouxuan.enums.ProcessStatus;
import com.camellia.squirrelyouxuan.model.order.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author fuyunjia
 * @Date 2024-03-05 9:53
 */
@Data
@ApiModel(description = "OrderInfoVo")
public class OrderInfoVo implements Serializable {
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

    @ApiModelProperty(value = "运费")
    @TableField("feight_fee")
    private BigDecimal feightFee;

    @ApiModelProperty(value = "减免运费")
    @TableField("feight_fee_reduce")
    private BigDecimal feightFeeReduce;

    @ApiModelProperty(value = "可退款日期（签收后1天）")
    @TableField("refundable_time")
    private Date refundableTime;

    @ApiModelProperty(value = "支付方式【1->微信】")
    @TableField("pay_type")
    private Integer payType;

    @ApiModelProperty(value = "订单来源[0->小程序；1->H5]")
    @TableField("source_type")
    private Integer sourceType;

    @ApiModelProperty(value = "订单状态【0->待付款；1->待发货；2->待收货；3->待用户收货，已完成；-1->已取消】")
    @TableField("order_status")
    private OrderStatus orderStatus;

    @ApiModelProperty(value = "进度状态")
    @TableField("process_status")
    private ProcessStatus processStatus;


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

    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("payment_time")
    private Date paymentTime;

    @ApiModelProperty(value = "发货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("delivery_time")
    private Date deliveryTime;

    @ApiModelProperty(value = "提货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("take_time")
    private Date takeTime;

    @ApiModelProperty(value = "确认收货时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("receive_time")
    private Date receiveTime;

    @ApiModelProperty(value = "订单备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "取消订单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("cancel_time")
    private Date cancelTime;

    @ApiModelProperty(value = "取消订单原因")
    @TableField("cancel_reason")
    private String cancelReason;


    @ApiModelProperty(value = "订单项列表")
    @TableField(exist = false)
    private List<OrderItem> orderItemList;
}
