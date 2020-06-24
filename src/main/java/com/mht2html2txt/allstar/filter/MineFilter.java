package com.mht2html2txt.allstar.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author admin
 *
 */
@WebFilter(filterName = "MineFilter", urlPatterns = "/*")
@Slf4j
public class MineFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.info("\n此为吾之Filter过滤器\n>>==It's Mine Filter==<<");

		// 放行
		chain.doFilter(request, response);
	}

	/*
	 * 下面两个如果没有可以自己添加上，看源码，有这两个，只是不是必须的
	 */

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}