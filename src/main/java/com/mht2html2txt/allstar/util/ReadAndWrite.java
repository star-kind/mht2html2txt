package com.mht2html2txt.allstar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * <b>文件名必须是纯数字,不得含有其他字符,正确格式如:1.html</b><br>
 * <b>本类主要是将HTML离线网页转化提炼为TXT文件</b>
 * <ul>
 * <li>0.读取指定路径某一文件夹下的所有文件之文本内容集合(假设为totalContent)</li>
 * <li>1.把totalContent写入至某一新建文件newFileText中</li>
 * <li>2.利用正则处理newFileText,提纯为纯文本格式TXT</li>
 * </ul>
 * 
 * @author admin
 *
 */
public class ReadAndWrite extends BaseUtil {
	public static void main(String[] args) {
		ReadAndWrite readAndWrite = new ReadAndWrite();
		String encode = "UTF-8";
		String textName = "/XiangMo";// 最近一级目录名
		String htmlDestination = SOURCE_PRE_DIR + "/00_stuff";// 除最近级别目录外的路径全名
		htmlDestination += textName;
		textName += "_" + local_time + ".txt";
		readAndWrite.html2Txt(htmlDestination, textName, FILE_TRANS_STORE, OUTPUT_TXT_DIR, encode);
	}

	/**
	 * 
	 * <b>overload</b>
	 * 
	 * @param htmlDestination HTML素材目录
	 * @param textName        新文本文件名称
	 * @param fileTransStore  文件中转存放地
	 * @param outputDir       最终的txt生成目的地
	 * @param encode          编码方式
	 */
	public void html2Txt(String htmlDestination, String textName, String fileTransStore, String outputDir,
			String encode) {
		try {
			String context = null;
			Integer ordinal = 1;

			List<String> list = getFileNamesForm(htmlDestination);
			sortFileNum(list);

			for (String fileName : list) {
				System.out.println(ordinal++ + "\n" + fileName);
				context = getContentByPath(fileName, encode);
				writeIntoFile(context, textName, fileTransStore);
			}

			context = getContentByPath(fileTransStore + textName, encode);
			context = replaceAllByPattern(context);
			writeIntoFile(context, textName, outputDir);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			File fileHtml = new File(html_destination + STUFF_HTML_NAME);
			File fileDir = new File(html_destination);
			/**
			 * 删除mht转换后的html素材文件夹:html_destination;<br>
			 * File.delete()只能删除空文件夹或单个文件;<br>
			 * 先删除文件,再删除文件夹
			 */
			boolean deleteFile = fileHtml.delete();
			System.out.println("delete fileHtml = " + deleteFile);

			boolean deleteDir = fileDir.delete();
			System.out.println("delete deleteDir = " + deleteDir);
		}
	}

	/**
	 * <b>overload</b>
	 * 
	 * @param htmlDestination HTML素材目录
	 * @param textName        新文本文件名称
	 * @param fileTransStore  文件中转存放地
	 * @param outputDir       最终的txt生成目的地
	 */
	public void html2Txt(String htmlDestination, String textName, String fileTransStore, String outputDir) {
		try {
			String content = null;
			Integer ordinal = 1;

			List<String> list = getFileNamesForm(htmlDestination);
			sortFileNum(list);

			for (String fileUrl : list) {
				System.out.println(ordinal++ + "\n" + fileUrl);
				content = getContentByPath(fileUrl);
				writeIntoFile(content, textName, fileTransStore);
			}

			content = replaceAllByPattern(content);
			// System.out.println(content);
			writeIntoFile(content, textName, outputDir);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			File fileHtml = new File(html_destination + STUFF_HTML_NAME);
			File fileDir = new File(html_destination);
			/**
			 * 删除mht转换后的html素材文件夹:html_destination;<br>
			 * File.delete()只能删除空文件夹或单个文件;<br>
			 * 先删除文件,再删除文件夹
			 */
			boolean deleteFile = fileHtml.delete();
			System.out.println("delete fileHtml = " + deleteFile);

			boolean deleteDir = fileDir.delete();
			System.out.println("delete deleteDir = " + deleteDir);
		}
	}

