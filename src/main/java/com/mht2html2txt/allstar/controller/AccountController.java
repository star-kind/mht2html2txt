package com.mht2html2txt.allstar.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mht2html2txt.allstar.bean.Account;
import com.mht2html2txt.allstar.bean.SignIn;
import com.mht2html2txt.allstar.controller.kit.BaseActionUtil;
import com.mht2html2txt.allstar.response.Result;
import com.mht2html2txt.allstar.service.IAccountService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.json.Json;

/**
 * 接收前台递与之数据之控制器
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping("/AccountController")
@Slf4j
public class AccountController extends BaseActionUtil {
	@Autowired
	private IAccountService ias;

	@Override
	protected String parameterMark(Object... args) {
		return super.parameterMark(args);
	}

	/**
	 * http://localhost:8080/mht2html2txt/AccountController/signinAction?mailbox=xxxvvv&password=01234
	 * 
	 * @param mailbox
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "signinAction", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录接口", notes = "用户登录", response = Json.class)
	@ApiImplicitParam(paramType = "query", name = "用户登录", value = "邮箱(mailbox)+密码(password)", required = true)
	public Result<SignIn> signinAction(@RequestParam("mailbox") String mailbox,
			@RequestParam("password") String password) {
		parameterMark(mailbox + " , " + password);

		SignIn signin = ias.signin(mailbox, password);

		return new Result<SignIn>(SUCCESS, signin);
	}

	/**
	 * http://localhost:8080/mht2html2txt/AccountController/regAccountAction
	 * 
	 * @param account
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "regAccountAction", method = RequestMethod.POST)
	@ApiOperation(value = "用户注册接口", notes = "新用户注册", response = Json.class)
	@ApiImplicitParam(paramType = "body", name = "注册新用户", value = "邮箱(mailbox)+昵称(nickname)+密码(password)", required = true)
	public Result<Integer> regAccountAction(HttpServletRequest request, @RequestBody Account account) {
		parameterMark(account);

		String header = request.getHeader("head_message");
		log.info("\n" + header);

		String parameter = request.getParameter("parameter00");
		if (parameter != null) {
			log.info("\n" + parameter);
		}

		String mailbox = request.getParameter("mailbox");
		if (mailbox != null) {
			log.info("\n" + mailbox);
		}

		Integer affects = ias.regAccount(account);
		return new Result<Integer>(SUCCESS, affects);
	}
}