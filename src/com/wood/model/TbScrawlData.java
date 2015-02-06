package com.wood.model;

/**
 * 
 * @title       :TbScrawlData
 * @description :网页爬虫信息
 * @update      :2015-2-2 上午10:26:52
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-2-2
 */
public class TbScrawlData {
	//主键ID
	private Integer id;
	//抓取链接的哈希值:用于地址判重
	private Integer hashCode;
	//url地址
	private String url;
	//该地址的状态：1，待抓取；2，正在抓取；3：已完成；4，失败；5：超时
	private Integer status;
	//网页内容
	private String content;
	//创建时间
	private String createTime;
	//链接类型：1 ，种子链接；2：分页链接；3：内容链接；4：终止链接；其他：无效链接
	private Integer type;
	
	public TbScrawlData(){
		
	}
	
	/**
	 * 最初设置抓取信息，只需要以下三个参数
	 * @param url，同时确定hashCode
	 * @param status
	 * @param type
	 */
	public TbScrawlData(String url,Integer status,Integer type){
		this.url = url;
		if(url!=null){
			this.hashCode = url.hashCode();
		}
		this.status = status;
		this.type = type;
	}
	
	public Integer getHashCode() {
		return hashCode;
	}

	public void setHashCode(Integer hashCode) {
		this.hashCode = hashCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("ScrawlData=[hashCode="+hashCode);
		buffer.append(",url="+url);
		buffer.append(",status="+status);
		buffer.append(",type="+type);
		buffer.append("]");
		return buffer.toString();
	}

}
