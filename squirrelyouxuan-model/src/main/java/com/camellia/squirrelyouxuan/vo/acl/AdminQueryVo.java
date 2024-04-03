//
//
package com.camellia.squirrelyouxuan.vo.acl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户查询实体
 * </p>
 *
 * @Author fuyunjia
 * @Date 2023-11-04 11:09
 */
@Data
@ApiModel(description = "用户查询实体")
public class AdminQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "昵称")
	private String name;

}

