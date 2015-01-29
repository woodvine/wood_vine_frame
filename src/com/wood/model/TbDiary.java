package com.wood.model;

import com.wood.pojo.PaginationBean;

/**
 * 
 * @title :TbDiary
 * @description :日记实体类
 * @update :2015-1-16 下午1:38:15
 * @author :wang_ll
 * @version :1.0.0
 * @since :2015-1-16
 */
public class TbDiary extends PaginationBean{
	private Integer id;
	private Integer type;
	private String createTime;
	private String title;
	private String content;
	
	//创建者用户ID
	private Integer author;

	public Integer getAuthor() {
		return author;
	}

	public void setAuthor(Integer author) {
		this.author = author;
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
