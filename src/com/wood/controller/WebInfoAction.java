package com.wood.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wood.dao.TbUserDao;

/**
 * 
 * @title       :WebInfoAction
 * @description :关于站点信息的Action
 * @update      :2015-1-12 下午3:41:28
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-12
 */
@Controller
public class WebInfoAction {
	@Autowired
	private TbUserDao userDao;
	
	/**
	 * 首页简介
	 * @return
	 */
	@RequestMapping(value="/introduce")
	public ModelAndView introduce(){
		ModelAndView view = new ModelAndView("main_introduce");
		return view;
	}
	
	/**
	 * 站点作者简介
	 * @return
	 */
	@RequestMapping(value="/aboutMe")
	public ModelAndView aboutMe(){
		ModelAndView view = new ModelAndView("main_aboutMe");
		Map<String,String> result = userDao.queryAboutMe();
		
		for(Map.Entry<String, String> entry:result.entrySet()){
			view.addObject(entry.getKey(),""+entry.getValue());
		}
		
		return view;
	}
}
