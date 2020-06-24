package com.mht2html2txt.allstar.test.service;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mht2html2txt.allstar.bean.Account;
import com.mht2html2txt.allstar.bean.SignIn;
import com.mht2html2txt.allstar.service.IAccountService;
import com.mht2html2txt.allstar.service.exception.ServiceException;

/**
 * 
 * @author admin
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest {
	@Autowired
	private IAccountService ias;

	@Test
	public void regTest() {
		Account a = new Account();
		a.setMailbox("10015315131@qq.cn");
		a.setNickname("arrays1011");
		a.setPassword("1234");

		try {
			Integer affect = ias.regAccount(a);
			System.err.println("==" + affect);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void loginTest() {
		try {
			SignIn signin = ias.signin("115315131@qq.cn", "12340");
			System.err.println(signin);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}
}
