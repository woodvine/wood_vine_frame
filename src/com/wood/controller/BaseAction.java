package com.wood.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.wood.model.TbUser;
import com.wood.pojo.ActionResponse;
import com.wood.service.define.UserService;
import com.wood.util.StringUtil;
import com.wood.util.SystemConstant;

/**
 * 
 * @title       :BaseAction
 * @description :系统管理相关的基础Action
 *               1 基础首页框架
 *               2 菜单展示
 *               3 注册/登陆/退出
 * @update      :2015-1-6 上午10:42:52
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-6
 */
@Controller
public class BaseAction {
	@Autowired
	private UserService userService;
	
	/**
	 * 登陆
	 * @param loginName
	 * @param userPwd
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login")
	public ModelAndView login(String loginName,String userPwd,HttpSession session){
		ModelAndView view =  new ModelAndView("/login");
		ActionResponse response = userService.login(loginName, userPwd);
		if(response.isStatus()){
			view = new ModelAndView("main");
			session.setAttribute(SystemConstant.CURRENT_USER,response.getResult());
		}else{
			view.addObject("msg", response.getDescription());
		}
		
		return view;
	}
	
	/**
	 * 退出
	 * @param loginName
	 * @param userPwd
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	@ResponseBody
	public ActionResponse logout(HttpSession session){
		session.setAttribute(SystemConstant.CURRENT_USER,null);
		ActionResponse response = new ActionResponse();
		response.setStatus(true);
		return response;
	}
	
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping(value="/toRegister")
	public ModelAndView toRegister(){
		ModelAndView view =  new ModelAndView("/base_user_add");
		return view;
	}
	
	/**
	 * 提交注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/submitRegister")
	@ResponseBody
	public ActionResponse register(TbUser user){
		return userService.register(user);
	}
	
	/**
	 * 注册结果返回，由页面选择跳转或不予处理
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/userManage")
	@ResponseBody
	public ModelAndView userManage(){
		ModelAndView view = new ModelAndView("/base_user");
		return view;
	}
	
	/**
	 * 注册结果返回，由页面选择跳转或不予处理
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/queryUsers")
	@ResponseBody
	public ActionResponse queryUsers(TbUser user){
		return userService.queryByPage(user);
	}
	
	/**
	 * 注册结果返回，由页面选择跳转或不予处理
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/modifyUserInfo")
	@ResponseBody
	public ActionResponse modifyUserInfo(TbUser user,Integer modifyType){
		if(modifyType==null){
			modifyType = SystemConstant.CHANGE_INFO;
		}
		return userService.modifyUserInfo(user,modifyType);
	}
	
	@RequestMapping(value="/header")
	public ModelAndView header(HttpSession session){
		ModelAndView view = new ModelAndView("main_header");
				
		TbUser user = (TbUser)session.getAttribute(SystemConstant.CURRENT_USER);
		if(user==null){
			user = new TbUser();
			user.setRoleId(4);
		}else{
		}
		ActionResponse response = userService.queryAuthority(user);
		if(response.isStatus()){
			view.addObject("authoritys",StringUtil.toJson(response.getResult()));
		}
		view.addObject(SystemConstant.CURRENT_USER,StringUtil.toJson(user));
		return view;
	}
	
	@RequestMapping(value="/footer")
	public ModelAndView footer(){
		ModelAndView view = new ModelAndView("main_footer");
		return view;
	}
}
