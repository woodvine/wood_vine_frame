package com.wood.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * 
 * @title       :HtmlParseUtil
 * @description :HTML页面解析工具类
 * @update      :2014-1-13 上午10:36:04
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2014-1-13
 */
public class HtmlParseUtil {
	private static Logger logger = Logger.getLogger(HtmlParseUtil.class.getName());
	
	/**
	 * 解析总页数：模式匹配包含1/255页的DIV
	 * @param content
	 * @return
	 * @throws ParserException 
	 */
	public static int parseTotalPage(String html){
		int totalPage = 0;
		Parser parser = null;;
		NodeList divList = null;
		try {
			parser = new Parser(html);
			NodeFilter filter = new TagNameFilter("Span");
		    divList = parser.extractAllNodesThatMatch(filter);
		} catch (ParserException e1) {
			logger.error(e1);
		}
		
		//判空处理：可能存在没有子节点的情况
		if(divList==null||divList.size()<=0){
			return totalPage;
		}
		
		//过滤DIV，解析出总页数
		for(int i = 0 ;i<divList.size();i++){
			Node e = divList.elementAt(i);
			String text = e.toPlainTextString();
			text = text.trim();//去掉首尾的空格，避免匹配失败
			//模式为：1/d+页
			if(text.matches("\\d+条数据  共\\d+页")){
				totalPage = Integer.parseInt(StringUtil.truncate(text, "共", "页"));
				System.out.println("total page:"+totalPage);
				break;
			}
		}
		return totalPage;
	}

	public static void main(String[] args) {
		String te = "<div style=\"position:absolute;left:390px;top:458px;width:94px;height:36px;color:#ffffff;font-size:20px;\"> 1/222页</div>";
		 te = te+"<div style=\"position:absolute;left:390px;top:458px;width:94px;height:36px;color:#ffffff;font-size:20px;\"> 1/2</div>";
		 te = te+"<div style=\"position:absolute;left:390px;top:458px;width:94px;height:36px;color:#ffffff;font-size:20px;\">人民币</div>";
		parseTotalPage(te);
		
		String t = "<div align=\"center\" style=\"left:88px;top:106px;width:80px;height:42px;position:absolute;\"><font style=\"font-size:22px\" color=\"#FFFFFF\">法治</font></div>";
		
		List<String> list = parseFontTextWithAttribute(t,"style","font-size:22px");
		for(String s:list){
			System.out.println(s);
		}
	}
		
	/**
	 * 解析源文件中包含特定字符串的<a>标签的链接地址
	 * @description    路径中包含中文的参数以指定的编码方式编码
	 * @param content  页面源文件
	 * @param contains 包含特定的字符串
	 * @param charSet  URL的编码方式
	 * @return
	 */
	public static List<String> parseLinkContains(String content) {
		List<String> links = new ArrayList<String>();
		NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
		Parser parser = null;;
		NodeList linkList = null;
		try {
			parser = new Parser(content);
		    linkList = parser.extractAllNodesThatMatch(linkFilter);
		} catch (ParserException e) {
			logger.error(e);
		} 
		
		//判空处理：可能存在没有子节点的情况
		if(linkList==null||linkList.size()<1){
			return links;
		}
		
		//取出所有的含有指定字符串的A标签
		for(int j =0;j<linkList.size();j++){
			LinkTag linkNode = (LinkTag)linkList.elementAt(j);
			String href = linkNode.getAttribute("href");
			links.add(href);
		}
		
		return links;
	}
	
	/**
	 * 解析源文件中具有指定属性和值的元素的列表
	 * @param html        源文件
	 * @param attribute   DOM元素的属性名称
	 * @param value       DOM元素的属性值
	 * @param useContains 匹配方式使用字符串的包含匹配
	 *                    true :匹配attributeValue.contains(value)的节点
	 *                    false:匹配attributeValue.equals(value)的节点
	 * @return            符合条件的元素的toHtml()列表
	 * @throws ParserException
	 */
	public static NodeList parseElementWithAttribute(String html,
			String attribute, String value) {
		if(html==null){
			return null;
		}
		
		Parser parser;
		try {
			parser = new Parser(html);
			NodeFilter filter = null;
			filter = new HasAttributeFilter(attribute, value);
			
			NodeList divList = parser.extractAllNodesThatMatch(filter);
			return divList;
		} catch (ParserException e) {
			logger.error(e);
		}

		return null;
	}
	
	/**
	 * 解析具有特定属性值的font标签的文本内容
	 * @param html
	 * @param attribute
	 * @param value
	 * @param useContains
	 * @return
	 */
	public static List<String> parseFontTextWithAttribute(String html,
			String attribute, String value) {
		List<String> result = new ArrayList<String>();
		Parser parser;
		try {
			parser = new Parser(html);
			NodeFilter attributeFilter  = new HasAttributeFilter(attribute, value);
			NodeList divList = parser.extractAllNodesThatMatch(attributeFilter);
			if (divList == null || divList.size() < 1) {
				return result;
			}
			
			for(int i=0,j=divList.size();i<j;i++){
				result.add(divList.elementAt(i).getNextSibling().getText());
			}
		} catch (ParserException e) {
			logger.error(e);
		}
		
		return result;
	}

	/**
	 * 解析指定标签名称的纯文本
	 * @param html
	 * @return
	 */
	public static String parseElementPlainText(String html,String tagName){
		String tagText = "";
		Parser parser;
		try {
			parser = new Parser(html);
			NodeFilter filter = new TagNameFilter(tagName);
			NodeList divList = parser.extractAllNodesThatMatch(filter);
			if(divList==null||divList.size()<1){
				return tagText;
			}
			
			return divList.elementAt(0).toPlainTextString();
		} catch (ParserException e) {
			logger.error(e);
		}
		
		return tagText;
	}
	
	/**
	 * 解析IMG标签的Src属性值
	 * @param html
	 * @return
	 */
	public static String parseImgSource(String html){
		String src = "";
		Parser parser;
		try {
			parser = new Parser(html);
			NodeFilter filter = new TagNameFilter("img");
			NodeList divList = parser.extractAllNodesThatMatch(filter);
			if(divList==null||divList.size()<1){
				return src;
			}
			
			String imgHtml = divList.elementAt(0).toHtml();
			return StringUtil.truncate(imgHtml,"src=\"","\"");
		} catch (ParserException e) {
			logger.error(e);
		}
		
		return src;
	}
}

