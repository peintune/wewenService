package com.wewen.sdk;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.apicloud.sdk.utils.HttpUtils;

/**
 * 统计云api
 * @author wangjinzhen
 * @time 25/05/2015
 * @version 0.0.1
 */
public class Analytics {

	//headers参数
	private Map<String,String> headers = new HashMap<String,String>();
	
	private String domain = "https://r.apicloud.com";
	
	//params参数
	private Map<String,String> params = new HashMap<String,String>();
	

	/**
	 * @param appId
	 * @param appKey
	 * @param domain 为空或者null为默认https
	 */
	public Analytics(String appId,String appKey,String domain){
		
		if(null!=domain&&!"".equals(domain)){
			this.domain = domain;
		}
		headers.put("X-APICloud-AppId", appId);
		headers.put("X-APICloud-AppKey", HttpUtils.encrypt(appId,appKey,"SHA-1"));
	}
	
	@SuppressWarnings("unused")
	private Analytics(){};
	
	/** 应用统计信息获取接口------begin------ **/
	
	/**
	 * 获取用户指定应用ID及时间范围内的相关应用统计数据信息
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JSONObject getAppStatisticDataById(String startDate,String endDate){
		
		if(!timeRegExp(startDate)||!timeRegExp(endDate)){
			return JSONObject.parseObject("{status:0,msg:\"日期不符合格式!\"}");
		}

		//设置参数
		params.clear();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		String url = domain+"/analytics/getAppStatisticDataById";
		
		return HttpUtils.doPost(url, headers, params, "");
	}
	
	/** 应用统计信息获取接口------end------ **/
	
	/** 应用各版本统计信息获取接口------begin------ **/
	
	/**
	 * 获取用户指定应用ID及时间范围内相关应用各版本的统计数据信息
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JSONObject getVersionsStatisticDataById(String startDate,String endDate){
		
		if(!timeRegExp(startDate)||!timeRegExp(endDate)){
			return JSONObject.parseObject("{status:0,msg:\"日期不符合格式!\"}");
		}
		
		//设置参数
		params.clear();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		String url = domain+"/analytics/getVersionsStatisticDataById";
		
		return HttpUtils.doPost(url, headers, params, "");
	}
	
	/** 应用各版本统计信息获取接口------end------ **/
	
	/** 应用地理分布统计信息获取接口------begin------ **/
	
	/**
	 * 获取用户指定应用ID及时间范围内的应用下各版本地理分布统计数据信息
	 * @param startDate
	 * @param endDate
	 * @param versionCode
	 * @return
	 */
	public JSONObject getGeoStatisticDataById(String startDate,String endDate,String versionCode){
		
		if(!timeRegExp(startDate)||!timeRegExp(endDate)){
			return JSONObject.parseObject("{status:0,msg:\"日期不符合格式!\"}");
		}
		
		//设置参数
		params.clear();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("versionCode", versionCode);
		
		String url = domain+"/analytics/getGeoStatisticDataById";
		
		return HttpUtils.doPost(url, headers, params, "");
	}
	
	/** 应用地理分布统计信息获取接口------end------ **/
	
	/** 应用设备分布统计信息获取接口------begin------ **/
	
	/**
	 * 获取用户指定应用ID及时间范围内的应用下各版本设备信息分布统计数据信息
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JSONObject getDeviceStatisticDataById(String startDate,String endDate){
		
		if(!timeRegExp(startDate)||!timeRegExp(endDate)){
			return JSONObject.parseObject("{status:0,msg:\"日期不符合格式!\"}");
		}
		
		//设置参数
		params.clear();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		String url = domain+"/analytics/getDeviceStatisticDataById";
		
		return HttpUtils.doPost(url, headers, params, "");
	}
	
	/** 应用设备分布统计信息获取接口------end------ **/
	
	/** 应用异常错误统计信息获取接口------begin------ **/
	
	/**
	 * 获取用户指定应用ID及时间范围内的应用下各版本异常错误统计数据信息
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public JSONObject getExceptionsStatisticDataById(String startDate,String endDate){
		
		if(!timeRegExp(startDate)||!timeRegExp(endDate)){
			return JSONObject.parseObject("{status:0,msg:\"日期不符合格式!\"}");
		}
		
		//设置参数
		params.clear();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		String url = domain+"/analytics/getExceptionsStatisticDataById";
		
		return HttpUtils.doPost(url, headers, params, "");
	}
	
	/** 应用异常错误统计信息获取接口------end------ **/
	
	/** 应用异常错误详细信息获取接口------begin------ **/
	
	/**
	 * 根据应用异常错误摘要获取异常错误详细信息
	 * @param title 错误摘要信息
	 * @return
	 */
	public JSONObject getExceptionsDetailByTitle(String title){
		
		//设置参数
		params.clear();
		params.put("title", title);
		
		String url = domain+"/analytics/getExceptionsDetailByTitle";
		
		return HttpUtils.doPost(url, headers, params, "");
	}
	
	/** 应用异常错误详细信息获取接口------end------ **/
	
	/**
	 * 验证时间是否符合yyyy-mm-dd形式
	 * @param time
	 * @return
	 */
	private boolean timeRegExp(String time){
		String reg = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(time);
		return m.matches();
	}
}
