package com.wood.model;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * @title       :TbRole
 * @description :角色表tb_role
 * @update      :2015-1-4 下午2:39:40
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-4
 */
public class TbRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<TbAuthority> authoritySet;
	private Integer id;
	private String roleDesc;
	private String roleName;
	private Integer status ;

	public TbRole() {

	}

	public TbRole(int roleId) {
		this.id = roleId;
	}

	public Set<TbAuthority> getAuthoritySet() {
		return authoritySet;
	}

	public Integer getId() {
		return id;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setAuthoritySet(Set<TbAuthority> authoritySet) {
		this.authoritySet = authoritySet;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("TbRole=[roleName:"+roleName);
		buffer.append(",roleDesc:"+roleDesc);
		buffer.append("]");
		return buffer.toString();
	}
}
