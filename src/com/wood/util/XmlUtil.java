package com.wood.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * 
 * @title :XmlUtil
 * @description :XML文档操作工具类
 * @update :2013-12-27 下午12:54:45
 * @author :
 * @version :1.0.0
 * @since :2013-12-27
 */
public class XmlUtil {

	/**
	 * 读取配置文件中的指定属性的值
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 * @throws UnsupportedEncodingException
	 */
	public static String readFromXML(String filePath, String key)
			throws UnsupportedEncodingException, JDOMException, IOException {
		Document doc = fileToDocument(filePath);
		Element root = doc.getRootElement();
		Element value = root.getChild(key);
		return value.getText();
	}

	/**
	 * 写入到指定文件，指定键值
	 * 
	 * @param filePath
	 * @param key
	 * @param value
	 * @throws IOException
	 * @throws JDOMException
	 * @throws UnsupportedEncodingException
	 */
	public static void writeToXML(String filePath, String key, String value)
			throws UnsupportedEncodingException, JDOMException, IOException {
		Document doc = fileToDocument(filePath);
		Element root = doc.getRootElement();
		Element action = root.getChild(key);
		action.setText(value);
		FileOutputStream outStream = null;
		try {
			XMLOutputter outer = new XMLOutputter(Format.getPrettyFormat()
					.setEncoding("UTF-8"));
			outStream = new FileOutputStream(filePath);
			outer.output(doc, outStream);
			/*
			 * 执行文件刷新处理 出现过该方法调用后文件内容没有刷新的情况
			 */
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outStream != null) {
				outStream.close();
			}
		}
	}

	/**
	 * 返回DOM文档对应的XML格式的文本信息
	 * 
	 * @param document
	 * @param encoding
	 * @return
	 */
	public static String documentToXml(Document document, String encoding) {
		Format format = Format.getPrettyFormat();
		format.setEncoding(encoding);
		XMLOutputter outputter = new XMLOutputter(format);
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		try {
			outputter.output(document, bo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bo.toString();
	}

	/**
	 * 将XML文本转换成Document对象
	 * 
	 * @param xml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document xmlToDocument(String xml) throws JDOMException,
			IOException {
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(new StringReader(xml));
		return doc;
	}

	/**
	 * XML文件转换为Document文档对象
	 * 
	 * @param path
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Document fileToDocument(String path)
			throws UnsupportedEncodingException, JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		FileInputStream fis = new FileInputStream(path);
		DataInputStream in = new DataInputStream(fis);
		InputStreamReader isReader = new InputStreamReader(in, "UTF-8");
		Document doc = builder.build(isReader);
		fis.close();
		in.close();
		isReader.close();
		return doc;
	}
}
