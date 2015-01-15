package com.bh.wexin.bean;

/**
 * 
 * @title       :BaseMsg
 * @description :微信消息公共属性抽象类
 *               微信推送的消息和开发者服务器响应的消息公用的属性
 *               BaseMsg
 *                  |----RequestMsg(微信推送)
 *                  |           |------RequestTextMsg
 *                  |           |------RequestImageMsg
 *                  |           |------RequestVoiceMsg
 *                  |           |------RequestVideoMsg
 *                  |           |------RequestLocationMsg
 *                  |           |------RequestLinkMsg
 *                  |
 *                  |----ResponseMsg(Action响应)
 *                  |           |------ResponseTextMsg
 *                  |           |------ResponseImageMsg
 *                  |           |------ResponseVoiceMsg
 *                  |           |------ResponseVideoMsg
 *                  |           |------ResponseMusicMsg
 * @update      :2015-1-14 下午2:25:20
 * @author      :
 * @version     :1.0.0
 * @since       :2015-1-14
 */
public abstract class BaseMsg {
	
		protected String toUserName;  
	    // 发送方帐号
		protected String fromUserName;  
	    // 消息创建时间 （整型）
		protected long createTime;  
	    // 消息类型
		protected String msgType;
		
		public String getToUserName() {
			return toUserName;
		}
		public void setToUserName(String toUserName) {
			this.toUserName = toUserName;
		}
		public String getFromUserName() {
			return fromUserName;
		}
		public void setFromUserName(String fromUserName) {
			this.fromUserName = fromUserName;
		}
		public long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(long createTime) {
			this.createTime = createTime;
		}
		public String getMsgType() {
			return msgType;
		}
		public void setMsgType(String msgType) {
			this.msgType = msgType;
		}
		
}
