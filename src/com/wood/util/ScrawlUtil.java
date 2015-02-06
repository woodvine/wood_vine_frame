package com.wood.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.util.NodeList;

import com.wood.model.TbCsdnBlog;
import com.wood.model.TbScrawlData;

/**
 * @title ScrawlUtil
 * @author WoodWang
 * @description 通过URLConnection获取指定请求链接的内容
 * @date   2015-1-31
 * @version 1.1.0
 */
public class ScrawlUtil {
	
	/**
	 * 获取网页内容
	 * @param data
	 */
	public static void scrawl(TbScrawlData data){
		
		if(data ==null||StringUtil.isEmpty(data.getUrl())){
			return ;
		}
		
		URL url = null;
		URLConnection con = null;
		try {
			url = new URL(data.getUrl());
			con = url.openConnection();
		} catch (Exception e) {
			data.setStatus(ScrawlStatus.OtherError.getValue());
			LogUtil.error(e);
			return;
		}
		
		//利用Http请求获取网页信息
		HttpURLConnection connection = (HttpURLConnection) con;
		// 设置请求头
		connection
				.setRequestProperty(
						"User-Agent",
						"Mozilla/5.0 (Windows; N; Windows NT 5.1; zh-CN) AppleWebKit/533.3 (KHTML, like Gecko) Qt/4.7.1 Safari/533.3");
		//以Get方式获取请求
		try {
			connection.setRequestMethod(SystemConstant.RequestMethod);
		} catch (ProtocolException e) {
			data.setStatus(ScrawlStatus.OtherError.getValue());
			LogUtil.error(e);
			return ;
		}
		
		//设置链接相关的属性
		connection.setConnectTimeout(SystemConstant.ConnectTimeout);
		connection.setReadTimeout(SystemConstant.ReadTimeout);
		connection.setInstanceFollowRedirects(false);
		
		//执行连接请求，获取响应码
		int statusCode = 0;
		try {
			connection.connect();
			statusCode = connection.getResponseCode();
		} catch (SocketTimeoutException e2) {
			data.setStatus(ScrawlStatus.SocketTimeout.getValue());
			LogUtil.error(e2);
			return ;
		} catch (IOException e) {
			data.setStatus(ScrawlStatus.OtherError.getValue());
			LogUtil.error(e);
			return ;
		}
		
		//200成功后获取响应流
		InputStream is = null;
		if (statusCode == 200) {
			try {
				is = connection.getInputStream();
			} catch (IOException e) {
				data.setStatus(ScrawlStatus.OtherError.getValue());
				LogUtil.error(e);
				return ;
			}
		} else {
			//返回码为403可以判断博客名称是不存在的
			data.setStatus(ScrawlStatus.Failure.getValue());
			return ;
		}
		
		//读取流信息并返回
		StringBuffer buffer = new StringBuffer();
		String readLine = null;
		BufferedReader reader = null;
		if (is != null) {
			try {
				reader = new BufferedReader(new InputStreamReader(is, SystemConstant.CharSet));
				while ((readLine = reader.readLine()) != null) {
					if (readLine.length() > 0) {
						buffer.append(readLine.trim());
					}
				}
				
				//返回页面内容
				data.setStatus(ScrawlStatus.Success.getValue());
				data.setContent(buffer.toString());
				LogUtil.info(data.getUrl()+"链接的页面内容成功获取。");
				
				return ;
			} catch (UnsupportedEncodingException e) {
				data.setStatus(ScrawlStatus.OtherError.getValue());
				LogUtil.error(e);
			} catch (IOException e) {
				data.setStatus(ScrawlStatus.OtherError.getValue());
				LogUtil.error(e);
			}finally{
				connection.disconnect();
			}
		}
	}

	public static void main(String[] args) {
		String url = "http://blog.csdn.net/wojiushiwo945you";
		TbScrawlData data = new TbScrawlData();
		data.setUrl(url);
		scrawl(data);
//		parse(data);
	}

