package com.wood.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.wood.service.MsgService;

public class MsgServiceImpl implements MsgService {

	@Override
	public String processMsg(HttpServletRequest request, String appId,
			String appSecret) {
		// TODO Auto-generated method stub
		return null;
	}

	private String processTextMsg(String xml){
		return null;
	}
	
	/*
	 * 关注时事件：粉丝记录入口 ，状态可用
	 * @param xml
	 * @return
	 */
	private String processSubscribeMsg(String xml){
		return null;
	}
	
	/*
	 * 取消关注事件：状态不可用
	 */
	private String processUnSubscribeMsg(String xml){
		return null;
	}
	
	/*
	 * 菜单click事件
	 */
	private String processClickEvent(String xml){
		return null;
	}
	
	/*
	 * 菜单view事件
	 */
	private String processViewEvent(String xml){
		return null;
	}
	
	/*
	 * 扫码事件
	 */
	private String processScanCodeEvent(String xml){
		return null;
	}
	
	
}
