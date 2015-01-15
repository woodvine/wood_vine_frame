package com.wood.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wood.service.MsgService;
import com.wood.util.SignUtil;

/**
 * 
 * @title       :CoreAction
 * @description :
 * @update      :2014-12-2 下午1:25:39
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2014-12-2
 */
@Controller
public class CoreAction {
	private MsgService wxMsgService;
	
	/**
	 * GET请求：进行URL、Tocken 认证；
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * 这里可以添加多个账户，不同的account/tocken，提供给微信平台，只要验证通过即可
	 */
	@RequestMapping(value = "/{account}/wxapi",  method = RequestMethod.GET)
	@ResponseBody
	public String doGet(HttpServletRequest request) {
		String token = "helloworld";
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		// 校验成功返回 echostr，成功成为开发者；否则返回error，接入失败
		if (SignUtil.validSign(signature, token, timestamp, nonce)) {
			return echostr;
		}
		return "error";
	}
	
	
	@RequestMapping(value = "/{account}/wxapi",produces = {"text/xml;charset=UTF-8"}, method = RequestMethod.POST)
	public @ResponseBody String doPost(HttpServletRequest request,@PathVariable String account,HttpServletResponse response) {
		//处理用户和微信公众账号交互消息
		String appId = null;
		String appSecret = null;
		return wxMsgService.processMsg(request,appId,appSecret);
	}
	
	public static void main(String[] args) {
		SignUtil.validSign("", "helloworld", "123", "45689");
	}
}
