package com.wood.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wood.dao.TbEnglishDao;
import com.wood.model.TbEnglish;
import com.wood.pojo.ActionResponse;
import com.wood.service.define.EnglishService;

@Service
public class EnglishServiceImpl implements EnglishService {

	@Autowired
	private TbEnglishDao EnglishDao;
	
	@Override
	public ActionResponse addEnglish(TbEnglish English) {
		ActionResponse response = new ActionResponse();
		if(English.getId()!=null){
			boolean flag = EnglishDao.updateEnglish(English);
			response.setStatus(flag);
		}else{
			boolean flag = EnglishDao.addEnglish(English);
			response.setStatus(flag);
		}
		return response;
	}

	@Override
	public ActionResponse deleteEnglish(int id) {
		ActionResponse response = new ActionResponse();
		boolean flag = EnglishDao.deleteEnglish(id);
		response.setStatus(flag);
		return response;
	}

	@Override
	public ActionResponse queryEnglishByPage(TbEnglish English) {
		
		ActionResponse response = new ActionResponse();
		List<TbEnglish> result = EnglishDao.queryByPage(English);
		response.setResult(result);
		
		//total count
		int total = EnglishDao.queryEnglishCount(English);
		English.setTotal(total);
		response.setExt(English);
		response.setStatus(true);
		return response;
	}

	@Override
	public ActionResponse getEnglish(Integer id) {
		ActionResponse response = new ActionResponse();
		TbEnglish English = EnglishDao.queryById(id);
		response.setStatus(true);
		response.setResult(English);
		return response;
	}

	@Override
	public ActionResponse getGoodEnglish() {
		ActionResponse response = new ActionResponse();
		HashMap<String,String> good = new HashMap<String,String>();
		int total = EnglishDao.queryDailyGoodCount();
		if(total==0){
			good.put("content", "I am a slow walker,but I never walk backwards.");
			good.put("chinese", "我走得很慢，但是我从来不会后退。");
			good.put("author", "亚伯拉罕.林肯美国");
			response.setResult(good);
			response.setStatus(true);
			return response;
		}
		
		Integer id = new Random().nextInt(total);
		good = EnglishDao.queryDailyGood(id);
		if(good==null){
			good = new HashMap<String,String>();
			good.put("content", "I am a slow walker,but I never walk backwards.");
			good.put("chinese", "我走得很慢，但是我从来不会后退。");
			good.put("author", "亚伯拉罕.林肯美国");
			response.setResult(good);
			response.setStatus(true);
			return response;
		}
		
		response.setStatus(true);
		response.setResult(good);
		return response;
	}

}
