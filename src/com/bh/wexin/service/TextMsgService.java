package com.bh.wexin.service;

import org.springframework.stereotype.Service;

import com.bh.wexin.bean.BaseMsg;
import com.bh.wexin.bean.ResponseMsg;
import com.bh.wexin.bean.request.MsgType;
import com.bh.wexin.bean.response.ResponseTextMsg;

@Service
public class TextMsgService extends AMsgService{
	
	private MsgType msgType = MsgType.text;
	
	@Override
	public ResponseMsg onMsg(BaseMsg msg) {
		String content = "helloworld";
		ResponseTextMsg response = new ResponseTextMsg(msg,content);
		return response;
	}

	@Override
	public MsgType getMsgType() {
		return msgType;
	}

}
