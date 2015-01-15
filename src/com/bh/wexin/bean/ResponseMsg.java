package com.bh.wexin.bean;

/**
 * 
 * @title :ResponseMsg
 * @description :微信请求响应消息抽象
 * @update :2015-1-14 下午2:26:05
 * @author :
 * @version :1.0.0
 * @since :2015-1-14
 */
public class ResponseMsg extends BaseMsg {

	public ResponseMsg() {
		this.createTime = System.currentTimeMillis();
	}

	public ResponseMsg(String toUserName, String fromUserName, String msgType) {
		this();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.msgType = msgType;
	}

	public ResponseMsg(BaseMsg reqMsg, String msgType) {
		this(reqMsg.fromUserName, reqMsg.toUserName, msgType);
	}
}
