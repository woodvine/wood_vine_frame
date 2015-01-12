package com.wood.service;

import java.io.File;
import java.util.Map;

public interface QcodeService  extends BaseService{
	// 获取二维码
	public File showQCode(Map<String,String>parameter);

}