	/**
	 * 解析网页内容，分析出新的待采集的URL
	 * @param data
	 * @param blog 博客内容
	 * @return
	 */
	public static TbCsdnBlog parse(TbScrawlData data,List<TbScrawlData> list) {
		if(data==null ||data.getContent()==null||list==null){
			return null;
		}
		
		int type = data.getType();
		switch (type){
		case 0:
			//首页，解析当页的博文列表，再解析博文分页列表
			list.addAll(parseBlogList(data.getContent()));
			list.addAll(parsePageList(data.getContent()));
			break;
		case 1:
			list.addAll(parseBlogList(data.getContent()));
			break;
		case 2:
			return parseBlogDetail(data.getContent());
		}
		
		return null;
	}
	
	/*
	 * 解析博文链接
	 */
	private static List<TbScrawlData> parseBlogList(String content){
		List<TbScrawlData> datas = new ArrayList<TbScrawlData>();
		NodeList blogList = HtmlParseUtil.parseElementWithAttribute(content, "class", "article_title");
		if(blogList==null||blogList.size()==0){
			return datas;
		}
		
		//遍历
		List<String> urls = new ArrayList<String>();
		for(int i = 0;i<blogList.size();i++){
			urls.addAll(HtmlParseUtil.parseLinkContains(blogList.elementAt(i).toHtml()));
		}
		
		for(String url :urls){
			datas.add(new TbScrawlData(SystemConstant.CsdnHome+url,ScrawlStatus.Newly.getValue(),
					SystemConstant.TypeBlog));
		}
		return datas;
	}
	
	/*
	 * 解析内容的分页链接
	 */
	private static List<TbScrawlData> parsePageList(String content){
		List<TbScrawlData> datas = new ArrayList<TbScrawlData>();
		NodeList pageList = HtmlParseUtil.parseElementWithAttribute(content, "id", "papelist");
		if(pageList==null||pageList.size()==0){
			return datas;
		}
		
		//只有个一个分页Node
		Node node = pageList.elementAt(0);
		Node firstChild = node.getFirstChild();//包含分页总数
		int totalPage = Integer.parseInt(StringUtil.truncate(firstChild.toPlainTextString(), "共", "页"));
		Node lastChild = node.getLastChild();//尾页链接
		String pageUrl = StringUtil.truncate(lastChild.toHtml(), "href=\"", totalPage+"\"");
		
		//拼接分页链接
		if(totalPage>1){
			for(int i = 2;i<=totalPage;i++){
				StringBuffer buffer = new StringBuffer();
				buffer.append(SystemConstant.CsdnHome);
				buffer.append(pageUrl);
				buffer.append(i);
				datas.add(new TbScrawlData(buffer.toString(),ScrawlStatus.Newly.getValue(),
						SystemConstant.TypePage));
			}
		}
		
		return datas;
	}
	
	/*
	 * 解析博文内容
	 * 博文标题 class="link_title"的span
	 * 博文的内容是id="article_content"
	 */
	private static TbCsdnBlog parseBlogDetail(String content){
		NodeList titleDiv = HtmlParseUtil.parseElementWithAttribute(content, "class", "link_title");
		String title = null;
		if(titleDiv!=null&&titleDiv.size()>0){
			title = titleDiv.elementAt(0).toPlainTextString();
		}
		
		NodeList categoryDiv = HtmlParseUtil.parseElementWithAttribute(content, "class", "link_categories");
		String category = null;
		if(categoryDiv!=null&&categoryDiv.size()>0){
			category = categoryDiv.elementAt(0).toPlainTextString();
		}
		
		NodeList postdateDiv = HtmlParseUtil.parseElementWithAttribute(content, "class", "link_postdate");
		String postdate = null;
		if(postdateDiv!=null&&postdateDiv.size()>0){
			postdate = postdateDiv.elementAt(0).toPlainTextString();
		}
		
		NodeList list = HtmlParseUtil.parseElementWithAttribute(content, "id", "article_content");
		StringBuffer buffer = new StringBuffer();
		if(list!=null&&list.size()>0){
			Node contentDiv = list.elementAt(0);
			NodeList childrens = contentDiv.getChildren();
			for(int i = 0,j=childrens.size();i<j;i++){
				Node child = childrens.elementAt(i);
				buffer.append(child.toPlainTextString().replace("&nbsp;", " "));
				buffer.append("\n");
			}
		}
		
		//返回
		TbCsdnBlog blog = new TbCsdnBlog("wojiushiwo945you",category,postdate,title,buffer.toString());
		return blog;
	}
}
