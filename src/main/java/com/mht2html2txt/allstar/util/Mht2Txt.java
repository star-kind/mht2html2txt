package com.mht2html2txt.allstar.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * mht转html,再转为TXT
 * 
 * <ul>
 * <li>0.按编码格式获取mht文件中的文本内容(Get text content)
 * <li>1.将内容写入目标路径下的html文件内(如无则创)
 * <li>2.转化完毕
 * <li>3.批量获取mht文件中的文本内容
 * </ul>
 * 
 * <b>文件名必须是纯数字,不得含有其他字符,正确格式如:1.html</b><br>
 * <b>本类主要是将mht转为HTML离线网页后,再进一步转化为TXT文件</b>
 * <ul>
 * <li>0.读取指定路径某一文件夹下的所有文件之文本内容集合(假设为totalContent)</li>
 * <li>1.把totalContent写入至某一新建文件newFileText中</li>
 * <li>2.利用正则处理newFileText,提纯为纯文本格式TXT</li>
 * </ul>
 * 
 * 
 * <strong>本类为某一目录下的多个文件转换</strong>
 * 
 * @author admin
 *
 */
public class Mht2Txt extends BaseUtil {
	public static void main(String[] args) {
		Mht2Txt mht2Txt = new Mht2Txt();
		ReadAndWrite readAndWrite = new ReadAndWrite();

		mht2Txt.batchConversion(mht_dir);
		readAndWrite.html2Txt(html_destination, txt_file_name, FILE_TRANS_STORE, OUTPUT_TXT_DIR);
	}

	/**
	 * 批量mht转换为一个HTML
	 * 
	 * @param filePath mht文件们所在目录
	 */
	public void batchConversion(String filePath) {
		String encodeType = null;
		int ordinal = 1;
		try {
			List<String> list = getFileNamesForm(filePath);
			sortFileNum(list);

			for (String folderName : list) {
				encodeType = getEncode(folderName);
				System.out.println("encode => " + encodeType);
				mht2html(folderName, encodeType);
				System.err.println(ordinal++);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将 <strong>单个</strong>mht文件内容 写入 html文件中(如无则建)
	 * 
	 * @param srcMht     原mht文件
	 * @param encodeType 编码格式
	 */
	public void mht2html(String srcMht, String encodeType) {
		try {
			InputStream fis = new FileInputStream(srcMht);
			Session mailSession = Session.getDefaultInstance(System.getProperties(), null);
			MimeMessage msg = new MimeMessage(mailSession, fis);
			Object content = msg.getContent();
			if (content instanceof Multipart) {
				MimeMultipart mp = (MimeMultipart) content;
				MimeBodyPart bp1 = (MimeBodyPart) mp.getBodyPart(0);

				// 获取mht文件的内容
				String htmText = getMhtContentText(bp1, encodeType);
				if (htmText == null)
					return;

				writeIntoFile(htmText, STUFF_HTML_NAME, html_destination);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写入新内容但不覆盖原内容,<br>
	 * 如果文件夹不存在则创建;<br>
	 * 若文件不存在则创建
	 * 
	 * @param context  文本内容
	 * @param fileName 新生文件名
	 * @param dirUrl   新生文件的文件夹路径
	 */
	public void writeIntoFile(String context, String fileName, String dirUrl) {
		File f1 = new File(dirUrl);
		File f2 = new File(dirUrl + fileName);
		FileOutputStream writer = null;
		byte[] buff = new byte[] {};
		try {
			if (f1.isDirectory() == false) {
				f1.mkdirs();// 此处需要创建多级目录
			}
			if (f2.exists() == false) {
				f2.createNewFile();
			}
			buff = context.getBytes();
			writer = new FileOutputStream(f2, true);
			writer.write(buff);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取filePath路径里面的文件的所有内容<br>
	 * 然后判断内容中是否含有
	 * <p>
	 * <b>meta charset=3D"UTF-8"</b>
	 * </p>
	 * or
	 * <p>
	 * <b>meta charset=3D"GBK"</b>
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 */
	public String getEncode(String filePath) {
		String p = new String();
		Path path = Paths.get(filePath);
		String gbkStr = "charset=3D\"GBK\"";
		String encode = null;
		try {
			p = new String(Files.readAllBytes(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Integer i = p.indexOf(gbkStr);
		System.out.println("i==>>" + i);
		if (i == -1) {
			encode = "UTF-8";
		} else {
			encode = "GBK";
		}
		return encode;
	}

	/**
	 * 
	 * /** 获取mht文件中的内容
	 * 
	 * @param mbp
	 * @param encode
	 * @return
	 */
	public String getMhtContentText(MimeBodyPart mbp, String encode) {
		InputStream is = null;
		BufferedInputStream buff = null;
		BufferedReader br = null;
		Reader r = null;
		StringBuffer strHtml = null;
		String content = null;
		try {
			is = mbp.getInputStream();
			buff = new BufferedInputStream(is);
			r = new InputStreamReader(buff, encode);
			br = new BufferedReader(r);

			strHtml = new StringBuffer("");
			String strLine = null;

			while ((strLine = br.readLine()) != null) {
				// System.out.println(strLine);
				strHtml.append(strLine + "\r\n");
			}
			br.close();
			r.close();
			is.close();
			content = strHtml.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (buff != null)
					buff.close();
				if (is != null)
					is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return content;
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
		System.out.println("file's path=> " + filePath);
		List<String> absoluteList = new LinkedList<String>();

		try {
			File file = new File(filePath);
			if (!file.isDirectory()) {
				System.err.println("is Directory...");
				System.out.println("absolutepath=>" + file.getAbsolutePath());
				System.out.println("file's name=>" + file.getName());
			} else if (file.isDirectory()) {
				System.err.println("is a file...");
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