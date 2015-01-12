package com.wood.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.wood.service.impl.FansServiceImpl;

public class TestFansService {
	public static void main(String[] args) {
		FansServiceImpl impl = new FansServiceImpl();
		
		Map<String,String> param  = new HashMap<String,String>();
		param.put("appId", "wx7a45ffbe1ca6dbf4");
		param.put("appSecret", "f66e397162bb791652aef1dd9fb0aedb");
		
		String info =(String) impl.getFansList(param).get("response");
		System.out.println("info="+info);
		
		param.put("openId", "otU1_jm8koNaKRaQab8yJ5yi76nY");
		info =(String) impl.getFansInfo(param).get("response");
		System.out.println("info="+info);
		
		long t = 1414030944;
		
		String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date(1414030944 * 1000));
		System.out.println("date:"+date);
		
		Date d = new Date();
		System.out.println(d.getTime());
		
		Timestamp time = new Timestamp(t);
//		d.setTime(t);
		System.out.println("time:"+time);
		
	}

}
