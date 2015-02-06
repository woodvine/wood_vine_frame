package com.wood.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wood.model.TbDiary;

/**
 * 
 * @title       :TbDiaryDao
 * @description :
 * @update      :2015-1-16 下午1:48:17
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-16
 */
@Repository
public class TbDiaryDao {
	private Logger logger = Logger.getLogger(TbDiaryDao.class.getName());
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	public boolean deleteDiary(int id) {
		try {
			this.sqlMapClient.insert("remind.deleteDiary",id);
			return true;
		} catch (Exception e) {
			logger.error("TbDiaryDao deleteDiary error", e);
			return false;
		}
	}
	
	public boolean addDiary(TbDiary diary) {
		if(diary==null){
			return false;
		}
		
		try {
			this.sqlMapClient.insert("remind.addDiary",diary);
			return true;
		} catch (Exception e) {
			logger.error("TbDiaryDao addDiary error", e);
			return false;
		}
	}

	public TbDiary queryById(int id) {
		TbDiary record = null;
		try {
			record =(TbDiary)this.sqlMapClient.queryForObject("remind.queryDiaryById",id);
		} catch (Exception e) {
			logger.error("TbDiaryDao queryById error", e);
		}
		return record;
	}
	
	@SuppressWarnings("unchecked")
	public List<TbDiary> queryByPage(TbDiary diary) {
		List<TbDiary> record = null;
		try {
			record =this.sqlMapClient.queryForList("remind.queryDiaryByPage",diary);
		} catch (Exception e) {
			logger.error("TbDiaryDao queryById error", e);
		}
		return record;
	}

	public int queryDiaryCount(TbDiary diary) {
		Integer count = 0;
		try {
			count =(Integer) this.sqlMapClient.queryForObject("remind.queryDiaryCount",diary);
		} catch (Exception e) {
			logger.error("TbDiaryDao queryDiaryCount error", e);
		}
		return count;
	}

	public boolean updateDiary(TbDiary diary) {
		if(diary==null){
			return false;
		}
		
		try {
			this.sqlMapClient.update("remind.updateDiary",diary);
			return true;
		} catch (Exception e) {
			logger.error("TbDiaryDao update error", e);
			return false;
		}
	}
}
