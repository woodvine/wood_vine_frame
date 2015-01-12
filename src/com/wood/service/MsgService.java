package com.wood.service;

import javax.servlet.http.HttpServletRequest;

public interface MsgService {
	public String processMsg(HttpServletRequest request,String appId,String appSecret);

	
}
