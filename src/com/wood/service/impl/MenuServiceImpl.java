package com.wood.service.impl;

import java.util.Map;

import com.wood.pojo.Token;
import com.wood.service.MenuService;
import com.wood.util.WeChatApiUtil;

/**
 * 
 * @title       :MenuServiceImpl
 * @description :
 * @update      :2014-10-23 下午5:30:07
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2014-10-23
 */
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	@Override
	public boolean publishMenu(Map<String, String> parameter) {
		// 获取Token
		Token token = getToken(parameter);
		if(token==null){
			return false;
		}
		
		String menus = parameter.get("menus");
		String path = WeChatApiUtil.getMenuCreateUrl(token.getAccessToken());
		String response = WeChatApiUtil.httpsRequestToString(path, "POST", menus);
		if(response==null){
			return false;
		}
		
		return response.contains("ok");
	}

	@Override
	public boolean deleteMenu(Map<String, String> parameter) {
		// 获取Token
		Token token = getToken(parameter);
		if(token==null){
			return false;
		}
		
		String path = WeChatApiUtil.getMenuDeleteUrl(token.getAccessToken());
		String response = WeChatApiUtil.httpsRequestToString(path, "GET",null);
		if(response==null){
			return false;
		}
		
		return response.contains("ok");
	}
	
	@Override
	public boolean queryMenu(Map<String, String> parameter) {
		// 获取Token
		Token token = getToken(parameter);
		if(token==null){
			return false;
		}
		
		String path = WeChatApiUtil.getMenuQueryUrl(token.getAccessToken());
		String response = WeChatApiUtil.httpsRequestToString(path, "GET",null);
		if(response==null){
			return false;
		}else{
			System.out.println("response=["+response);
		}
		
		return response.contains("menu");
	}

	
}
