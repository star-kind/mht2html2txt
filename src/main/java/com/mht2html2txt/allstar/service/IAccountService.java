package com.mht2html2txt.allstar.service;

import com.mht2html2txt.allstar.bean.Account;
import com.mht2html2txt.allstar.bean.SignIn;
import com.mht2html2txt.allstar.service.exception.ServiceException;

/**
 * 
 * @author admin
 *
 */
public interface IAccountService {
	/**
	 * 登录
	 * 
	 * @param mailbox 邮箱,即登录所用之账号
	 * @param keyword
	 * @return
	 * @throws ServiceException
	 */
	SignIn signin(String mailbox, String keyword) throws ServiceException;

	/**
	 * 
	 * @param account
	 * @return
	 * @throws ServiceException
	 */
	Integer regAccount(Account account) throws ServiceException;

	/**
	 * 
	 * @throws Exception
	 */
	public void createTableIfNotExist() throws Exception;
}