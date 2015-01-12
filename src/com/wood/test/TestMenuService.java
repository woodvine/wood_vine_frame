package com.wood.test;

import java.util.HashMap;
import java.util.Map;

import com.wood.service.impl.MenuServiceImpl;
import com.wood.util.FileUtil;

public class TestMenuService {
	public static void main(String[] args) {
		MenuServiceImpl imp = new MenuServiceImpl();
		Map<String,String> param  = new HashMap<String,String>();
		param.put("appId", "wx7a45ffbe1ca6dbf4");
		param.put("appSecret", "f66e397162bb791652aef1dd9fb0aedb");
		
		String data = FileUtil.read("E:/menu.txt", "UTF-8");
		param.put("menus", data);
		
		boolean falg = imp.publishMenu(param);
		System.out.println("publish result:"+falg);
		 falg = imp.queryMenu(param);
		 System.out.println("query result:"+falg);
		 falg = imp.deleteMenu(param);
		 System.out.println("delete result:"+falg);
		
		
	}

}
