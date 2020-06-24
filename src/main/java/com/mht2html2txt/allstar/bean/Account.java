package com.mht2html2txt.allstar.bean;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("t_account")
public class Account {
	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "帐号ID")
	private Integer id;

	/**
	 * 昵称
	 */
	@NotNull(message = "缺少昵称")
	@ApiModelProperty(value = "昵称")
	private String nickname;

	/**
	 * 昵称
	 */
	@NotNull(message = "缺少密码`")
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 昵称
	 */
	@NotNull(message = "缺少盐值")
	@ApiModelProperty(value = "盐值")
	private String salt;

	/**
	 * 昵称
	 */
	@NotNull(message = "缺少注册时间")
	@ApiModelProperty(value = "注册时间")
	private LocalDateTime regTime;

	/**
	 * 昵称
	 */
	@NotNull(message = "缺少邮箱")
	@ApiModelProperty(value = "邮箱")
	private String mailbox;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Account [id=").append(id).append(", nickname=").append(nickname).append(", password=")
				.append(password).append(", salt=").append(salt).append(", regTime=").append(regTime)
				.append(", mailbox=").append(mailbox).append("]");
		return builder.toString();
	}

}