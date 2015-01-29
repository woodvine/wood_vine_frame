package com.bh.wexin.service;

import com.bh.wexin.bean.BaseMsg;
import com.bh.wexin.bean.ResponseMsg;
import com.bh.wexin.bean.request.MsgType;

/**
 * 
 * @title       :MsgService
 * @description :普通消息处理
 *               调用者根据MsgType确定是否需要调用其onMsg处理相应的消息
 * @update      :2015-1-14 下午4:18:22
 * @author      :
 * @version     :1.0.0
 * @since       :2015-1-14
 */
public interface IMsgHandler {
	//处理的消息类型
	public MsgType getMsgType();
	
	//消息处理方法
	public ResponseMsg onMsg(BaseMsg msg);
	
}
