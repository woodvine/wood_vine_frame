package com.wood.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wood.model.TbDiary;
import com.wood.model.TbUser;
import com.wood.pojo.ActionResponse;
import com.wood.service.define.DiaryService;
import com.wood.util.SystemConstant;

/**
 * 
 * @title       :RemindAction
 * @description :备忘功能
 * @update      :2015-1-16 下午1:06:25
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-16
 */
@Controller
public class DiaryAction {
	@Autowired
	private DiaryService diaryService;
	
	@RequestMapping(value="/diary")
	public ModelAndView toDiaryList(HttpSession session){
		ModelAndView view =  new ModelAndView("/diary_list");
		TbUser user = (TbUser)session.getAttribute(SystemConstant.CURRENT_USER);
		
		//set info of current user
		if(user!=null){
			Integer id = user.getId();
			view.addObject("userId",id);
		}
		
		return view;
	}
	
	@RequestMapping(value="/queryDiaryList")
	@ResponseBody
	public ActionResponse queryDiaryList(TbDiary diary){
		return diaryService.queryDiaryByPage(diary);
	}
	
	@RequestMapping(value="/toAddDiary")
	public ModelAndView toAddDiary(HttpSession session){
		ModelAndView view =  new ModelAndView("/diary_add");
		TbUser user = (TbUser)session.getAttribute(SystemConstant.CURRENT_USER);
		if(user!=null){
			view.addObject("author",user.getId());
		}
		return view;
	}
	
	@RequestMapping(value="/addDiary")
	@ResponseBody
	public ActionResponse addDiary(TbDiary diary){
		return diaryService.addDiary(diary);
	}
	
	@RequestMapping(value="/deleteDiary")
	@ResponseBody
	public ActionResponse deleteDiary(Integer id){
		return diaryService.deleteDiary(id);
	}
	
	@RequestMapping(value="/getDiary")
	@ResponseBody
	public ActionResponse getDiary(Integer id){
		return diaryService.getDiary(id);
	}
}
