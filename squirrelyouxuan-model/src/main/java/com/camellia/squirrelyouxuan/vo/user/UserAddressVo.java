package com.camellia.squirrelyouxuan.vo.user;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 收货地址表
 * </p>
 *
 * @author fuyunjia
 * @since 2024-02-26
 */
@Data
@ApiModel(description = "UserAddress")
public class UserAddressVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("会员ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("收货地址(省市区)")
    @TableField("address")
    private String address;

    @ApiModelProperty("详细收货地址")
    @TableField("address_detail")
    private String addressDetail;


    @ApiModelProperty("收货人姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("收货人电话")
    @TableField("phone")
    private String phone;

}
