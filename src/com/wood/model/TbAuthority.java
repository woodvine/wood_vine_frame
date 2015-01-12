package com.wood.model;

import java.io.Serializable;
import java.util.Set;

/**
 * 
 * @title       :TbAuthority
 * @description :权限表tb_authority
 * @update      :2015-1-4 下午2:39:23
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-4
 */
public class TbAuthority implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authorityDesc;
	private String authorityName;
	private String authorityUrl;
	private Set<TbAuthority> children;
	private Integer id;
	private TbAuthority parent;
	private Integer typeOrder;
	private String  type; // 标示当前权限信息是功能点，或是后台菜单项，只有功能点需要在拦截其中拦截

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTypeOrder() {
		return typeOrder;
	}

	public void setTypeOrder(Integer typeOrder) {
		this.typeOrder = typeOrder;
	}

	public String getAuthorityDesc() {
		return authorityDesc;
	}

	public String getAuthorityName() {
		return authorityName;
	}


	public String getAuthorityUrl() {
		return authorityUrl;
	}

	public void setAuthorityUrl(String authorityUrl) {
		this.authorityUrl = authorityUrl;
	}

	public Set<TbAuthority> getChildren() {
		return children;
	}

	public Integer getId() {
		return id;
	}

	public TbAuthority getParent() {
		return parent;
	}

	public void setAuthorityDesc(String authorityDesc) {
		this.authorityDesc = authorityDesc;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	

	public void setChildren(Set<TbAuthority> children) {
		this.children = children;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setParent(TbAuthority parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "TbAuthority [authorityDesc=" + authorityDesc
				+ ", authorityName=" + authorityName + ", authorityURL="
				+ authorityUrl + ", id=" + id + ", order=" + typeOrder + "]";
	}

}
