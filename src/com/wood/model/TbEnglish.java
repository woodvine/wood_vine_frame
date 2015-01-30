package com.wood.model;

import com.wood.pojo.PaginationBean;

/**
 * 
 * @title       :TbEnglish
 * @description :英语学习相关的实体类
 * @update      :2015-1-30 下午1:52:16
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-30
 */
public class TbEnglish  extends PaginationBean{
	private Integer id;
	private Integer type;//类型：1（词汇），2（句子）
	private String content;
	private String chinese;//中文翻译
	private String description;//词汇相关的描述（词性）
	private String createTime;
	private Integer author;
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("TbEnglish=[");
		buffer.append("author:"+author);
		buffer.append(",content:"+content);
		buffer.append(",chinese:"+chinese);
		buffer.append("]");
		return buffer.toString();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getChinese() {
		return chinese;
	}
	public void setChinese(String chinese) {
		this.chinese = chinese;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getAuthor() {
		return author;
	}

	public void setAuthor(Integer author) {
		this.author = author;
	}
	
}
