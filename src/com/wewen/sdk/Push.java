package com.wewen.sdk;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.apicloud.sdk.utils.HttpUtils;

/**
 * 推送云API
 * @author wangjinzhen
 * @time 25/05/2015
 * @version 0.0.1
 */
public class Push {

	//headers参数
	private Map<String,String> headers = new HashMap<String,String>();
	
	private String domain = "https://p.apicloud.com";
	
	//params参数
	private Map<String,String> params = new HashMap<String,String>();
	
	/**
	 * @param appId
	 * @param appKey
	 * @param domain 为空或者null为默认https
	 */
	public Push(String appId,String appKey,String domain){

		if(null!=domain&&!"".equals(domain)){
			this.domain = domain;
		}
		headers.put("X-APICloud-AppId", appId);
		headers.put("X-APICloud-AppKey", HttpUtils.encrypt(appId,appKey,"SHA-1"));
	}
	
	@SuppressWarnings("unused")
	private Push(){};
	
	/**
	 * 向某个推送组所有的成员推送消息
	 * @param title 消息标题
	 * @param content 消息内容
	 * @param type 消息类型，1:消息 2:通知
	 * @param platform 0:全部平台，1：ios, 2：android
	 * @param groupName 推送组名，多个组用英文逗号隔开.默认:全部组。eg.group1,group2
	 * @param userIds 推送用户id, 多个用户用英文逗号分隔，eg. user1,user2
	 * @return
	 */
	public JSONObject pushMessage(String title,String content,int type,int platform,String groupName,String userIds){
		
		//设置参数
		params.clear();
		params.put("title", title);
		params.put("content", content);
		params.put("type", type+"");
		
		params.put("platform", platform+"");
		params.put("groupName", groupName);
		params.put("userIds", userIds);
		String url = domain+"/api/push/message";
		
		return HttpUtils.doPost(url, headers, params, "");
	}
}
