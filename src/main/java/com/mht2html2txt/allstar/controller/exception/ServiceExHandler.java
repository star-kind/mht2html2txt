package com.mht2html2txt.allstar.controller.exception;

import org.springframework.web.bind.annotation.ResponseBody;
import com.mht2html2txt.allstar.response.Result;
import com.mht2html2txt.allstar.service.exception.ServiceExceptionEnum;

import lombok.extern.slf4j.Slf4j;

/**
 * <b>Around注解会使ExceptionHandler注解无效化</b><br>
 * <br>
 * 本类配合切面接口之环绕通知方法抛出自定义业务异常
 * 
 * @author admin
 *
 */
@Slf4j
public class ServiceExHandler {
	/**
	 * 根据信息字符串返还异常封装实体
	 * 
	 * @param errMsg
	 * @return
	 */
	@ResponseBody
	public Result<Void> getServiceExceptionByMsg(String errMsg) {
		Result<Void> result = new Result<Void>();
		ServiceExceptionEnum instance = ServiceExceptionEnum.INSTANCE;

		log.info("\n LocalizedMessage: \n" + errMsg);
		System.err.println(this.getClass().getName() + "\n=>ERR Msg==" + errMsg);

		Integer code = instance.getCodeByDesc(errMsg);
		log.error("\n自定义异常详情\nCode:{},\nMessage:{}\n", code, errMsg);

		result.setStatus(code);
		result.setMessage(errMsg);
		return result;
	}

}