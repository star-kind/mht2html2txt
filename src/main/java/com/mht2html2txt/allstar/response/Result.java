package com.mht2html2txt.allstar.response;

import java.io.Serializable;

import com.auth0.jwt.internal.com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 接口返回实体类
 * 
 * @author admin
 *
 */
@Getter
@Setter
@Data
@JsonSerialize
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer status;
	private String message;
	private T data;

	public Result(Integer status, T data) {
		this.status = status;
		this.data = data;
	}

	public Result(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Result() {

	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Result [status=").append(status).append(", message=").append(message).append(", data=")
				.append(data).append("]");
		return builder.toString();
	}

}