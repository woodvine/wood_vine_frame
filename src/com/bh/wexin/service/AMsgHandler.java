package com.bh.wexin.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @title       :AMsgHandler
 * @description :消息处理类的顶层抽象类
 * @update      :2015-1-15 下午1:22:26
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-15
 */
public abstract class AMsgHandler implements IMsgHandler{
	@Autowired
	private HandlerDispatcher requestHandler;

	public AMsgHandler(HandlerDispatcher requestHandler) {
		this.requestHandler = requestHandler;
	}
	
	public AMsgHandler(){
		System.out.println("hello");
	}

	@PostConstruct
    public void initRequestHandler(){
		if(this.requestHandler!=null){
			this.requestHandler.addMsgHandler(this);
			System.out.println("add msgService OK");
		}else{
			System.out.println("add msgService bad");
		}
	} 
	
	public HandlerDispatcher getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(HandlerDispatcher requestHandler) {
		this.requestHandler = requestHandler;
		if(this.requestHandler!=null){
			this.requestHandler.addMsgHandler(this);
			System.out.println("add msgService OK");
		}else{
			System.out.println("add msgService bad");
		}
	}
}
