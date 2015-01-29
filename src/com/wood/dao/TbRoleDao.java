package com.wood.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wood.model.TbRole;

/**
 * 
 * @title       :TbRoleDao
 * @description :DAO类，需要标准Repository，否则不会自动依赖注入的
 * @update      :2015-1-5 下午3:03:04
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-5
 */
@Repository
public class TbRoleDao {
	private Logger logger = Logger.getLogger(TbRoleDao.class.getName());
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;

	/**
	 * 使用try-catch捕获异常，避免异常被淹没无处可查的情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TbRole> queryAll() {
		List<TbRole> list = null;
		try {
			list =this.sqlMapClient.queryForList("baseSql.queryAllRoles");
		} catch (Exception e) {
			logger.error("TbRoleDao queryAll error", e);
		}
		return list;
	}
	
	public TbRole queryById(int id) {
		TbRole role = null;
		try {
			role =(TbRole)this.sqlMapClient.queryForObject("baseSql.queryRoleById",id);
		} catch (Exception e) {
			logger.error("TbRoleDao queryById error", e);
		}
		return role;
	}
}
