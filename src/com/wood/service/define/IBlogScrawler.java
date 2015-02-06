package com.wood.service.define;

import com.wood.pojo.ActionResponse;

public interface IBlogScrawler {
	public ActionResponse scrawl(String blogOwner);
	public ActionResponse xmlSearch();

}
