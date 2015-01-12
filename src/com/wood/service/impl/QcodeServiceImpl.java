package com.wood.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.wood.pojo.Token;
import com.wood.service.QcodeService;
import com.wood.util.WeChatApiUtil;

public class QcodeServiceImpl extends BaseServiceImpl implements QcodeService{
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Override
	public File showQCode(Map<String,String>parameter) {
		//二维码JSON信息
		String info = parameter.get("data");
		String fileName = parameter.get("fileName");
		
		//获取Token
		Token token = getToken(parameter);
		if(token!=null){
			//生成二维码
			String qCodeUrl = WeChatApiUtil.getQCodeUrl(token.getAccessToken());
			JSONObject o = JSONObject.fromObject(WeChatApiUtil.httpsRequestToString(qCodeUrl, "POST", info));
			if(o!=null&&o.containsKey("ticket")){
				String ticketId = o.getString("ticket");
				
				//对ticket进行编码
				try {
					ticketId = URLEncoder.encode(ticketId, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error(e);
					return null;
				}
				
				//获取二维码
				String ticketUrl = WeChatApiUtil.getShowQCodeUrl(ticketId);
				File result = WeChatApiUtil.httpsRequestToFile(fileName,ticketUrl, "GET", null);
				return result;
			}
		}
		
		return null;
	}

}
