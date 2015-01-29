package com.wood.controller;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.WebXml.WeatherWS;
import cn.com.WebXml.WeatherWSLocator;
import cn.com.WebXml.WeatherWSSoap;

import com.wood.pojo.ActionResponse;

/**
 * 
 * @title       :WeatherAction
 * @description :RMI调用webservice获取天气相关信息
 * @update      :2015-1-29 上午9:55:14
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2015-1-29
 */
@Controller
public class WeatherAction {
	/**
	 * 天气预报webService对象
	 */
	private WeatherWS weatherWS = new WeatherWSLocator();
	
	private Logger logger = Logger.getLogger(WeatherAction.class.getName());
	
	@RequestMapping(value="/weatherGet")
	public ModelAndView weatherGet(){
		ModelAndView view = new ModelAndView("weather");
		return view;
	}
	
	/**
	 * 获取省份列表
	 * @return
	 */
	@RequestMapping(value="/getProvinces")
	@ResponseBody
	public ActionResponse getProvinces(){
		ActionResponse response = new ActionResponse();
		try {
			WeatherWSSoap soap = weatherWS.getWeatherWSSoap();
			String[] provinces = soap.getRegionProvince();
			if(provinces!=null){
				int length = provinces.length;
				if(length>0){
					List<ReginInfo> list = new ArrayList<ReginInfo>(length);
					//返回城市信息是成对出现在数组中的："名称，区域码"
					for(int i = 0;i<length;i++){
						String[] province = provinces[i].split(",");
						list.add(new ReginInfo(province[0],province[1]));
					}
					response.setResult(list);
					response.setStatus(true);
				}else{
					response.setDescription("获取省份信息为空,请稍后重试!");
				}
			}
		} catch (ServiceException execption) {
			logger.error("WebService服务异常，获取省份信息失败！", execption);
			response.setDescription("WebService服务异常，获取省份信息失败！");
		} catch (RemoteException execption) {
			logger.error("RemoteException异常，获取省份信息失败！", execption);
			response.setDescription("RemoteException异常，获取省份信息失败！");
		}
		return response;
	}
	
	/**
	 * 获取对应省的城市列表
	 * @return
	 */
	@RequestMapping(value="/getCitys")
	@ResponseBody
	public ActionResponse getCitys(String theRegionCode){
		ActionResponse response = new ActionResponse();
		try {
			WeatherWSSoap soap = weatherWS.getWeatherWSSoap();
			String[] citys = soap.getSupportCityString(theRegionCode);
			if(citys!=null&&citys.length>0){
				int length = citys.length;
				List<ReginInfo> list = new ArrayList<ReginInfo>(citys.length);
				//返回城市信息是成对出现在数组中的："名称，区域码"
				for(int i = 0;i<length;i++){
					String[] city = citys[i].split(",");
					list.add(new ReginInfo(city[0],city[1]));
				}
				response.setResult(list);
				response.setStatus(true);
			}
		} catch (ServiceException execption) {
			logger.error("WebService服务异常，获取城市信息失败！", execption);
			response.setDescription("WebService服务异常，获取城市列表信息失败！");
		} catch (RemoteException execption) {
			logger.error("RemoteException异常，获取城市信息失败！", execption);
			response.setDescription("RemoteException异常，获取城市列表信息失败！");
		}
		return response;
	}

	/**
	 * 获取指定城市的天气信息
	 * @description 还有调用限制的（免费用户调用此时貌似很少啊）
	 * @return
	 */
	@RequestMapping(value="/queryWeather")
	@ResponseBody
	public ActionResponse queryWeather(String theRegionCode){
		ActionResponse response = new ActionResponse();
		try {
			WeatherWSSoap soap = weatherWS.getWeatherWSSoap();
			String[] weatherResult = soap.getWeather(theRegionCode, null);
			int totalLength = weatherResult.length;
			if(weatherResult!=null&&totalLength>=12){
				//分解近5日天气信息
				int length = (totalLength-7)/5;
				List<WeatherInfo> list = new ArrayList<WeatherInfo>(length);
				for(int i = 7;i<totalLength;i+=5){
					String info = weatherResult[i];
					String temp = weatherResult[i+1];
					String wind = weatherResult[i+2];
					String image = weatherResult[i+3];
					
					//分解日期和天气
					int index = info.indexOf(" ");
					String date = null;
					String weather = null;
					if(index>0){
						date= info.substring(0, index);
						weather= info.substring(index);
					}else{
						date = info;
						weather = info;
					}
					
					//添加到数据返回列表
					list.add(new WeatherInfo(date,temp,wind,weather,image));
				}
				response.setExt(list);
				response.setStatus(true);
				
				String[] result = new String[7];
				System.arraycopy(weatherResult, 0, result, 0, 7);
				response.setResult(Arrays.asList(result));
			}else{
				response.setDescription("数据信息获取不全!");
				logger.error(totalLength>0?weatherResult[0]:"数据信息获取不全!");
			}
		} catch (ServiceException execption) {
			logger.error("WebService服务异常，天气查询失败！", execption);
			response.setDescription("WebService服务异常，天气查询失败！");
		} catch (RemoteException execption) {
			logger.error("RemoteException异常，天气查询失败！", execption);
			response.setDescription("RemoteException异常，天气查询失败！");
		}
		return response;
	}
	
	/**
	 * @title       :ReginInfo
	 * @description :天气预报中使用到的区域信息：名称+区域码
	 * @update      :2015-1-29 上午10:54:11
	 * @author      :wang_ll
	 * @version     :1.0.0
	 * @since       :2015-1-29
	 */
	public class ReginInfo{
		private String name;
		private String code;
		
		public ReginInfo(String name,String code){
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}
	
	/**
	 * @title       :WeatherInfo
	 * @description :天气预报提供的天气信息
	 * @update      :2015-1-29 下午1:59:55
	 * @author      :wang_ll
	 * @version     :1.0.0
	 * @since       :2015-1-29
	 */
	public class WeatherInfo{
		private String date;
		private String temperature;
		private String wind;
		private String weather;
		private String image;
		public WeatherInfo(String date, String temperature, String wind,
				String weather,String image) {
			super();
			this.date = date;
			this.temperature = temperature;
			this.wind = wind;
			this.weather = weather;
			this.image = image;
		}
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getTemperature() {
			return temperature;
		}
		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}
		public String getWind() {
			return wind;
		}
		public void setWind(String wind) {
			this.wind = wind;
		}
		public String getWeather() {
			return weather;
		}
		public void setWeather(String weather) {
			this.weather = weather;
		}
		public String getImage() {
			return image;
		}
		public void setImage(String image) {
			this.image = image;
		}
	}
}
