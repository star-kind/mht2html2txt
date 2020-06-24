package com.mht2html2txt.allstar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * 视图解析器Configuration
 * 
 * @author admin
 *
 */
@Configuration
public class MVCAdapter implements WebMvcConfigurer {
	/**
	 * 以/开头，解析到/templates/Htmls/
	 * 
	 * @return
	 */
	@Bean
	public ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setPrefix("classpath:/templates/Htmls/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("utf-8");
		templateResolver.setCacheable(false);
		return templateResolver;
	}

	/**
	 * 以/开头，解析到/templates/front/Htmls/
	 * 
	 * @return
	 */
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		return templateEngine;
	}

	/**
	 * 以/开头，解析到/templates/Htmls/
	 * 
	 * @return
	 */
	@Bean
	public ThymeleafViewResolver viewResolverThymeLeaf() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("utf-8");
		viewResolver.setViewNames(new String[] { "/*" });
		viewResolver.setOrder(1);
		return viewResolver;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}