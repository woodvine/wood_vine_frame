package com.wood.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.wood.service.impl.QcodeServiceImpl;

public class TestQcodeService {
	public static void main(String[] args) {
		QcodeServiceImpl impl = new QcodeServiceImpl();
		
		Map<String,String> param  = new HashMap<String,String>();
		param.put("appId", "wx9dcde6b2042b9ca2");
		param.put("appSecret", "9c8e3e25a12a24320829dcdb23fbdab7");
		param.put("data", "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 12345}}}");
		param.put("fileName", "E:/qcode.jpg");
		
		File file = impl.showQCode(param);
		if(file!=null&&file.exists()){
			System.out.println("qcoder ok...");
		}else{
			System.out.println("wrong");
		}
	}
}
