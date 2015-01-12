package com.wood.pojo;

/**
 * 
 * @title       :MsgType
 * @description :消息类型枚举类
 * @update      :2014-10-23 下午2:50:12
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2014-10-23
 */
public enum MsgType {
	Text("text"),//文本消息
	News("news"),//图文消息
	Location("location"),//地理位置消息
	Image("image"),//图片消息
	Voice("voice"),//语音消息
	Video("video"),//视频消息
	Event("event"),//事件消息
	
	SUBSCRIBE("subscribe"),//订阅消息
	UNSUBSCRIBE("unsubscribe");//取消订阅
	
	private String name;
	
	private MsgType(String name) {
	     this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
	
	public static void main(String[] args){
		System.out.println(MsgType.Text);
	}
}
