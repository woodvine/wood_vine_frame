package com.bh.wexin.bean.request;

/**
 * 
 * @title       :MsgType
 * @description :消息类型枚举类
 * @update      :2015-1-14 下午1:51:46
 * @author      :
 * @version     :1.0.0
 * @since       :2015-1-14
 */
public enum MsgType {
	text("text"),//文本消息
	location("location"),//地理位置消息
	image("image"),//图片消息
	voice("voice"),//语音消息
	video("video"),//视频消息
	link("link"),//视频消息
	event("event");//事件消息
	
	private String name;
	
	private MsgType(String name) {
	     this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	public static void main(String[] args){
		System.out.println(getMsgType("text"));
	}

	/**
	 * 根据消息类型查找枚举类实例
	 * @param msgType
	 * @return
	 */
	public static MsgType getMsgType(String msgType) {
		if(msgType==null){
			return null;
		}
		
		MsgType[] types = MsgType.values();
		for(MsgType type:types){
			if(type.name.equals(msgType)){
				return type;
			}
		}
		return null;
	}
}
