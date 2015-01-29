package com.bh.wexin.service;

import com.bh.wexin.bean.BaseMsg;
import com.bh.wexin.bean.ResponseMsg;
import com.bh.wexin.bean.event.EventType;

/**
 * 
 * @title       :IEventHandler
 * @description :事件消息处理
 * @update      :2015-1-14 下午4:18:36
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-14
 */
public interface IEventHandler {
	//事件处理方法
	public ResponseMsg onEvent(BaseMsg msg);
	
	//处理的事件类型
	public EventType getEventType();
}
