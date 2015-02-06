package com.wood.util;

/**
 * 
 * @title       :SystemConstant
 * @description :存储常量信息
 * @update      :2015-1-6 下午3:40:11
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-6
 */
public class SystemConstant {
	//====================Session用户key=============================
	public static final String CURRENT_USER = "currentUser";
	//====================Session用户key=============================
	
	//====================微信公众号信息=================================
	public static final String APP_ID = "";
	public static final String APP_SECRET = "";
	//====================微信公众号信息=================================
	
	//===================用户信息更改的类型===============================
	public static final int CHANGE_PWD  = 1;  //修改密码
	public static final int CHANGE_INFO = 0;  //修改其他信息
	//===================用户信息更改的类型===============================
	
	//爬虫相关的常量信息
	public final static int ConnectTimeout = 30000;
	public final static int ReadTimeout = 30000;
	public final static String RequestMethod = "GET";
	public final static String CharSet = "UTF-8";
	public final static String SpaceASCII = "%20";
	public final static String SpaceString = " ";
	
	//批量查询的记录总数
	public static final String CsdnHome = "http://blog.csdn.net/";
	public static final int BatchedCount = 20;
	public static final int MaxThreadCount = 1;
	
	//url类型：首页链接，分页，博文
	public static final int TypeHome = 0;//首页链接:解析分页总数，和当前页博文链接
	public static final int TypePage = 1;//分页：解析博客链接
	public static final int TypeBlog = 2;//博文：抽取博文内容
}
