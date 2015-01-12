package com.wood.util;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @title :JsonParseUtil
 * @description :Json文本解析工具类
 * @update :2014-2-24 下午01:55:41
 * @author :wang_ll
 * @version :1.0.0
 * @since :2014-2-24
 */
public class JsonParseUtil {
	/**
	 * 将Json文本解析成一个POJO对象
	 * 
	 * @param json
	 *            待解析的Json文本，Json信息标准格式，形如{,,,}
	 * @param className
	 *            POJO类名全称
	 * @return 一个POJO对象
	 * @throws ClassNotFoundException
	 */
	public static Object parseJson(String json, String className)
			throws ClassNotFoundException {
		if (json == null || className == null) {
			throw new IllegalArgumentException(
					"json text or className is empty");
		}

		// 直接将JSONObject转换成POJO对象
		JSONObject jsonObject = JSONObject.fromObject(json);

		return JSONObject.toBean(jsonObject, Class.forName(className));
	}

	/**
	 * 将Json文本解析成POJO对象列表
	 * 
	 * @param json
	 *            待解析的Json文本，一维JSON数组形如[{},{}]格式
	 * @param className
	 *            POJO类名全称
	 * @return List,其元素为POJO对象
	 * @throws ClassNotFoundException
	 */
	public static List<Object> parseJsonArray1(String json, String className)
			throws ClassNotFoundException {
		if (json == null || className == null) {
			throw new IllegalArgumentException(
					"json text or className is empty");
		}

		List<Object> list = new ArrayList<Object>();
		JSONArray array = JSONArray.fromObject(json);
		for (int i = 0, j = array.size(); i < j; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			list.add(JSONObject.toBean(jsonObject, Class.forName(className)));
		}
		return list;
	}

	/**
	 * 将Json文本解析成POJO对象列表
	 * 
	 * @param json
	 *            待解析的Json文本，一维JSON数组形如[{},{}]格式
	 * @param className
	 *            POJO类名全称
	 * @param beginIndex
	 *            索引开始位置
	 * @param endIndex
	 *            索引结束位置
	 * @return List,其元素为POJO对象
	 * @throws ClassNotFoundException
	 */
	public static List<Object> parseJsonArray1(String json, String className,
			int beginIndex, int count) throws ClassNotFoundException {
		if (json == null || className == null) {
			throw new IllegalArgumentException(
					"json text or className is empty");
		}

		List<Object> list = new ArrayList<Object>();
		JSONArray array = JSONArray.fromObject(json);
		if (beginIndex < array.size()) {
			int endIndex = (beginIndex + count) > array.size() ? array.size() : (beginIndex + count) ; 
			if (count == -1) {
				endIndex = array.size();
			}
			for (int i = beginIndex, j = endIndex; i < j; i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				list.add(JSONObject.toBean(jsonObject, Class.forName(className)));
			}
		}
		return list;
	}

	/**
	 * 将Json文本解析成POJO对象列表
	 * 
	 * @param json
	 *            待解析的Json文本，二维JSON数组形如[[{},{}],[{},{},{}]]格式
	 * @param className
	 *            POJO类名全称
	 * @return List,其元素为POJO对象
	 * @throws ClassNotFoundException
	 */
	public static List<Object> parseJsonArray2(String json, String className)
			throws ClassNotFoundException {
		if (json == null || className == null) {
			throw new IllegalArgumentException(
					"json text or className is empty");
		}

		List<Object> list = new ArrayList<Object>();
		JSONArray array = JSONArray.fromObject(json);

		for (int i = 0; i < array.size(); i++) {
			// array的元素是JSONArray对象，再对其二次解析
			JSONArray element = array.getJSONArray(i);
			for (int j = 0; j < element.size(); j++) {
				// element的元素是JSONObject对象，对其解析得到POJO对象
				JSONObject jsonObject = element.getJSONObject(j);
				list.add(JSONObject.toBean(jsonObject, Class.forName(className)));
			}
		}
		return list;
	}

	/**
	 * 将Json文本解析成POJO对象列表
	 * 
	 * @param json
	 *            待解析的Json文本，二维JSON数组形如[[{},{}],[{},{},{}]]格式
	 * @param className
	 *            POJO类名全称
	 * @return List,其元素为POJO对象
	 * @throws ClassNotFoundException
	 */
	public static List<Object> parseJsonArray2(String json, String className,
			int beginIndex, int count) throws ClassNotFoundException {
		if (json == null || className == null) {
			throw new IllegalArgumentException(
					"json text or className is empty");
		}

		List<Object> list = new ArrayList<Object>();
		JSONArray array = JSONArray.fromObject(json);

		if (beginIndex < array.size()) {
			int endIndex = (beginIndex + count) > array.size() ? array.size() : (beginIndex + count) ; 
			if (count == -1) {
				endIndex = array.size();
			}
			for (int i = beginIndex, j = endIndex; i < j; i++) {
				// array的元素是JSONArray对象，再对其二次解析
				JSONArray element = array.getJSONArray(i);
				for (int k = 0; k < element.size(); k++) {
					// element的元素是JSONObject对象，对其解析得到POJO对象
					JSONObject jsonObject = element.getJSONObject(k);
					list.add(JSONObject.toBean(jsonObject,
							Class.forName(className)));
				}
			}
		}
		return list;
	}

	/**
	 * 过滤掉JSON中的注释元素
	 * 
	 * @param jsonInfo
	 * @return
	 */
	public static String filterCommentInJOSN(String jsonInfo) {
		if (StringUtil.isEmpty(jsonInfo)) {
			return jsonInfo;
		}

		int commentIndex = jsonInfo.indexOf("//");
		// 没有注释信息，返回源文本
		if (commentIndex == -1) {
			return jsonInfo;
		}

		while (commentIndex != -1) {
			int commaIndex = jsonInfo.indexOf("},", commentIndex);
			// 去掉JSON中的注释
			if (commaIndex != -1) {
				String comment = jsonInfo.substring(commentIndex,
						commaIndex + 2);
				jsonInfo = jsonInfo.replace(comment, "");
			}
			// 过滤下一条注释
			commentIndex = jsonInfo.indexOf("//", commentIndex);
		}

		return jsonInfo;
	}
}
