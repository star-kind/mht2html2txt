package com.mht2html2txt.allstar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置类，启动的时候加上静态文件
 * 
 * @author admin
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
	/**
	 * 这里配置静态资源文件的路径,导包都是默认的,直接导入就可以
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
		super.addResourceHandlers(registry);
	}

}