	/**
	 * 根据正则表达式替换
	 * 
	 * @param context
	 * @return
	 */
	public String replaceAllByPattern(String context) {
		/*
		 * 正则表达式们
		 */
		String regExBasicTag = "<([^>]*)>";// 所有以 < 开头,以 > 结尾的标签
		String regExScript = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regExStyle = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regExW = "<w[^>]*?>[\\s\\S]*?<\\/w[^>]*?>";// 定义所有w标签
		// String regEx_space = "\\s*|\t|\r|\n";// 定义空格回车换行符
		// String regEx00 = "【([^】]*)】";// 所有以 【 开头,以 】 结尾的标签

		// Pattern compile = Pattern.compile(regEx00, Pattern.CASE_INSENSITIVE);
		// Matcher matcher = compile.matcher(context);
		// context = matcher.replaceAll("");

		Pattern compile3 = Pattern.compile(regExW, Pattern.CASE_INSENSITIVE);
		Matcher matcher3 = compile3.matcher(context);
		context = matcher3.replaceAll("");

		Pattern compile2 = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
		Matcher matcher2 = compile2.matcher(context);
		context = matcher2.replaceAll("");

		Pattern compile1 = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
		Matcher matcher1 = compile1.matcher(context);
		context = matcher1.replaceAll("");

		/*
		 * 必须在script和style之后处理此正则,因其范围较于script和style更广
		 */
		Pattern compile0 = Pattern.compile(regExBasicTag, Pattern.CASE_INSENSITIVE);
		Matcher matcher0 = compile0.matcher(context);
		context = matcher0.replaceAll("");

		context = replaceCharacters(context);// TODO 正则表达式套娃
		context = replaceCharacters1(context);
		context = replaceCharacters2(context);

		return context;
	}

	/**
	 * 写入新内容但不覆盖原内容,<br>
	 * 如果文件夹不存在则创建;<br>
	 * 若文件不存在则创建
	 * 
	 * @param context  正文内容
	 * @param fileName 文件名
	 * @param dirUrl   文件夹路径
	 */
	public void writeIntoFile(String context, String fileName, String dirUrl) {
		File file = new File(dirUrl);
		File f1 = new File(dirUrl + fileName);

		byte[] buff = new byte[] {};
		FileOutputStream writer = null;

		try {
			writer = new FileOutputStream(f1, true);
			if (file.isDirectory() == false) {
				file.mkdir();
			} else if (f1.exists() == false) {
				f1.createNewFile();
			}

			buff = context.getBytes("utf-8");
			writer.write(buff);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <b>overload</b>
	 * 
	 * @param filePath 路径
	 * @param encoding 读取内容时之编码
	 * @return
	 * @throws IOException
	 */
	public String getContentByPath(String filePath, String encoding) throws IOException {
		Path path = Paths.get(filePath);
		String content = new String();
		try {
			byte[] bytes = Files.readAllBytes(path);
			content = new String(bytes, encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * <b>overload</b>
	 * 
	 * @param filePath 路径
	 * @return
	 * @throws IOException
	 */
	public String getContentByPath(String filePath) throws IOException {
		StringBuffer buffer = new StringBuffer();
		File file = new File(filePath);
		BufferedReader reader = null;
		String tempString = null;
		int line = 1;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
			reader = new BufferedReader(inputStreamReader);

			while ((tempString = reader.readLine()) != null) {// 以行为单位读取文件内容,一次读一整行
				// System.out.println("Line" + line + ":" + tempString);
				buffer.append(tempString + "\n");
				line++;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * 按文件名数字编号来排序,<i>按先后次序读取写入,所以要正序排列</i>
	 * 
	 * @param absoluteList
	 */
	public void sortFileNum(List<String> absoluteList) {
		/*
		 * 匿名内部类实现排序
		 */
		Collections.sort(absoluteList, new Comparator<String>() {
			/**
			 * return 1;升序<br>
			 * return -1;降序 <br>
			 * return 0;相等为0
			 */
			@Override
			public int compare(String o1, String o2) {
				int n1 = Integer.parseInt(getFileNum(o1));
				int n2 = Integer.parseInt(getFileNum(o2));
				int diff = n1 - n2;
				if (diff > 0) {
					return 1;
				} else if (diff < 0) {
					return -1;
				}
				return 0;// 相等为0
			}
		});
	}

	/**
	 * 获取真实路径文件名中的数字编号 <b>文件名必须是纯数字</b>
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileNum(String fileName) {
		int last = fileName.lastIndexOf("/");
		int lastPoint = fileName.lastIndexOf(".");

		String substr = fileName.substring(last + 1, lastPoint);
		return substr;
	}

	/**
	 * 获取某个文件夹下的所有文件的文件名(支持多级文件夹)
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> getFileNamesForm(String filePath) throws FileNotFoundException, IOException {
		System.out.println("传入之路径=>" + filePath);
		List<String> absoluteList = new LinkedList<String>();

		try {
			File file = new File(filePath);
			if (!file.isDirectory()) {
				System.err.println("其乃文件");
				System.out.println("path=" + file.getPath());
				System.out.println("absolutepath=" + file.getAbsolutePath());
				System.out.println("name=" + file.getName());
			} else if (file.isDirectory()) {
				System.err.println("其乃文件夹");
				File[] filesList = file.listFiles();
				List<File> asList = Arrays.asList(filesList);
				for (File file1 : asList) {
					File destination = new File(filePath + "//" + file1.getName());
					if (!destination.isDirectory()) {
						absoluteList.add(destination.getAbsolutePath());
					} else if (destination.isDirectory()) {
						getFileNamesForm(filePath + "//" + file1.getName());
					}
				}
			}
		} catch (Exception e) {
			e.getLocalizedMessage();
		}
		return absoluteList;
	}
}