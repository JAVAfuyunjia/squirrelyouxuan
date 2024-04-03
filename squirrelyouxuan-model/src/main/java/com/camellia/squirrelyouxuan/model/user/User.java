package com.camellia.squirrelyouxuan.model.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.camellia.squirrelyouxuan.enums.UserType;
import com.camellia.squirrelyouxuan.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "User")
@TableName("user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableField("user_type")
	private UserType userType;

	@ApiModelProperty(value = "会员头像")
	@TableField("photo_url")
	private String photoUrl;

	@ApiModelProperty(value = "昵称")
	@TableField("nick_name")
	private String nickName;


	@ApiModelProperty(value = "电话号码")
	@TableField("phone")
	private String phone;


	@ApiModelProperty(value = "小程序open id")
	@TableField("open_id")
	private String openId;



}