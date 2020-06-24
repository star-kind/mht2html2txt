package com.mht2html2txt.allstar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mht2html2txt.allstar.controller.kit.BaseActionUtil;
import com.mht2html2txt.allstar.response.Result;
import com.mht2html2txt.allstar.service.exception.ServiceException;
import com.mht2html2txt.allstar.service.exception.ServiceExceptionEnum;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author admin
 *
 */
@Slf4j
@Controller
@RequestMapping("/TransferController")
public class TransferController extends BaseActionUtil {
	@Override
	protected String parameterMark(Object... args) {
		return super.parameterMark(args);
	}

	/**
	 * http://localhost:8080/mht2html2txt/TransferController/jumpToAction/{htmlIndex}
	 * 
	 * @return
	 */
	@RequestMapping("jumpToAction/{htmlIndex}")
	@ApiOperation(value = "页面路径跳转接口", notes = "页面路径跳转", response = String.class)
	@ApiImplicitParam(paramType = "query", name = "根据URL所带值跃迁", value = "页面索引数值", required = true)
	public String jumpToAction(@PathVariable("htmlIndex") Integer htmlIndex) {
		parameterMark("htmlIndex====" + htmlIndex);
		String index = getHtmlNameByIndex(htmlIndex);
		return index;
	}

	/**
	 * 测试抛出异常<br>
	 * 
	 * http://localhost:8080/mht2html2txt/TransferController/testAction01?param=0
	 * 
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "testAction01", method = RequestMethod.GET)
	public Result<HashMap<Integer, String>> testAction01(@RequestParam("param") Integer param) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		map.put(0, this.getClass().getCanonicalName());
		map.put(1, this.getClass().getSimpleName());
		map.put(2, this.getClass().getTypeName());
		map.put(3, this.getClass().getName());
		map.put(4, this.getNowTime());

		if (param == -1) {
			String description = ServiceExceptionEnum.NETWORK_ERROR.getDescription();
			System.err.println("description==" + description);
			throw new ServiceException(description);
		}

		return new Result<HashMap<Integer, String>>(SUCCESS, map);
	}

	/**
	 * http://localhost:8080/mht2html2txt/TransferController/testAction
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("testAction")
	public Map<String, Object> testAction() {
		log.info(this.getClass().getSimpleName());

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("declaredConstructors", this.getClass().getDeclaredConstructors());
		map.put("clzName", this.getClass().getName());
		map.put("nowTime", TransferController.now_time);

		return map;
	}

	/**
	 * http://localhost:8080/mht2html2txt/TransferController/indexAction
	 * 
	 * @return
	 */
	@RequestMapping("indexAction")
	public String indexAction() {
		String clzName = this.getClass().getName();
		log.info(clzName);
		log.info("indexAction");
		return "index";
	}

	/**
	 * 
	 * @param index
	 * @return
	 */
	public String getHtmlNameByIndex(Integer index) {
		String htmlName = "";

		switch (index) {
		case 0:
			htmlName = "Regist";
			break;
		case 1:
			htmlName = "SignIn";
			break;
		case 2:
			htmlName = "index";
			break;
		case 3:
			htmlName = "MineBackgroundManage";
			break;
		}

		return htmlName;
	}

}
