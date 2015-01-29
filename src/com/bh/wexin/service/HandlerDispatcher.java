package com.bh.wexin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bh.wexin.bean.BaseMsg;
import com.bh.wexin.bean.ResponseMsg;
import com.bh.wexin.bean.event.EventType;
import com.bh.wexin.bean.request.MsgType;

/**
 * 
 * @title       :HandlerDispatcher
 * @description :微信推送的请求处理类
 * 				  它包含事件和消息处理类的列表，当收到消息后，根据其类型查找对应的处理类
 *               类似观察者模式的被观察者Observable
 * @update      :2015-1-15 下午1:11:54
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-15
 */
@Service
public class HandlerDispatcher {
	private List<AEventHandler> eventHandlers ;
	private List<AMsgHandler> msgHandlers ;
	
	/**
	 * 构造函数：初始化监听器列表
	 */
	public HandlerDispatcher(){
		this.eventHandlers = new ArrayList<AEventHandler>();
		this.msgHandlers = new ArrayList<AMsgHandler>();
	}

	/**
	 * 注册事件处理类
	 * @param eventHandler
	 */
	public void addEventHandler(AEventHandler eventHandler){
		this.eventHandlers.add(eventHandler);
	}
	
	/**
	 * 注册消息处理类
	 * @param msgHandler
	 */
	public void addMsgHandler(AMsgHandler msgHandler){
		this.msgHandlers.add(msgHandler);
	}
	
	/**
	 * 消息处理方法：提供给Action使用的方法
	 * @param msg
	 * @return
	 */
	public ResponseMsg doDispatch(BaseMsg msg){
		//判断参数
		ResponseMsg r = null;
		if(msg==null){
			return r;
		}
		
		//事件类型：根据事件类型找到对应的处理类
		if ("event".equals(msg.getMsgType())) {
			EventType eventType = EventType.getEventType(msg.getMsgType());
			AEventHandler msgHandler = this.getEventHandler(eventType);
			if (msgHandler != null) {
				r = msgHandler.onEvent(msg);
			}
			return r;
		} 
		
		//消息类型：根据消息类型找到对应的处理类
		MsgType msgType = MsgType.getMsgType(msg.getMsgType());
		AMsgHandler msgHandler = this.getMsgHandler(msgType);
		if (msgHandler != null) {
			r = msgHandler.onMsg(msg);
		}
		return r;
	}
	
	private AEventHandler getEventHandler(EventType enentType){
		if(enentType==null){
			return null;
		}
		
		for(AEventHandler handler:this.eventHandlers){
			if(handler.getEventType()==enentType){
				System.out.println("find the event handler...");
				return handler;
			}
		}
		System.out.println("dont' find the event handler...");
		return null;
	}
	
	private AMsgHandler getMsgHandler(MsgType msgType){
		if(msgType==null){
			return null;
		}
		
		for(AMsgHandler handler:this.msgHandlers){
			if(handler.getMsgType()==msgType){
				System.out.println("find the msg handler...");
				return handler;
			}
		}
		System.out.println("dont' find the msg handler...");
		return null;
	}
}
