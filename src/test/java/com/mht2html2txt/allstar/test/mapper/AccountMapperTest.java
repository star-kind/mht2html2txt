package com.mht2html2txt.allstar.test.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mht2html2txt.allstar.bean.Account;
import com.mht2html2txt.allstar.mapper.AccountMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountMapperTest {
	@Autowired
	private AccountMapper iam;

	@Test
	public void updateByIdTest() {
		Account account = new Account();
		account.setMailbox("shellock_4548@163.com");
		account.setNickname("refID-4612");
		account.setPassword("555888");
		account.setSalt("qwerty");

		Integer affect = iam.updateByID(account, 1);
		System.err.println("====" + affect);
	}

	@Test
	public void selectTest() {
		Account account = iam.selectByID(1);
		System.err.println("==>" + account);
	}

	@Test
	public void selectClauseTest() {
		Account account = iam.selectColEqVal("nickname", "rpaSOSren");
		System.err.println("==>" + account);
	}

	@Test
	public void selectByMailboxTest() {
		Account account = iam.selectByMailbox("6916316315@126.cn");
		System.err.println("==>" + account);
	}

	@Test
	public void selectsAllTest() {
		List<Account> accounts = iam.selectAllAccounts();
		for (Account account : accounts) {
			System.err.println(account.toString());
		}
	}

	@Test
	public void createdTest() {
		iam.createTblAccount();
	}

	@Test
	public void deleTest() {
		Integer affects = iam.delTblAccount();
		System.err.println("==>" + affects);
	}

	@Test
	public void insertsTest() {
		Account a = new Account();
		a.setMailbox("6916316315@126.cn");
		a.setNickname("rpaSOSren");
		a.setPassword("01234");
		a.setSalt("xxxyyyzzzkkkllll");
		a.setRegTime(LocalDateTime.now());

		Integer affects = iam.saveAccount(a);
		System.err.println("==>" + affects);
	}
}
