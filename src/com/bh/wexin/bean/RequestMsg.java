package com.bh.wexin.bean;

/**
 * 
 * @title       :RequestMsg
 * @description :微信推送的请求消息抽象
 *               各种具体的消息都继承该类
 * @update      :2015-1-14 下午2:25:41
 * @author      :
 * @version     :1.0.0
 * @since       :2015-1-14
 */
public class RequestMsg extends BaseMsg{
	   // 消息id，64位整型
		protected long msgId;

		public long getMsgId()
		{
			return msgId;
		}

		public void setMsgId(long msgId)
		{
			this.msgId = msgId;
		}
}
