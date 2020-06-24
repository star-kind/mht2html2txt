package com.mht2html2txt.allstar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <b>@ConditionalOnProperty</b><br>
 * 这个注解能够控制某个configuration是否生效。<br>
 * <i>具体操作是通过其两个属性name以及havingValue来实现的，</i><br>
 * 其中name用来从application.properties中读取某个属性值，如果该值为空，则返回false;<br>
 * 如果值不为空， 则将该值与havingValue指定的值进行比较，如果一样则返回true;否则返回false。<br>
 * 如果返回值为false，则该configuration不生效；为true则生效。
 * 
 * @author admin
 *
 */
@Configuration // 让spring加载该类
@EnableSwagger2 // 启动swagger
@ConditionalOnProperty(value = "swagger.enable", havingValue = "true", matchIfMissing = false)
public class Swagger2 {
	/**
	 * 可以获取属性文件中对应的值（如果属性文件中没有这个属性，则会报错。可以通过赋予默认值解决这个问题
	 */
	@Value("${swagger.enable:false}")
	private boolean enableSwagger;

	/**
	 * 可以获取属性文件中对应的值（如果属性文件中没有这个属性，则会报错。可以通过赋予默认值解决这个问题
	 */
	@Value("${swagger.basePackage:com.mht2html2txt.allstar.controller}")
	private String basePackage;

	@Bean
	public Docket createRestApi() {// 创建API基本信息
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).enable(enableSwagger).select()
				// 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
				.apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build();
	}

	/**
	 * 创建API的基本信息，这些信息会在Swagger UI中进行显示
	 * 
	 * @return
	 */
	private ApiInfo apiInfo() {
		Contact contact = new Contact("yuanjingpeng", "https://gitee.com/yuanjingpeng/mht2html2txt.git",
				"duernna@163.com");
		return new ApiInfoBuilder().title("MHT2HTML2TXT系统端口")// API 标题
				.description("维护在springboot中的api")// API描述
				.contact(contact)// 联系人
				.version("1.0")// 版本号
				.build();
	}
}