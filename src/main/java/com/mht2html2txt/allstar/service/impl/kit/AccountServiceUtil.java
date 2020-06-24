package com.mht2html2txt.allstar.service.impl.kit;

import java.util.UUID;

import org.springframework.util.DigestUtils;

import com.mht2html2txt.allstar.service.exception.ServiceException;
import com.mht2html2txt.allstar.service.exception.ServiceExceptionEnum;

import cn.hutool.log.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author admin
 *
 */
@Slf4j
public class AccountServiceUtil {
	private static AccountServiceUtil instance = new AccountServiceUtil();

	private AccountServiceUtil() {
		log.info("私有化构造方法,防止被实例化...");
	}

	/**
	 * 饿汉式单例
	 * 
	 * @return
	 */
	public static AccountServiceUtil getInstance() {
		return instance;
	}

	/**
	 * 获取盐值
	 * 
	 * @return
	 */
	public String getSalt() {
		String uuid = UUID.randomUUID().toString();
		log.info("SALT === " + uuid);

		return uuid;
	}

	/**
	 * 获取加工好的密文
	 * 
	 * @param salt
	 * @param originalPwd
	 */
	public String getKwdText(String salt, String originalPwd) {
		// 加密规则:在密码左右两边拼接一次盐值
		String text = salt + originalPwd + salt;

		// 将最终拼接完成的密码通过for循环进行md5加密5次
		for (int i = 0; i < 9; i++) {
			text = DigestUtils.md5DigestAsHex(text.getBytes());
		}
		log.info("加工后的密文===" + text);
		return text;
	}

	/**
	 * 
	 * @param signInWord
	 * @param salt
	 * @param databasePwd
	 * @return
	 */
	public void verifyKwd(String signInWord, String salt, String databasePwd) {
		String kwdText = getKwdText(salt, signInWord);

		log.info("database's password===" + databasePwd);
		log.info("signIn's password===" + kwdText);

		if (!databasePwd.equals(kwdText)) {
			String description = ServiceExceptionEnum.KEYWORD_ERR.getDescription();
			log.warn(description);
			throw new ServiceException(description);
		}
	}
}