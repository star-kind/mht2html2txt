package com.mht2html2txt.allstar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @SpringBootApplication： 申明让spring boot自动给程序进行必要的配置，这个配置等同于：
 * 
 * <ul>
 * <li>@Configuration</li>
 * <li>@EnableAutoConfiguration</li>
 * <li>@ComponentScan</li>
 * </ul>
 * 
 * 三个配置。
 * 
 * @author admin
 *
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = { "com.mht2html2txt.allstar.mapper" })
@ComponentScan(basePackages = { "com.mht2html2txt.allstar.*" })
//配置有ControllerAdvice或RestControllerAdvice注解的类之直属包应当被注明扫描
@SpringBootApplication(scanBasePackages = { "com.mht2html2txt.allstar",
		"com.mht2html2txt.allstar.controller.exception" })
@ServletComponentScan({ "com.mht2html2txt.allstar.filter", "com.mht2html2txt.allstar.listener" })
public class EnterpriseApplication extends SpringBootServletInitializer {
	public EnterpriseApplication() {
		super();
		setRegisterErrorPageFilter(false);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(EnterpriseApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(EnterpriseApplication.class, args);
	}
}
