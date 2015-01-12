package com.wood.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wood.model.TbAuthority;
import com.wood.model.TbUser;
import com.wood.util.StringUtil;

/**
 * 
 * @title       :TbUserDao
 * @description :user dao
 * @update      :2015-1-6 上午11:27:19
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-6
 */
@SuppressWarnings("deprecation")
@Repository
public class TbUserDao  extends SqlMapClientDaoSupport{
	private Logger logger = Logger.getLogger(TbUserDao.class.getName());
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	@PostConstruct
    public void initSqlMapClient(){
	     super.setSqlMapClient(sqlMapClient);
	} 
	
	/**
	 * 使用try-catch捕获异常，避免异常被淹没无处可查的情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TbUser> queryByPage(TbUser user) {
		List<TbUser> list = null;
		try {
			list =this.getSqlMapClientTemplate().queryForList("baseSql.queryUserByPage",user);
		} catch (Exception e) {
			logger.error("TbUserDao queryByPage error", e);
		}
		return list;
	}
	
	/**
	 * 使用try-catch捕获异常，避免异常被淹没无处可查的情况
	 * @return
	 */
	public int queryTotalCount(TbUser user) {
		int totalCount = 0;
		try {
			totalCount =(Integer)this.getSqlMapClientTemplate().queryForObject("baseSql.queryUserCount",user);
		} catch (Exception e) {
			logger.error("TbUserDao queryTotalCount error", e);
		}
		return totalCount;
	}
	
	public TbUser queryByName(String loginName){
		TbUser user = null;
		if(StringUtil.isEmpty(loginName)){
			return user;
		}
		
		try {
			user =(TbUser)this.getSqlMapClientTemplate().queryForObject("baseSql.queryUserByName",loginName);
		} catch (Exception e) {
			logger.error("TbUserDao queryUserByName error", e);
		}
		
		return user;
	}
	
	public boolean insertUser(TbUser user){
		if(user==null){
			return false;
		}
		
		try {
			this.getSqlMapClientTemplate().insert("baseSql.addUser",user);
			return true;
		} catch (Exception e) {
			logger.error("TbUserDao addUser error", e);
			return false;
		}
	}

	/**
	 * 判断某个用户是否存在
	 * @param loginName
	 * @return
	 */
	public boolean isUserExist(String loginName){
		boolean flag = false;
		if(StringUtils.isEmpty(loginName)){
			return flag;
		}
		
		try {
			int count = (Integer)this.getSqlMapClientTemplate().queryForObject("baseSql.isUserExist",loginName);
			return !(count==0);
		} catch (Exception e) {
			logger.error("TbUserDao isUserExist error", e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TbAuthority> queryAuthority(int roleId) {
		List<TbAuthority> list = null;
		try {
			list =this.getSqlMapClientTemplate().queryForList("baseSql.queryAuthorityByRoleId",roleId);
		} catch (Exception e) {
			logger.error("TbUserDao queryAuthority error", e);
		}
		return list;
	}

	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public boolean updateUserPwd(TbUser user) {
		if(user==null){
			return false;
		}
		
		try {
			this.getSqlMapClientTemplate().update("baseSql.updateUserPwd",user);
			return true;
		} catch (Exception e) {
			logger.error("TbUserDao updateUserPwd error", e);
			return false;
		}
	}
	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public boolean updateUserLogintime(TbUser user) {
		if(user==null){
			return false;
		}
		
		try {
			this.getSqlMapClientTemplate().update("baseSql.updateUserLogintime",user);
			return true;
		} catch (Exception e) {
			logger.error("TbUserDao updateUserLogintime error", e);
			return false;
		}
	}
}
