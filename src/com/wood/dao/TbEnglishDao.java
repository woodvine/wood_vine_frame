package com.wood.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wood.model.TbEnglish;

@Repository
public class TbEnglishDao {
	private Logger logger = Logger.getLogger(TbEnglishDao.class.getName());
	
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	public boolean deleteEnglish(int id) {
		try {
			this.sqlMapClient.insert("english.deleteEnglish",id);
			return true;
		} catch (Exception e) {
			logger.error("TbEnglishDao deleteEnglish error", e);
			return false;
		}
	}
	
	public boolean addEnglish(TbEnglish English) {
		if(English==null){
			return false;
		}
		
		try {
			this.sqlMapClient.insert("english.addEnglish",English);
			return true;
		} catch (Exception e) {
			logger.error("TbEnglishDao addEnglish error", e);
			return false;
		}
	}

	public TbEnglish queryById(int id) {
		TbEnglish record = null;
		try {
			record =(TbEnglish)this.sqlMapClient.queryForObject("english.queryEnglishById",id);
		} catch (Exception e) {
			logger.error("TbEnglishDao queryById error", e);
		}
		return record;
	}
	
	@SuppressWarnings("unchecked")
	public List<TbEnglish> queryByPage(TbEnglish English) {
		List<TbEnglish> record = null;
		try {
			record =this.sqlMapClient.queryForList("english.queryEnglishByPage",English);
		} catch (Exception e) {
			logger.error("TbEnglishDao queryById error", e);
		}
		return record;
	}

	public int queryEnglishCount(TbEnglish English) {
		Integer count = 0;
		try {
			count =(Integer) this.sqlMapClient.queryForObject("english.queryEnglishCount",English);
		} catch (Exception e) {
			logger.error("TbEnglishDao queryEnglishCount error", e);
		}
		return count;
	}

	public boolean updateEnglish(TbEnglish English) {
		if(English==null){
			return false;
		}
		
		try {
			this.sqlMapClient.update("english.updateEnglish",English);
			return true;
		} catch (Exception e) {
			logger.error("TbEnglishDao update error", e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, String> queryDailyGood(Integer id) {
		try {
			return (HashMap<String, String>) this.sqlMapClient.queryForObject("english.queryDailyGood",id);
		} catch (Exception e) {
			logger.error("TbEnglishDao queryDailyGood error", e);
		}
		return null;
	}

	public int queryDailyGoodCount() {
		Integer count = 0;
		try {
			count =(Integer) this.sqlMapClient.queryForObject("english.queryDailyGoodCount");
		} catch (Exception e) {
			logger.error("TbEnglishDao queryEnglishCount error", e);
		}
		return count;
	}
}
