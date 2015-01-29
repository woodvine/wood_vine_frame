package com.wood.service.define;

import com.wood.model.TbDiary;
import com.wood.pojo.ActionResponse;

public interface DiaryService {
	public ActionResponse addDiary(TbDiary diary);
	public ActionResponse deleteDiary(int id);
	public ActionResponse queryDiaryByPage(TbDiary diary);
	public ActionResponse getDiary(Integer id);
}
