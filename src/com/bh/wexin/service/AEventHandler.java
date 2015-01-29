package com.bh.wexin.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @title       :AEventHandler
 * @description :事件处理类的顶层抽象
 * 				  它依赖一个被观察者RequestHandler
 * 			            并且在构造时就注册添加事件
 * @update      :2015-1-15 下午1:19:05
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-15
 */
@Service
public abstract class AEventHandler implements IEventHandler{
	@Autowired
	private HandlerDispatcher requestHandler;

	public AEventHandler(HandlerDispatcher requestHandler) {
		this.requestHandler = requestHandler;
	}

	public AEventHandler() {
	}

	@PostConstruct
    public void initRequestHandler(){
		if(this.requestHandler!=null){
			this.requestHandler.addEventHandler(this);
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
			this.requestHandler.addEventHandler(this);
		}
	}
	
}
