package com.wood.pojo;

/**
 * 接口凭证 
 * @author brain
 *
 */
public class Token {
	private String accessToken;// 接口访问凭证
	private int expiresIn;// 凭证有效期，单位：秒
	
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	public String toString(){
		return "access_token=["+accessToken+"]";
	}
	
public static void main(String[] args) {
	//强制转成Double
	Object o1 = true?new Integer(1):new Double("2.0");
	System.out.println("Integer(1):"+o1);
	
	//OK
	Object o2 = true?null:new Double("2.0");
	System.out.println(o2);
	
	//因类型转而异常
	Integer i = null;
	Object o3 = true?i:new Double("2.0");
	System.out.println(o3);
}
}