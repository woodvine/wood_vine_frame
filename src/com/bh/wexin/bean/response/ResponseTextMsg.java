package com.bh.wexin.bean.response;

import com.bh.wexin.bean.BaseMsg;
import com.bh.wexin.bean.ResponseMsg;
import com.bh.wexin.bean.request.MsgType;

public class ResponseTextMsg extends ResponseMsg{
	private String content;

	public ResponseTextMsg(BaseMsg req, String content)
	{
		super(req, MsgType.text.name());
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
