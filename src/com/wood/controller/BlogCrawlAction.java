package com.wood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wood.pojo.ActionResponse;
import com.wood.service.define.IBlogScrawler;

/**
 * 
 * @title       :BlogCrawlAction
 * @description :博客文章抓取的Action
 * 				目前提供指定博客网站，指定博主的文章抓取功能
 * @update      :2015-2-2 上午9:30:02
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-2-2
 */
@Controller
public class BlogCrawlAction {
	@Autowired
	private IBlogScrawler scrawler;
	
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping(value="/toScrawlBlog")
	public ModelAndView toRegister(){
		ModelAndView view =  new ModelAndView("/scrawl_blog");
		return view;
	}
	
	@RequestMapping(value="/scrawlBlog")
	@ResponseBody
	public ActionResponse scrawlBlog(String blogOwner){
		ActionResponse response = new ActionResponse();
		response = scrawler.scrawl(blogOwner);
//		response = scrawler.xmlSearch();
		return response;
	}

}
