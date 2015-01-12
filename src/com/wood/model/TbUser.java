package com.wood.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.wood.pojo.PaginationBean;

/**
 * 
 * @title       :TbUser
 * @description :用户表tb_user
 * @update      :2015-1-4 下午2:40:20
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-4
 */
public class TbUser extends PaginationBean 
	implements Serializable {
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String createTime;
	private String email;
	private Integer groupId;
	private String id;
	private String lastLoginTime;
	private String loginName;
	private String nickname;
	private String picUrl;
	private TbRole role;
	private Integer status;
	private String userName;
	private String userPwd;
	private Integer roleId;

	// Constructors
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	/** default constructor */
	public TbUser() {
	}

	/** minimal constructor */
	public TbUser(String id) {
		this.id = id;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public String getEmail() {
		return this.email;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public String getId() {
		return this.id;
	}

	public String getLastLoginTime() {
		return this.lastLoginTime;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public String getNickname() {
		return this.nickname;
	}

	public String getPicUrl() {
		return this.picUrl;
	}

	public TbRole getRole() {
		return role;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setRole(TbRole role) {
		this.role = role;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
}