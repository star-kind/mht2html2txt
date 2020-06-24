<<<<<<< HEAD
# mht2html2txt

#### 简介
将手机UC浏览器保存下来的离线小说网页,转换成TXT文本文件


---------------------------------------

	感言:
	依靠本项目,某积累和掌握了AOP面向切面编码,及配置监听器/过滤器的一些经验与技巧,除此以外还含有Derby,swagger;但因为某偶获现存替代品,所以本项目的开发需求已不复存在,于是草草终止.


---------------------------------------

#### 软件架构
软件基本架构

- 后端框架: SpringBoot
- Java版本: JDK 1.8
- 前端脚本语言: JavaScript+Vue.js
- UI框架: BootStrap
- 数据库: Derby(嵌入式数据库)

#### 开发环境
- linux

-----------------------------

## 辅助技术
#### 数据缓存
redis

#### 日志插件
log4j2+Slf4j

#### 跨域身份验证
jwt

#### API规范服务
swagger

swagger访问之URL:
http://localhost:8080/mht2html2txt/swagger-ui.html

#### 弹出层框架
layui

------------------------------

流程:
1.从前台传文件夹完整路径名至后台,交与后台细化处理

------------------------------

* 基于注解实现监听器+过滤器
* 基于AOP注解加自定义注解实现拦截器01
* 基于AOP对接口参数加密及解密
* 基于注解实现拦截器02:interceptor(未实行)

------------------------------

问题与小结:

+ Around注解会使ExceptionHandler注解无效化
	
	reason: around率先结束,优先级高于ExceptionHandler,且使用around标注的方法返回值如若为void,则response直接为返回NULL
=======
# mht2html2txt
>>>>>>> fec6096b9620b6abe00da7d53a4df3a788094fbd
