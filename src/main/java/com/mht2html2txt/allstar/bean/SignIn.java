package com.mht2html2txt.allstar.bean;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录成功信息实体
 * 
 * @author admin
 *
 */
@Data
public class SignIn extends Account {
	/**
	 * 令牌
	 */
	@NotNull(message = "缺少令牌")
	@ApiModelProperty(value = "令牌")
	private String token;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SignIn [token=").append(token).append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}

}