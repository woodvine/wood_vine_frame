package com.wood.util;

import java.util.Date;

public class LogUtil {
	public static void error(Exception e){
		e.printStackTrace();
	}
	public static void info(String info){
		System.out.println(new Date()+" "+info);
	}
}
