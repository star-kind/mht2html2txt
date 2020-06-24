package com.mht2html2txt.allstar.service.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义之业务异常之枚举
 * 
 * @author admin
 *
 */
@Slf4j
public enum ServiceExceptionEnum {
	/** 此名字被其他用户使用 */
	USERNAME_HAS_EXISTED(4012, "此名字被其他用户使用"),
	/** 网络繁忙,请稍候再重新注册账户 */
	REGIST_FAILED(4011, "网络繁忙,请稍候再重新注册账户"),
	/** 该邮箱地址已被其他用户注册 */
	MAILBOX_HAS_EXISTED(4010, "该邮箱地址已被其他用户使用"),
	/** 您已下线,请重新登录 */
	OFFLINE_LOGIN(4009, "您已下线,请重新登录"),
	/** "密码错误,请检查密码无误后再登录" */
	KEYWORD_ERR(4008, "密码错误,请检查密码无误后再登录"),
	/** "未寻获此帐号,无此用户" */
	ACCOUNT_NOT_FOUND(4007, "未寻获此帐号,无此用户"),
	/** 网络错误,请稍后重试 */
	NETWORK_ERROR(9999, "网络错误,请稍后重试"),
	/** 实例对象 */
	INSTANCE;

	private Integer code;
	private String description;

	private ServiceExceptionEnum() {
	}

	private ServiceExceptionEnum(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 根据code获取description
	 * 
	 * @param code
	 * @return
	 */
	public String getDescByCode(Integer code) {
		for (ServiceExceptionEnum element : ServiceExceptionEnum.values()) {
			if (code == element.code) {
				return element.description;
			}
		}

		return null;
	}

	/**
	 * 根据description获取code
	 * 
	 * @param description
	 * @return
	 */
	public Integer getCodeByDesc(String description) {
		System.err.println(this.getClass().getName() + "\n传入之description==" + description);
		log.info("\n传入之description==" + description);
		for (ServiceExceptionEnum element : ServiceExceptionEnum.values()) {
			if (description.equals(element.description)) {
				return element.code;
			}
		}

		return null;
	}
}