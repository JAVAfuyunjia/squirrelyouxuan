package com.camellia.squirrelyouxuan.model.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.camellia.squirrelyouxuan.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Category")
@TableName("category")
public class Category extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "分类名称")
	@TableField("name")
	private String name;

	@ApiModelProperty(value = "图标")
	@TableField("img_url")
	private String imgUrl;

	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;

}