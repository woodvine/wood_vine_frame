package com.wood.model;

/**
 * 
 * @title :TbCsdnBlog
 * @description :CSDN博客文章抽象类
 * @update :2015-2-3 上午9:19:00
 * @author :wang_ll
 * @version :1.0.0
 * @since :2015-2-3
 */
public class TbCsdnBlog {
	private Integer id;
	private String author; //作者

	private String category;//分类
	private String postdate;//最后修改日期

	private String title;  //标题
	private String content;//内容

	public TbCsdnBlog(){
		
	}
	
	public TbCsdnBlog(String author,String title){
		this.author = author;
		this.title = title;
	}
	
	public TbCsdnBlog(String author,String category,String postdate,String title,String content){
		this.author = author;
		this.category = category;
		this.postdate = postdate;
		this.title = title;
		this.content = content;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("CsdnBlog=[title:"+title);
		buffer.append(",category:"+category);
		buffer.append(",postdate:"+postdate);
		buffer.append("]");
		return buffer.toString();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
