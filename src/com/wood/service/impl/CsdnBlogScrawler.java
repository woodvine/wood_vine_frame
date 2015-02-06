package com.wood.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wood.dao.ScrawlDataDao;
import com.wood.model.TbCsdnBlog;
import com.wood.model.TbScrawlData;
import com.wood.pojo.ActionResponse;
import com.wood.service.define.IBlogScrawler;
import com.wood.util.ScrawlStatus;
import com.wood.util.ScrawlUtil;
import com.wood.util.SystemConstant;

/**
 * 
 * @title       :CsdnBlogScrawler
 * @description :CSDN博文抽取模块
 * @update      :2015-2-2 下午3:17:10
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-2-2
 */
@Service
public class CsdnBlogScrawler implements IBlogScrawler{
	@Autowired
	private ScrawlDataDao scrawlDao;
	
	private Logger logger = Logger.getLogger(CsdnBlogScrawler.class.getName());

	/**
	 * 爬取CSDN某个博主的所有的博文
	 */
	public ActionResponse xmlSearch() {
		ActionResponse response = new ActionResponse();
		response.setResult(scrawlDao.queryForxml());
		response.setStatus(true);
		response.setDescription("all scrawl ok");
		return response;
	}
	
	/**
	 * 爬取CSDN某个博主的所有的博文
	 */
	public ActionResponse scrawl(String blogOwner) {
		ActionResponse response = new ActionResponse();
		response.setStatus(true);
		logger.info("Scrawl csdn bloc start at :"+new Date());
		init(blogOwner);
		doScrawl(blogOwner,response);
		logger.info("Scrawl csdn bloc end at :"+new Date());
		if(response.getDescription()==null){
			response.setDescription("all scrawl ok");
		}
		return response;
	}

	//初始入库一条种子地址
	private void init(String blogOwner) {
		String url = SystemConstant.CsdnHome+blogOwner;
		TbScrawlData data = new TbScrawlData(url,ScrawlStatus.Newly.getValue(),
				SystemConstant.TypeHome);
		scrawlDao.insertScrawlData(data);
	}

	//每次间隔5秒启动一个采集任务
	private void doScrawl(String blogOwner,ActionResponse response) {
		ExecutorService exec = Executors.newFixedThreadPool(SystemConstant.MaxThreadCount);
		for(int i = 0;i<SystemConstant.MaxThreadCount;i++){
			exec.execute(new ScrawlTask(scrawlDao,response));
			try {
				Thread.sleep(5000);
			} catch (InterruptedException execption) {
				logger.error(execption);
			}
		}
		
		exec.shutdown();
		
		//监听任务是否执行完成
		while(true){
			if(exec.isTerminated()){
				break;
			}
			
			//休眠5秒
			try {
				Thread.sleep(5000);
			} catch (InterruptedException execption) {
				logger.error(execption);
			}
		}
	}

	/**
	 * @title       :ScrawlTask
	 * @description :网页抓取任务操作流程
	 * 				1)获取网页内容
	 * 				2)解析网页内容，重新提起新的待采集链接
	 * @update      :2015-2-2 下午4:49:34
	 * @author      :wang_ll
	 * @version     :1.0.0
	 * @since       :2015-2-2
	 */
	public class ScrawlTask implements Runnable{
		private ScrawlDataDao scrawlDao;
		private ActionResponse response;
		
		public ScrawlTask(ScrawlDataDao scrawlDao,ActionResponse response){
			this.scrawlDao = scrawlDao;
			this.response = response;
		}

		@Override
		public void run() {
			List<TbScrawlData> list = scrawlDao.queryNewlyData(SystemConstant.BatchedCount);
			//查询N条待采集的记录
			
			while(list!=null&&list.size()>0){
				//遍历URL列表，抓取网页内容
				for(TbScrawlData data:list){
					//网页抓取
					ScrawlUtil.scrawl(data);
					
					//如果失败403则给出提示
					if(data.getStatus()==ScrawlStatus.Failure.getValue()
							&&data.getType()==SystemConstant.TypeHome){
						response.setStatus(false);
						response.setDescription("您输入的博客用户不存在，请核对输入!");
						return;
					}
					
					//解析产生新的待抓取链接
					List<TbScrawlData> newlyDatas = new ArrayList<TbScrawlData>();
					
					//调用解析：解析结果可能有两种
					TbCsdnBlog blog = ScrawlUtil.parse(data,newlyDatas);
					scrawlDao.insertScrawlBatched(newlyDatas);
					//插入博客内容，如果解析到了博客内容
					scrawlDao.insertBlogData(blog);
				}
				
				//批量更新
				scrawlDao.updateScrawlBatched(list);
				
				//重新下一轮查询
				list = scrawlDao.queryNewlyData(SystemConstant.BatchedCount);
			}
			
			//对超时记录重新请求
			System.out.println("重新查询超时的记录...");
			list = scrawlDao.queryTimeoutData(SystemConstant.BatchedCount);
			while(list!=null&&list.size()>0){
				//遍历URL列表，抓取网页内容
				for(TbScrawlData data:list){
					//网页抓取
					ScrawlUtil.scrawl(data);
					
					//解析产生新的待抓取链接
					List<TbScrawlData> newlyDatas = new ArrayList<TbScrawlData>();
					
					//调用解析：解析结果可能有两种
					TbCsdnBlog blog = ScrawlUtil.parse(data,newlyDatas);
					scrawlDao.insertScrawlBatched(newlyDatas);
					//插入博客内容，如果解析到了博客内容
					scrawlDao.insertBlogData(blog);
				}
				
				//批量更新
				scrawlDao.updateScrawlBatched(list);
				
				//重新下一轮查询
				list = scrawlDao.queryTimeoutData(SystemConstant.BatchedCount);
			}
		}
	}
}
