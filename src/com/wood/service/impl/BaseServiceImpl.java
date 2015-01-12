package com.wood.service.impl;

import java.util.Map;

import com.wood.pojo.Token;
import com.wood.service.BaseService;
import com.wood.util.WeChatApiUtil;

/**
 * 
 * @title       :BaseServiceImpl
 * @description :
 * @update      :2014-10-23 下午5:30:14
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2014-10-23
 */
public class BaseServiceImpl implements BaseService {

	@Override
	public Token getToken(Map<String, String> parameter) {
		String appId = parameter.get("appId");
		String appSecret = parameter.get("appSecret");
		
		// 获取Token
		Token token = WeChatApiUtil.getToken(appId, appSecret);
		return token;
	}

}
