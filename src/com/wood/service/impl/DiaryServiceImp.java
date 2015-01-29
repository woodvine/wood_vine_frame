package com.wood.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wood.dao.TbDiaryDao;
import com.wood.model.TbDiary;
import com.wood.pojo.ActionResponse;
import com.wood.service.define.DiaryService;

@Service
public class DiaryServiceImp implements DiaryService{
	@Autowired
	private TbDiaryDao diaryDao;
	
	@Override
	public ActionResponse addDiary(TbDiary diary) {
		ActionResponse response = new ActionResponse();
		if(diary.getId()!=null){
			boolean flag = diaryDao.updateDiary(diary);
			response.setStatus(flag);
		}else{
			boolean flag = diaryDao.addDiary(diary);
			response.setStatus(flag);
		}
		return response;
	}

	@Override
	public ActionResponse deleteDiary(int id) {
		ActionResponse response = new ActionResponse();
		boolean flag = diaryDao.deleteDiary(id);
		response.setStatus(flag);
		return response;
	}

	@Override
	public ActionResponse queryDiaryByPage(TbDiary diary) {
		
		ActionResponse response = new ActionResponse();
		List<TbDiary> result = diaryDao.queryByPage(diary);
		response.setResult(result);
		
		//total count
		int total = diaryDao.queryDiaryCount(diary);
		diary.setTotal(total);
		response.setExt(diary);
		response.setStatus(true);
		return response;
	}

	@Override
	public ActionResponse getDiary(Integer id) {
		ActionResponse response = new ActionResponse();
		TbDiary diary = diaryDao.queryById(id);
		response.setStatus(true);
		response.setResult(diary);
		return response;
	}
}
