package com.wood.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wood.model.TbEnglish;
import com.wood.model.TbUser;
import com.wood.pojo.ActionResponse;
import com.wood.service.define.EnglishService;
import com.wood.util.StringUtil;
import com.wood.util.SystemConstant;

/**
 * 
 * @title       :EnglishAction
 * @description :英语学习相关的Action
 * @update      :2015-1-30 下午2:00:28
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-30
 */
@Controller
public class EnglishAction {
	@Autowired
	private EnglishService englishService;
	
	@RequestMapping(value="/dailyOne")
	public ModelAndView dailyOne(HttpSession session){
		ModelAndView view =  new ModelAndView("/daily_one");
		//随机查询一条名言名句
		ActionResponse response = englishService.getGoodEnglish();
		view.addObject("goodOne",StringUtil.toJson(response.getResult()));
		return view;
	}
	
	@RequestMapping(value="/englishList")
	public ModelAndView toEnglishList(HttpSession session){
		ModelAndView view =  new ModelAndView("/english_list");
		TbUser user = (TbUser)session.getAttribute(SystemConstant.CURRENT_USER);
		
		//set info of current user
		if(user!=null){
			Integer id = user.getId();
			view.addObject("userId",id);
		}
		
		return view;
	}
	
	@RequestMapping(value="/queryEnglishList")
	@ResponseBody
	public ActionResponse queryEnglishList(TbEnglish English){
		return englishService.queryEnglishByPage(English);
	}
	
	@RequestMapping(value="/toAddEnglish")
	public ModelAndView toAddEnglish(HttpSession session){
		ModelAndView view =  new ModelAndView("/english_add");
		TbUser user = (TbUser)session.getAttribute(SystemConstant.CURRENT_USER);
		if(user!=null){
			view.addObject("author",user.getId());
		}
		return view;
	}
	
	@RequestMapping(value="/addEnglish")
	@ResponseBody
	public ActionResponse addEnglish(TbEnglish English){
		return englishService.addEnglish(English);
	}
	
	@RequestMapping(value="/deleteEnglish")
	@ResponseBody
	public ActionResponse deleteEnglish(Integer id){
		return englishService.deleteEnglish(id);
	}
	
	@RequestMapping(value="/getEnglish")
	@ResponseBody
	public ActionResponse getEnglish(Integer id){
		return englishService.getEnglish(id);
	}
	
	@RequestMapping(value="/getGoodEnglish")
	@ResponseBody
	public ActionResponse getGoodEnglish(){
		return englishService.getGoodEnglish();
	}
}
