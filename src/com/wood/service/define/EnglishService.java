package com.wood.service.define;

import com.wood.model.TbEnglish;
import com.wood.pojo.ActionResponse;

public interface EnglishService {
	public ActionResponse addEnglish(TbEnglish English);
	public ActionResponse deleteEnglish(int id);
	public ActionResponse queryEnglishByPage(TbEnglish English);
	public ActionResponse getEnglish(Integer id);
	public ActionResponse getGoodEnglish();
}
