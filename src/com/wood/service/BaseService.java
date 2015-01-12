package com.wood.service;

import java.util.Map;

import com.wood.pojo.Token;

/**
 * 
 * @title       :BaseService
 * @description :公共接口，公用getToken方法
 * @update      :2014-10-23 下午5:21:06
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2014-10-23
 */
public interface BaseService {
	public Token getToken(Map<String,String> parameter);

}
