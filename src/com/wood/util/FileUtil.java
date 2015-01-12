package com.wood.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @title :FileUtil
 * @description :文件操作相关的工具类
 * @update :2013-8-13 下午03:21:39
 * @author :wang_ll
 * @version :1.0.0
 * @since :2013-8-13
 */
public class FileUtil {

	/**
	 * 判断文件是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean isFileExist(String fileName) {
		try {
			File f = new File(fileName);
			if (f.isFile() && f.exists()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 读取指定文件的文本内容，返回文本值是一个长串，原文本中的换行不会被读入
	 * 
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static String read(String fileName, String charSet) {
		if (fileName == null || "".equals(fileName)) {
			System.out.println("文件名称为空...");
			return "";
		}

		// 设置字符编码
		if (StringUtil.isEmpty(charSet)) {
			charSet = "UTF-8";
		}

		File file = new File(fileName);
		FileInputStream fin = null;
		StringBuffer buffer = new StringBuffer();
		try {
			fin = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					fin, charSet));
			String content = reader.readLine();
			while (content != null) {
				buffer.append(content);
				// 循环读取下一行，没有这行代码就是死循环的啦。。。
				content = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return buffer.toString();
	}

	/**
	 * 返回文件中每一行，返回值为List<String>,值为每一行的文本
	 * 
	 * @param fileName
	 * @param uncode
	 * @return
	 */
	public static List<String> readFile(String fileName, String uncode) {
		List<String> list = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			File f = new File(fileName);
			if (f.isFile() && f.exists()) {
				InputStreamReader read = null;
				if (uncode != "") {
					read = new InputStreamReader(new FileInputStream(f), uncode);
				} else {
					read = new InputStreamReader(new FileInputStream(f));
				}
				reader = new BufferedReader(read);
				String line;
				while ((line = reader.readLine()) != null) {
					list.add(line);
				}
				read.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}

	/**
	 * 读取文件中的每一行，返回值中包含行尾的\r\n
	 * 
	 * @param fileName
	 * @param uncode
	 * @return
	 */
	public static String readFileByLines(String fileName, String uncode) {
		StringBuffer sbf = new StringBuffer();
		BufferedReader reader = null;
		try {
			File f = new File(fileName);
			if (f.isFile() && f.exists()) {
				InputStreamReader read = null;
				if (uncode != "") {
					read = new InputStreamReader(new FileInputStream(f), uncode);
				} else {
					read = new InputStreamReader(new FileInputStream(f));
				}
				reader = new BufferedReader(read);
				String line;
				sbf.append("\r\n");
				while ((line = reader.readLine()) != null) {
					sbf.append(line + "\r\n");
				}
				read.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sbf.toString();
	}

	/**
	 * 读取输入流的内容
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String read(InputStream in, String charSet) {
		if (in == null) {
			System.out.println("输入流为空.");
			return "";
		}

		if (StringUtil.isEmpty(charSet)) {
			charSet = "UTF-8";
		}

		// 读取输入流的内容
		BufferedReader reader = null;
		StringBuffer buffer = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(in, charSet));
			String content = reader.readLine();
			while (content != null) {
				buffer.append(content);
				// 循环读取下一行，没有这行代码就是死循环的啦。。。
				content = reader.readLine();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}

	/**
	 * 将字符串以UTF-8编码写入文件
	 * 
	 * @param fileName
	 *            待写入的文件
	 * @param content
	 *            待写入的文本
	 * @param isAppended
	 *            是否追加到原来文件
	 */
	public static void write(String fileName, String content, boolean isAppended) {
		write(fileName, content, isAppended, null);
	}

	/**
	 * 将字符串以指定的编码方式写入文件
	 * 
	 * @param fileName
	 *            待写入的文件
	 * @param content
	 *            待写入的文本
	 * @param append
	 *            是否追加
	 * @param charset
	 *            文件输出编码格式，默认为UTF-8
	 */
	public static void write(String fileName, String content, boolean append,
			String charset) {
		if (fileName == null || "".equals(fileName)) {
			return;
		}

		if (content == null || "".equals(content)) {
			return;
		}

		// 默认编码格式为UTF-8
		if (charset == null) {
			charset = "UTF-8";
		}

		// 将字符串写入到文件输出流中
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(
					new FileOutputStream(fileName, append), charset);
			osw.write(content + "\r\n");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将文件添加到ZIP输出流中
	 * 
	 * @param filePath
	 * @param zos
	 */
	public static void addFileToZip(String filePath, ZipOutputStream zos) {
		BufferedInputStream bis = null;
		File f = new File(filePath);
		if (!f.exists()) {
			// LogUtil.info(filePath + "文件不存在", "FileUtil", 1);
			return;
		}

		try {
			FileInputStream fis = new FileInputStream(f);
			bis = new BufferedInputStream(fis);
			byte[] buf = new byte[1024];
			int len;

			ZipEntry ze = new ZipEntry(f.getName());// 这是压缩包名里的文件名
			zos.putNextEntry(ze);// 写入新的 ZIP 文件条目并将流定位到条目数据的开始处

			while ((len = bis.read(buf)) != -1) {
				zos.write(buf, 0, len);
				zos.flush();
			}
		} catch (Exception e) {
			// LogUtil.error(e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Exception e1) {
					// LogUtil.error(e1);
				}
			}
		}
	}

	/**
	 * 将文件添加到ZIP文件中
	 * 
	 * @param filePath
	 * @param zos
	 */
	public static void addFileToZip(String filePath, String imgZipPath) {
		File f = new File(filePath);
		if (!f.exists()) {
			// LogUtil.info(filePath + "文件不存在", "FileUtil", 1);
			return;
		}

		try {
			FileInputStream fis = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] buf = new byte[1024];
			int len;
			FileOutputStream fos = new FileOutputStream(imgZipPath);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			ZipOutputStream zos = new ZipOutputStream(bos);// 压缩包
			ZipEntry ze = new ZipEntry(f.getName());// 这是压缩包名里的文件名
			zos.putNextEntry(ze);// 写入新的 ZIP 文件条目并将流定位到条目数据的开始处

			while ((len = bis.read(buf)) != -1) {
				zos.write(buf, 0, len);
				zos.flush();
			}
			bis.close();
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新建一个文件夹
	 * 
	 * @param filePath
	 */
	public static void createNewFolder(String filePath) {
		File newFolder = new File(filePath);
		try {
			if (newFolder.isDirectory()) {
			} else {
				newFolder.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新建一个文件，如果该文件已存在则删除后再新建
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public static void createNewFile(String fileName) {
		File f = new File(fileName);
		if (f.isFile() && f.exists()) {
			f.delete();
		}
		// 创建新文件
		boolean flag = false;
		try {
			flag = f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!flag) {
			System.out.println("创建新文件失败...");
		}
	}

	/**
	 * 获取文件夹下的所有文件夹列表
	 * 
	 * @param directory
	 * @return
	 */
	public static List<String> getDirectoryList(File directory) {
		List<String> list = new ArrayList<String>();
		// 判断传入对象是否为一个文件夹对象
		if (!directory.isDirectory()) {
			System.out.println("你输入的不是一个文件夹，请检查路径是否有误！！");
		} else {
			File[] t = directory.listFiles();
			for (int i = 0; i < t.length; i++) {
				if (t[i].isDirectory()) {
					list.add(t[i].getName());
				}
			}
		}
		return list;
	}

	/**
	 * 删除文件夹及其下面所有的文件
	 * 
	 * @param folderPath
	 */
	public static void deleteFolder(String folderPath) {
		try {
			deleteAllFiles(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定文件
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public static void deleteFile(String fileName) {
		File f = new File(fileName);
		boolean flag = false;
		if (f.isFile() && f.exists()) {
			flag = f.delete();
		} else {
			System.out.println("待删除的文件不存在");
		}

		if (!flag) {
			System.out.println("删除文件失败...");
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 */
	public static boolean deleteAllFiles(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}

		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				deleteAllFiles(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				deleteFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除指定文件夹下包含指定名称的所有文件
	 * 
	 * @param path
	 * @param name
	 * @return
	 */
	public static boolean deleteAllFilesContainsName(String path, String name) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}

		if (!file.isDirectory()) {
			return flag;
		}

		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].indexOf(name) >= 0) {
				if (path.endsWith(File.separator)) {
					temp = new File(path + tempList[i]);
				} else {
					temp = new File(path + File.separator + tempList[i]);
				}
				if (temp.isFile()) {
					temp.delete();
				}
			}
		}
		return flag;
	}

	/**
	 * 下载远程文件，以指定名称保存到本地
	 * 
	 * @param resourcePath
	 *            资源路径
	 * @param fileName
	 *            本地保存文件全称
	 * @return true（成功），false（失败）
	 */
	public static boolean downloadFile(String resourcePath, String fileName) {
		DataInputStream dis = null;
		OutputStream fos = null;
		try {
			URL url = new URL(resourcePath);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(10000);
			dis = new DataInputStream(connection.getInputStream());
			fos = new FileOutputStream(fileName);
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = dis.read(buff)) != -1) {
				fos.write(buff, 0, len);
			}
			buff = null;
			return true;
		} catch (SocketTimeoutException e2) {
			e2.printStackTrace();
			return false;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 文件拷贝
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param targetFile
	 *            目标文件
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	public static void main(String[] args) {
		// String file = "src/itemKeyTest1.xml";
		// deleteFile(file);

		// createNewFolder("test");
		// deleteFolder("test");

		// createNewFile("test.txt");
		// write("test.txt","hello world\n\rIwant to change you",true);
		// String sss = readFileByLines("test.txt","UTF-8");
		// System.out.println(sss);
		// deleteFile("test.txt");
	}
}
