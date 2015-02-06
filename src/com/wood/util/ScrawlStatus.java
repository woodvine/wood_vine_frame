package com.wood.util;

/**
 * 
 * @title       :ScrawlStatus
 * @description :网页爬取状态
 * @update      :2015-2-2 下午1:57:31
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-2-2
 */
public enum ScrawlStatus {
	//最新解析到的记录
	Newly(1,"new parsed"),
	//中间状态：正在执行抓取操作
	Medium(2,"in scrawl"),
	//成功抓取页面内容
	Success(3,"success"),
	//抓取失败：返回码非200
	Failure(4,"failure"),
	//超时
	SocketTimeout(5,"read time out"),
	//其他异常
	OtherError(6,"other exception in scrawl");
	
	private ScrawlStatus(Integer value,String desc){
		this.value = value;
		this.desc = desc;
	}

	private Integer value;
	private String desc;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
