package com.wood.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.wood.model.TbCsdnBlog;
import com.wood.model.TbScrawlData;
import com.wood.util.ScrawlStatus;
import com.wood.util.StringUtil;

/**
 * 
 * @title       :ScrawlDataDao
 * @description :
 * @update      :2015-2-2 下午2:42:45
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-2-2
 */
@Repository
public class ScrawlDataDao {
	private Logger logger = Logger.getLogger(ScrawlDataDao.class.getName());
	
	private ReentrantLock lock = new ReentrantLock();
	
	/**
	 * Resource这个注解是J2EE的，默认是按名称进行装配的
	 * 以name属性指定的名称为主，没有则按注解所在的字段名称来装配
	 */
	@Resource(name = "sqlMapClient")
	private SqlMapClient sqlMapClient;
	
	public boolean isUrlExist(String url){
		boolean flag = false;
		
		int hashCode =  url.hashCode();
		try {
			//根据散列码校验URL是否存在
			TbScrawlData data = (TbScrawlData)sqlMapClient.queryForObject("scrawl.queryByHash", hashCode);
			if(data==null){
				return false;
			}
			
			//散列值相等的记录，再比较内容判重
			return url.equals(data.getUrl());
		} catch (SQLException execption) {
			logger.error("ScrawlDataDao check url repeate by hash error.", execption);
		}
		return flag;
	}
	
	/**
	 * 网页爬虫时可能需要判重：但是目前CSDN博文抓取时，应该不存在重复记录
	 * @param data
	 * @return
	 */
	public boolean insertBlogData(TbCsdnBlog data){
		if(data==null){
			return false;
		}
		
		try{
			//插入
			sqlMapClient.insert("scrawl.insertCsdnBlog", data);
		}catch(Exception e){
			logger.error("ScrawlDataDao insertCsdnBlog error.", e);
		}
		
		return true;
	}
	
	/**
	 * 网页爬虫时可能需要判重：但是目前CSDN博文抓取时，应该不存在重复记录
	 * @param data
	 * @return
	 */
	public boolean insertScrawlData(TbScrawlData data){
		if(data==null||StringUtil.isEmpty(data.getUrl())){
			return false;
		}
		
		try{
			lock.lock();
			String url = data.getUrl();
			if(isUrlExist(url)){
				logger.info(url+" already exists...");
				return false;
			}
			
			//插入
			sqlMapClient.insert("scrawl.insertScrawlData", data);
		}catch(Exception e){
			logger.error("ScrawlDataDao insert error.", e);
		}finally{
			lock.unlock();
		}
		
		return true;
	}
	
	/**
	 * 一条URL网址内容爬虫执行完成可能更新记录
	 * （更新内容和状态）
	 * @param data
	 */
	public void updateScrawlData(TbScrawlData data){
		if(data==null){
			return;
		}
		
		//更新记录的信息：状态和内容
		try {
			sqlMapClient.update("scrawl.updateScrawlData", data);
		} catch (SQLException execption) {
			logger.error("ScrawlDataDao updateScrawlData error.", execption);
		}
	}
	
	/**
	 * 批量插入新解析的URL
	 * @param datas
	 */
	public void insertScrawlBatched(List<TbScrawlData>datas){
		if(datas==null||datas.size()==0){
			return;
		}
		
		//更新记录的信息：状态和内容
		try {
			sqlMapClient.insert("scrawl.insertScrawlBatched", datas);
		} catch (SQLException execption) {
			logger.error("ScrawlDataDao insertScrawlBatched error.", execption);
		}
	}
	
	/**
	 * 批量更新记录的状态和内容：
	 *   成功完成网页下载后批量更新记录
	 * @param data
	 */
	public void updateScrawlBatched(List<TbScrawlData>datas){
		if(datas==null||datas.size()==0){
			return;
		}
		
		//更新记录的信息：状态和内容
		try {
			sqlMapClient.startBatch();
			for(TbScrawlData data:datas){
				sqlMapClient.update("scrawl.updateScrawlData", data);
			}
			sqlMapClient.executeBatch();
		} catch (SQLException execption) {
			logger.error("ScrawlDataDao updateScrawlBatched error.", execption);
		}
	}
	
	/**
	 * 批量更新记录的状态:设置中间状态2
	 * @param data
	 */
	public void updateMediumBatched(List<Integer>datas){
		if(datas==null||datas.size()==0){
			return;
		}
		
		//更新记录的信息：状态和内容
		System.out.println(Arrays.toString(datas.toArray()));
		/*try {
			sqlMapClient.startTransaction();
			sqlMapClient.startBatch();
			for(Integer data:datas){
				sqlMapClient.update("scrawl.updateMediumStatus", data);
			}
			sqlMapClient.executeBatch();
			sqlMapClient.commitTransaction();
		} catch (SQLException execption) {
			logger.error("ScrawlDataDao updateMediumBatched error.", execption);
		}
		*/
		try {
			sqlMapClient.update("scrawl.updateMediumBatched", datas);
		} catch (SQLException execption) {
			logger.error("ScrawlDataDao updateMediumBatched error.", execption);
		}
	}
	
	/**
	 * 查询N条待抓取的记录:获取操作和和更新中间状态操作在一个锁保护下完成
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TbScrawlData> queryNewlyData(int count){
		List<TbScrawlData> list = null;
		//更新记录的信息：状态和内容
		try {
			lock.lock();
			list = (List<TbScrawlData>)sqlMapClient.queryForList("scrawl.queryNewlyData", count);
			if(list!=null&&list.size()>0){
				List<Integer> ids = new ArrayList<Integer>(list.size());
				for(TbScrawlData data:list){
					data.setStatus(ScrawlStatus.Medium.getValue());
					ids.add(data.getId());
				}
				
				//设置成中间状态
				updateMediumBatched(ids);
			}
		} catch (SQLException execption) {
			logger.error("ScrawlDataDao queryNewlyData error.", execption);
		}finally{
			lock.unlock();
		}
		return list;
	}
	
	/**
	 * 查询N条超时的记录:重新请求
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TbScrawlData> queryTimeoutData(int count){
		List<TbScrawlData> list = null;
		//更新记录的信息：状态和内容
		try {
			lock.lock();
			list = (List<TbScrawlData>)sqlMapClient.queryForList("scrawl.queryTimeoutData", count);
			if(list!=null&&list.size()>0){
				List<Integer> ids = new ArrayList<Integer>(list.size());
				for(TbScrawlData data:list){
					data.setStatus(ScrawlStatus.Medium.getValue());
					ids.add(data.getId());
				}
				
				//设置成中间状态
				updateMediumBatched(ids);
			}
		} catch (SQLException execption) {
			logger.error("ScrawlDataDao queryTimeoutData error.", execption);
		}finally{
			lock.unlock();
		}
		return list;
	}
	
	public String queryForxml(){
		String r = null;
		try {
			r = (String)sqlMapClient.queryForList("scrawl.xmlSearch", 10).get(0);
		} catch (SQLException execption) {
			execption.printStackTrace();
		}
		return r;
	}
}
