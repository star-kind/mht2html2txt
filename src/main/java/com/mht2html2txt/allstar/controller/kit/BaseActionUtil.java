package com.mht2html2txt.allstar.controller.kit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mht2html2txt.allstar.service.exception.ServiceException;
import com.mht2html2txt.allstar.service.exception.ServiceExceptionEnum;
import com.mht2html2txt.allstar.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 控制器工具基类
 * 
 * @author admin @ControllerAdvice：包含@Component。可以被扫描到。统一处理异常。
 *
 */
@Slf4j
public class BaseActionUtil {
	/**
	 * "成功"
	 */
	public static final Integer SUCCESS = 200;

	/**
	 * 日志目录实际路径: "/home/admin/workspace/temporary-logs/Attendance"
	 */
	public final static String ENGINE_DAILY_PATH = "/home/admin/workspace/temporary-logs/Mht2html2txt";

	/**
	 * 当前时间
	 */
	public static String now_time = null;

	static {
		now_time = getNowTime();
	}

	/**
	 * 换行分隔符+p闭合标签,后缀
	 */
	protected static final String P_TAG_SUFFIX = System.getProperty("line.separator") + "</p>";

	/**
	 * p标签,前缀
	 */
	public static String p_tag_prefix = "<p>";

	/**
	 * the base builder of P_TAG_PREFIX
	 */
	public static StringBuilder base_builder = new StringBuilder();

	/**
	 * 文件输出流
	 */
	protected static FileOutputStream fos = null;

	/**
	 * 二进制数组,充作缓冲对象
	 */
	protected static byte[] buff = null;

	/**
	 * 切口,验证前端发来的令牌
	 * 
	 * @param jwt token
	 * @return 解析后的令牌散列表
	 */
	public Map<String, Object> verifyFrontToken(String jwt) {
		JwtUtil util = new JwtUtil();

		Map<String, Object> map = util.parseJavaWebToken(jwt);
		if (map == null) {
			String description = ServiceExceptionEnum.OFFLINE_LOGIN.getDescription();
			log.error(description);
			throw new ServiceException(description);
		}

		return map;
	}

	/**
	 * 获取session.attribute(Object),充当切面
	 * 
	 * @param session
	 * @return
	 * @throws ServiceException
	 */
	public Integer getAccidFromSession(HttpSession session) throws ServiceException {
		Integer accid = null;
		int anchor = 0;

		try {
			accid = Integer.parseInt(session.getAttribute("accid").toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName());
			anchor = 1;
		}

		if (anchor == 1) {
			String description = ServiceExceptionEnum.OFFLINE_LOGIN.getDescription();
			log.error(description);
			throw new ServiceException(description);
		}

		return accid;
	}

	/**
	 * 获取session.attribute(Object),充当切面
	 * 
	 * @param session
	 * @return
	 * @throws ServiceException
	 */
	public Integer getMailboxFromSession(HttpSession session) throws ServiceException {
		int anchor = 0;
		Integer mailbox = null;

		try {
			mailbox = Integer.parseInt(session.getAttribute("mailbox").toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName());
			anchor = 1;
		}

		if (anchor == 1) {
			String description = ServiceExceptionEnum.OFFLINE_LOGIN.getDescription();
			log.error(description);
			throw new ServiceException(description);
		}

		return mailbox;
	}

	/**
	 * 
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = format.format(new Date());

		return dateStr;
	}

	/**
	 * 创建文件夹和文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String createFolderAndFile(String fileName) throws IOException {
		File file = new File(ENGINE_DAILY_PATH);

		if (!file.exists()) {
			file.mkdir();
		}

		File fi = new File(file, fileName);
		fi.createNewFile();

		String path = fi.getAbsolutePath();
		return path;
	}

	/**
	 * 把记录写入日志文件(有单行判断条件)
	 * 
	 * @param affect   受影响行数
	 * @param fileName
	 * @param sentence
	 */
	public void writeToFile(Integer affect, String fileName, String sentence) {
		String filePath = null;

		try {
			filePath = createFolderAndFile(fileName);
			System.err.println("File path:" + filePath);

		} catch (IOException e) {
			e.printStackTrace();
		}

		if (affect == 1) {
			executeWrite(sentence, filePath);
		}
	}

	/**
	 * 把记录写入日志文件 <br>
	 * <b>Overload</b>
	 * 
	 * @param fileName
	 * @param sentence
	 */
	protected void writeToFile(String fileName, String sentence) {
		String filePath = null;

		try {
			filePath = createFolderAndFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		executeWrite(sentence, filePath);
	}

	/**
	 * 执行写入具体方法
	 * 
	 * @param record
	 * @param filePath
	 */
	public void executeWrite(String record, String filePath) {
		try {
			fos = new FileOutputStream(filePath, true);
			buff = record.getBytes();
			fos.write(buff);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 参数标记,即<b>打桩</b>
	 * 
	 * @param args
	 */
	protected String parameterMark(Object... args) {
		StringBuffer buffer = new StringBuffer();
		LinkedList<Object> list = new LinkedList<Object>();
		String clzName = this.getClass().getName();

		for (int i = 0; i < args.length; i++) {
			list.add(args[i]);
		}

		System.err.println(clzName + ".Received parameters from front:");
		buffer.append(clzName + "\n");
		buffer.append("Received parameters from front:\n");

		for (Object object : list) {
			buffer.append("\n");
			buffer.append(object);
		}

		log.info(buffer.toString());
		return buffer.toString();
	}

}
