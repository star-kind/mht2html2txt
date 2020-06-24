package com.mht2html2txt.allstar.util;

import java.time.LocalDateTime;

/**
 * 工具超类
 * 
 * @author admin
 *
 */
public class BaseUtil {
	public static final String SOURCE_PRE_DIR = "/home/user/001/downloads/public/html2Txts";

	/**
	 * html网页文件素材所在上一目录<br>
	 * <strong>亦为新文件名</strong> <br>
	 * <strong>每次须亲自指定</strong>
	 */
	public static String html_dir = "/WuKongZuang";

	/**
	 * mht素材所在目录完整路径(不含文件名) <br>
	 * <strong>每次须亲自指定</strong>
	 */
	public static String mht_dir = SOURCE_PRE_DIR + "/00_stuff/WuKongZuang/6-19";

	/**
	 * 当地时间,用于缀在文本名末尾,防止文件名重复
	 */
	public static String local_time = LocalDateTime.now().toString();

	/**
	 * 生成的html素材文件名称 <br>
	 * <b>宜固定</b>
	 */
	public static final String STUFF_HTML_NAME = "/0000.html";

	/**
	 * HTML素材所在目录完整路径(不含文件名)<br>
	 * <b>宜固定</b>
	 */
	public static String html_destination = SOURCE_PRE_DIR + "/33_trans" + html_dir;

	/**
	 * 中转站(html to txt)<br>
	 * <b>宜固定</b>
	 */
	public static final String FILE_TRANS_STORE = SOURCE_PRE_DIR + "/66_transfer";

	/**
	 * 正则替换处理后,txt文本存放地(html to txt) <br>
	 * <b>宜固定</b>
	 */
	public static final String OUTPUT_TXT_DIR = SOURCE_PRE_DIR + "/999999";

	/**
	 * 新文本文件完整路径+文本文件名
	 */
	public static String txt_file_name = html_dir + "_" + local_time + ".TXT";

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static String replaceCharacters(String context) {
		context = context.replaceAll("&gt;", "").replaceAll("gt", "");
		context = context.replaceAll("amp;", "").replaceAll("C〇M", "");
		context = context.replaceAll("&amp;", "").replaceAll("nbsp;", "");
		context = context.replaceAll("-->", "").replaceAll("&amp", "");
		context = context.replaceAll("<--", "").replaceAll("quot;", "");
		context = context.replaceAll("&lt;", "").replaceAll("&lt;", "");
		context = context.replaceAll("/br", "").replaceAll("&", "");

		context = context.replaceAll("章节目录", "").replaceAll("正文内容", "").replaceAll("最新章节", "");

		for (int i = 1; i < 40; i++) {
			context = context.replaceAll("【" + i + "】", "");
		}

		return context;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static String replaceCharacters1(String context) {
		context = context.replaceAll("", "").replaceAll("", "").replaceAll("", "");

		return context;
	}

	/**
	 * @param context
	 * @return
	 */
	public static String replaceCharacters2(String context) {
		String[] strArr = { "仙侠武侠" };

		for (int i = 0; i < strArr.length; i++) {
			context = context.replaceAll(strArr[i], "");
		}

		return context;
	}
}