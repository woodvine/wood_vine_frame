package com.wood.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wood.util.EncryptUtil;
import com.wood.dao.TbUserDao;
import com.wood.model.TbAuthority;
import com.wood.model.TbUser;
import com.wood.pojo.ActionResponse;
import com.wood.service.define.UserService;
import com.wood.util.DateTimeUtil;
import com.wood.util.SystemConstant;

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
			response.setDescription("用户名和密码不一致!");
		}else if(user.getStatus()==0){
			response.setDescription("该用户已被禁用，如有疑问请致电管理员:58132588！");
		}else if(EncryptUtil.checkByMD5(userPwd, user.getUserPwd())){
			//校验密码
			response.setResult(user);
			response.setStatus(true);
			
			//修改登录时间
			user.setLastLoginTime(DateTimeUtil.nowAsString());
			userDao.updateUserLogintime(user);
		}else{
			response.setDescription("用户名和密码不一致！");
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
			//加密密码信息后存储
			String encPwd = EncryptUtil.encryptByMD5(user.getUserPwd());
			//修正密码为密文
			user.setUserPwd(encPwd);
			boolean flag = userDao.insertUser(user);
			if(flag){
				response.setStatus(true);
			}else{
				response.setDescription("添加用户失败，稍后请重试!");
			}
		}
		return response;
	}

	@Override
	public ActionResponse modifyUserInfo(TbUser user,int modifyType) {
		ActionResponse response = new ActionResponse();
		//密码修改，需判断原密码
		if(SystemConstant.CHANGE_PWD==modifyType){
			//原记录
			TbUser oldRecord = userDao.queryById(user.getId());
			String oldPlain = user.getUserOldPwd();
			String oldCyper = oldRecord.getUserPwd();
			if(EncryptUtil.checkByMD5(oldPlain, oldCyper)){
				String newCyper = EncryptUtil.encryptByMD5(user.getUserOldPwd());
				user.setUserPwd(newCyper);
				userDao.updateUser(user);
				response.setStatus(true);
			}else{
				response.setDescription("原密码输入错误,密码修改失败!");
			}
		}else{
			userDao.updateUser(user);
			response.setStatus(true);
		}
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
