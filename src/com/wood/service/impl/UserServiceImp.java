package com.wood.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wood.dao.TbUserDao;
import com.wood.model.TbAuthority;
import com.wood.model.TbUser;
import com.wood.pojo.ActionResponse;
import com.wood.service.define.UserService;
import com.wood.util.DateTimeUtil;

/**
 * 
 * @title       :UserServiceImp
 * @description :业务层，如果需要扩展，则可重新实现对应的接口，修改配置
 *               Action不用变更。
 * @update      :2015-1-8 上午11:03:19
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-8
 */
@Service
public class UserServiceImp implements UserService{
	@Autowired
	private TbUserDao userDao;

	@Override
	public ActionResponse login(String loginName,String userPwd){
		ActionResponse response = new ActionResponse();
		
		TbUser user = userDao.queryByName(loginName);
		if(user==null){
			response.setDescription("用户不存在!");
		}else if(user.getUserPwd().equals(userPwd)){
			response.setResult(user);
			response.setStatus(true);
			
			//修改登录时间
			user.setLastLoginTime(DateTimeUtil.nowAsString());
			userDao.updateUserLogintime(user);
		}else if(user.getStatus()==0){
			response.setDescription("该用户已被禁用，如有疑问请致电管理员:58132588！");
		}else{
			response.setDescription("密码错误！");
		}
		
		return response;
	}

	@Override
	public ActionResponse register(TbUser user) {
		ActionResponse response = new ActionResponse();
		String loginName = user.getLoginName();
		boolean isUserExist = userDao.isUserExist(loginName);
		if(isUserExist){
			response.setDescription("登陆名为"+loginName+"已存在，请重新填写!");
		}else{
			user.setRoleId(3);//角色为普通用户
			boolean flag = userDao.insertUser(user);
			if(flag){
				response.setStatus(true);
			}else{
				response.setDescription("注册失败，稍后请重试!");
			}
		}
		return response;
	}

	@Override
	public ActionResponse modifyPwd(TbUser user) {
		ActionResponse response = new ActionResponse();
		userDao.updateUserPwd(user);
		response.setStatus(true);
		return response;
	}

	@Override
	public ActionResponse queryByPage(TbUser user){
		ActionResponse response = new ActionResponse();
		int totalCount = userDao.queryTotalCount(user);
		List<TbUser> list = userDao.queryByPage(user);
		user.setTotal(totalCount);
		response.setDescription("OK");
		response.setStatus(true);
		response.setResult(list);
		response.setExt(user);//返回分页信息
		return response;
	}

	@Override
	public ActionResponse queryAuthority(TbUser user) {
		ActionResponse response = new ActionResponse();
		List<TbAuthority> list = userDao.queryAuthority(user.getRoleId());
		response.setResult(list);
		response.setStatus(true);
		return response;
	}

}
