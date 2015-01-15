package com.bh.wexin.shared;


/**
 * 
 * @title       :MediaType
 * @description :图片（image）: 1M，支持JPG格式 
 *                语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
 *                视频（video）：10MB，支持MP4格式 
 *                缩略图（thumb）：64KB，支持JPG格式
 * @update      :2014-10-22 下午5:05:06
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2014-10-22
 */
public enum MediaType {
	
	Voice("voice","语音,2MB,播放长度不超过60s,支持AMR和MP3格式","AMR,MP3",2048),
	Image("image","图片,1MB,支持JPG格式 ","JPG",1024),
	Video("video","视频,10MB,支持MP4格式  ","MP4",10240),
	Thumb("thumb","缩略图,64KB,支持JPG格式 ","JPG",64);
	
	/**
	 * 
	 * @param type
	 * @param description
	 * @param mimeTypes  支持的文件类型以","分割
	 * @param maxSize    文件限制大小（单位KB）
	 */
	private MediaType(String type,String description,String mimeTypes,int maxSize){
		this.type = type;
		this.description = description;
		this.mimeTypes = mimeTypes;
		this.maxSize = maxSize;
	}

	private String type;
	private String description;
	private int maxSize;
	private String mimeTypes;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public String getMimeTypes() {
		return mimeTypes;
	}

	public void setMimeTypes(String mimeTypes) {
		this.mimeTypes = mimeTypes;
	}
	
}
