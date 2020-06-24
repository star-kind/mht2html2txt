package com.mht2html2txt.allstar.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import com.mht2html2txt.allstar.bean.Account;
import com.mht2html2txt.allstar.bean.SignIn;
import com.mht2html2txt.allstar.mapper.AccountMapper;
import com.mht2html2txt.allstar.service.IAccountService;
import com.mht2html2txt.allstar.service.exception.ServiceException;
import com.mht2html2txt.allstar.service.exception.ServiceExceptionEnum;
import com.mht2html2txt.allstar.service.impl.kit.AccountServiceUtil;
import com.mht2html2txt.allstar.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author admin
 *
 */
@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	private AccountMapper am;

	AccountServiceUtil instance = AccountServiceUtil.getInstance();

	@Override
	public void createTableIfNotExist() throws Exception {
		try {
			am.count();
		} catch (BadSqlGrammarException e) {
			// 42X05:表示表或视图不存在
			if (e.getSQLException().getSQLState().equals("42X05")) {
				try {
					am.createTblAccount();
				} catch (Exception e2) {
					log.error("创建表异常", e2);
				}
			} else {
				log.error("创建表未知异常", e);
			}
		} catch (Exception e) {
			log.error("创建表未知异常", e);
		}
	}

	@Override
	public Integer regAccount(Account account) throws ServiceException {
		// 检查昵称是否有重复
		Account account2 = am.selectColEqVal("nickname", account.getNickname());
		if (account2 != null) {
			String description = ServiceExceptionEnum.USERNAME_HAS_EXISTED.getDescription();
			log.warn(description);
			throw new ServiceException(description);
		}

		// 检查邮箱是否有重复
		Account account1 = am.selectByMailbox(account.getMailbox());
		if (account1 != null) {
			String description = ServiceExceptionEnum.MAILBOX_HAS_EXISTED.getDescription();
			log.warn(description);
			throw new ServiceException(description);
		}

		// 获取盐值
		String salt = instance.getSalt();

		// 获取加工后的密文
		String kwdText = instance.getKwdText(salt, account.getPassword());

		// 载入实体
		account.setPassword(kwdText);
		account.setSalt(salt);
		account.setRegTime(LocalDateTime.now());

		// 插入
		Integer effects = am.saveAccount(account);

		if (effects != 1) {
			String description = ServiceExceptionEnum.REGIST_FAILED.getDescription();
			log.warn(description);
			throw new ServiceException(description);
		}

		return effects;
	}

	@Override
	public SignIn signin(String mailbox, String keyword) throws ServiceException {
		Account account = am.selectByMailbox(mailbox);
		JwtUtil jwtUtil = new JwtUtil();
		HashMap<String, Object> map = new HashMap<String, Object>();
		SignIn signIn = new SignIn();

		if (account == null) {
			String description = ServiceExceptionEnum.ACCOUNT_NOT_FOUND.getDescription();
			log.warn(description);
			throw new ServiceException(description);
		}

		String databasePwd = account.getPassword();
		String salt = account.getSalt();
		instance.verifyKwd(keyword, salt, databasePwd);

		// TODO token
		map.put("id", account.getId());
		map.put("mailbox", account.getMailbox());
		map.put("nickname", account.getNickname());

		String token = jwtUtil.createJavaWebToken(map);

		signIn.setToken(token);
		signIn.setMailbox(mailbox);
		signIn.setId(account.getId());
		signIn.setNickname(account.getNickname());
		signIn.setRegTime(account.getRegTime());

		return signIn;
	}

}