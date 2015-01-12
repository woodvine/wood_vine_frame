package com.wood.service;

import java.util.Map;

public interface GroupService  extends BaseService{
	public Map<String,Object> createGroup(Map<String,String>parameter);
	public Map<String,Object> updateGroup(Map<String,String>parameter);
	public Map<String,Object> queryAllGroup(Map<String,String>parameter);

}
