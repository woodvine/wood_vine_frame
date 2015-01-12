package com.wood.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.wood.pojo.Token;
import com.wood.service.FansService;
import com.wood.util.WeChatApiUtil;

/**
 * 
 * @title       :FansServiceImpl
 * @description :
 * @update      :2014-10-23 下午5:30:23
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2014-10-23
 */
public class FansServiceImpl extends BaseServiceImpl implements FansService {

	@Override
	public Map<String, Object> changeFansGroup(Map<String, String> parameter) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "success");
		
		Token token = getToken(parameter);
		if(token==null){
			result.put("result", "failure");
			result.put("failureMsg", "Token invalid.");
			return result;
		}
		
		String path = WeChatApiUtil.getChangeFansGroupUrl(token.getAccessToken());
		String data = parameter.get("data");
		String response = WeChatApiUtil.httpsRequestToString(path, "POST", data);
		result.put("response",response);
		return result;
	}

	@Override
	public Map<String, Object> getFansGroup(Map<String, String> parameter) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "success");
		
		Token token = getToken(parameter);
		if(token==null){
			result.put("result", "failure");
			result.put("failureMsg", "Token invalid.");
			return result;
		}
		
		String path = WeChatApiUtil.getQueryFansGroupUrl(token.getAccessToken());
		String data = parameter.get("data");
		String response = WeChatApiUtil.httpsRequestToString(path, "POST", data);
		result.put("response",response);
		return result;
	}

	@Override
	public Map<String, Object> setFansRemark(Map<String, String> parameter) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "success");
		
		Token token = getToken(parameter);
		if(token==null){
			result.put("result", "failure");
			result.put("failureMsg", "Token invalid.");
			return result;
		}
		
		String path = WeChatApiUtil.getFansRemarkUrl(token.getAccessToken());
		String data = parameter.get("data");
		String response = WeChatApiUtil.httpsRequestToString(path, "POST", data);
		result.put("response",response);
		return result;
	}

	@Override
	public Map<String, Object> getFansInfo(Map<String, String> parameter) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "success");
		
		Token token = getToken(parameter);
		if(token==null){
			result.put("result", "failure");
			result.put("failureMsg", "Token invalid.");
			return result;
		}
		
		String openId = parameter.get("openId");
		String path = WeChatApiUtil.getFansInfoUrl(token.getAccessToken(),openId);
		String response = WeChatApiUtil.httpsRequestToString(path, "GET", null);
		if(response==null||response.contains("errmsg")){
			result.put("result", "failure");
			result.put("failureMsg", "invalid openid.");
			return result;
		}
		
		result.put("response", response);
		return result;
	}

	@Override
	public Map<String, Object> getFansList(Map<String, String> parameter) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("result", "success");
		
		Token token = getToken(parameter);
		if(token==null){
			result.put("result", "failure");
			result.put("failureMsg", "Token invalid.");
			return result;
		}
		
		String nextOpenId = parameter.get("nextOpenId");
		nextOpenId = nextOpenId==null?"":nextOpenId;
		
		String path = WeChatApiUtil.getFansListUrl(token.getAccessToken(),nextOpenId);
		String response = WeChatApiUtil.httpsRequestToString(path, "GET", null);
		

		result.put("response", response);
		return result;
	}

}
