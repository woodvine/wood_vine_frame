package com.bh.wexin.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bh.wexin.bean.ResponseMsg;
import com.bh.wexin.bean.request.MsgType;
import com.bh.wexin.bean.request.RequestTextMsg;
import com.bh.wexin.service.HandlerDispatcher;
import com.wood.util.SignUtil;

/**
 * 
 * @title       :WxCoreAction
 * @description :微信的核心Action
 * @update      :2015-1-15 下午1:27:11
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-15
 */
@Controller
public class WxCoreAction {
	@Autowired
	private HandlerDispatcher requestHandler;
	
	/**
	 * GET请求：进行URL、Tocken 认证；
	 * 1. 将token、timestamp、nonce三个参数进行字典序排序
	 * 2. 将三个参数字符串拼接成一个字符串进行sha1加密
	 * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
	 * 这里可以添加多个账户，不同的account/tocken，提供给微信平台，只要验证通过即可
	 */
	@RequestMapping(value = "/signWx",  method = RequestMethod.GET)
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
	
	
	@RequestMapping(value = "/requestWx",produces = {"text/xml;charset=UTF-8"}, method = RequestMethod.POST)
	public @ResponseBody String doPost(HttpServletRequest request,@PathVariable String account,HttpServletResponse response) {
		//处理用户和微信公众账号交互消息
		String appId = null;
		String appSecret = null;
		
		RequestTextMsg msg = new RequestTextMsg();
		msg.setMsgType(MsgType.text.name());
//		requestHandler.addMsgHandler(service);
		
//		System.out.println(service.getRequestHandler()==requestHandler);
		//查找服务执行者
		
		return null;
	}
	
	@RequestMapping(value = "/testWx",produces = {"text/xml;charset=UTF-8"}, method = RequestMethod.GET)
	public @ResponseBody ResponseMsg doPost() {
		//处理用户和微信公众账号交互消息
		String appId = null;
		String appSecret = null;
		
		//解析请求数据
		RequestTextMsg msg = new RequestTextMsg();
		msg.setMsgType(MsgType.text.name());
		
		//执行
		return requestHandler.doDispatch(msg);
	}
	
	public static void main(String[] args) {
		SignUtil.validSign("", "helloworld", "123", "45689");
	}
}
