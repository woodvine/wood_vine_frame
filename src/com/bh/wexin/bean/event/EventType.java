package com.bh.wexin.bean.event;

/**
 * 
 * @title       :Event
 * @description :微信推送事件类型,当MsgType为event时，对应的Event参数值，同时具有EventKey
 * @update      :2015-1-14 上午10:40:59
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-14
 */
public enum EventType {
	subscribe("subscribe"),//
	unsubscribe("unsubscribe"),//
	SCAN("SCAN"),//
	LOCATION("LOCATION"),//
	CLICK("CLICK"),//
	VIEW("VIEW");
	
	private String name;
	
	private EventType(String name) {
	     this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}
}
