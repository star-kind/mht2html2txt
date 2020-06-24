package com.mht2html2txt.allstar.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author admin
 * @description 与拦截器结合使用,验证权限
 */
@Target({ ElementType.TYPE, ElementType.METHOD }) // ElementType.TYPE,ElementType.METHOD表示注解可标记类和方法
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
	String value();
}