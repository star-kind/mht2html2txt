package com.mht2html2txt.allstar.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.mht2html2txt.allstar.service.IAccountService;

/**
 * 监听spring boot的启动
 * 
 * @author admin
 *
 */
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	private IAccountService iAccountService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		initCreateDbTable();
	}

	/**
	 * 如果数据库表不存在，则创建表
	 */
	private void initCreateDbTable() {
		try {
			iAccountService.createTableIfNotExist();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}