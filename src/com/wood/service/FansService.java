package com.wood.service;

import java.util.Map;

public interface FansService extends BaseService{
	public Map<String,Object> changeFansGroup(Map<String,String> parameter);
	public Map<String,Object> getFansGroup(Map<String,String> parameter);
	public Map<String,Object> setFansRemark(Map<String,String> parameter);
	public Map<String,Object> getFansInfo(Map<String,String> parameter);
	public Map<String,Object> getFansList(Map<String,String> parameter);

}
