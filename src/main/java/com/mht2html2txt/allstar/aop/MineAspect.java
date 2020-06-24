package com.mht2html2txt.allstar.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mht2html2txt.allstar.controller.exception.ServiceExHandler;

/**
 * 拦截器
 * 
 * @author admin
 *
 */
@Aspect
@Component
public class MineAspect {
	private final static Logger log = LoggerFactory.getLogger(MineAspect.class);

	/**
	 * 定义切点位置:下面如果你在SSM中用AOP，在xml中配的就是下面
	 */
	@Pointcut("execution(* com.mht2html2txt.allstar.controller..*.*(..))")
	public void performance() {
	}

	/**
	 * 环绕通知:aop的最重要,最常用的注解
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("performance()")
	public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("\n进入环绕通知\n");
		System.err.println("进入环绕通知");
		// 记录起始时间
		long begin = System.currentTimeMillis();
		Object result = null;
		ServiceExHandler exHandler = new ServiceExHandler();

		/* 执行目标方法 */
		try {
			result = joinPoint.proceed();
			System.err.println("未出现异常,正常返回");
			log.info("\n未出现异常,正常返回,\n===>" + result);
		} catch (Exception e) {
			String errMsg = e.getLocalizedMessage();
			log.error("\n日志记录发生错误, errorMessage: {}\n", errMsg);
			System.err.println("LocalizedMessage==" + errMsg);
			result = exHandler.getServiceExceptionByMsg(errMsg);// TODO 抛出业务异常
		} finally {
			/** 记录操作时间 */
			long took = (System.currentTimeMillis() - begin) / 1000;
			log.info("\ncontroller执行时间为(execute Time==): {}秒\n", took);
		}
		log.info("\ndoAround.result==[\n" + result + "\n]");
		log.info("\n离开环绕通知\n");
		System.err.println("离开环绕通知");
		return result;
	}

	/**
	 * 前置通知
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("performance()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		log.info("\ndoBefore():前置通知\n");
		log.info("\n接收到请求,记录请求内容..\n");
	}

	/**
	 * 
	 */
	@After("performance()")
	public void doAfter() {
		String simpleName = this.getClass().getSimpleName();
		log.info("\n" + simpleName + "的doAfter()方法:后置通知[After advice]");
	}

}