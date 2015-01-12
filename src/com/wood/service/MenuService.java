package com.wood.service;

import java.util.Map;

/**
 * 
 * @title       :MenuService
 * @description :简化开发，只在系统初始安装时调用一次
 * @update      :2014-10-27 上午11:07:14
 * @author      :bh
 * @version     :1.0.0
 * @since       :2014-10-27
 */
public interface MenuService  extends BaseService{
	/**
	 * 创建菜单,创建自定义菜单
	 * @param parameter
	 * @return
	 */
	public boolean publishMenu(Map<String,String>parameter);

	/**
	 * 删除菜单,删除当前使用的自定义菜单
	 * @param parameter
	 * @return
	 */
	public boolean deleteMenu(Map<String,String>parameter);
	
	/**
	 * 自定义菜单查询
	 * @param parameter
	 * @return
	 */
	public boolean queryMenu(Map<String,String>parameter);
	
}
