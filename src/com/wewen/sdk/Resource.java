package com.wewen.sdk;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.wewen.utils.HttpUtils;

public class Resource{

	//headerså�‚æ•°
	private Map<String,String> headers = new HashMap<String,String>();
	
	private String domain = "https://d.apicloud.com";
	
	/**
	 * @param appId
	 * @param appKey
	 * @param domain ä¸ºç©ºæˆ–è€…nullä¸ºé»˜è®¤https
	 */
	public Resource(String appId,String appKey,String domain){
		
		if(null!=domain&&!"".equals(domain)){
			this.domain = domain;
		}
		headers.put("X-APICloud-AppId", appId);
		headers.put("X-APICloud-AppKey", HttpUtils.encrypt(appId,appKey,"SHA-1"));
	}
	
	@SuppressWarnings("unused")
	private Resource() {}

	/** å¯¹è±¡ ------begin------ **/
	
	/**
	 * åˆ›å»ºå¯¹è±¡
	 * @param object å¯¹è±¡å��ç§°
	 * @param property å¯¹è±¡æ‰€å…·æœ‰çš„å±žæ€§
	 * @return
	 */
	public JSONObject createObject(String object,JSONObject property){
		
		//æ ¡éªŒæ˜¯å�¦ä¼ é€’å�‚æ•°
		if(property==null){
			property = new JSONObject();
		}
		
		handleFile(property);
		
		headers.put("Content-Type", "application/json");
		
		String url = domain+"/mcm/api/"+object;
		
		return HttpUtils.doPost(url, headers, null,property.toJSONString());
	}
	
	/**
	 * æ ¹æ�®idèŽ·å�–å¯¹è±¡
	 * @param object å¯¹è±¡å��ç§°
	 * @param id å¯¹è±¡Id
	 * @return
	 */
	public JSONObject getObject(String object,String id){
		
		String url = domain+"/mcm/api/"+object+"/"+id;
		
		return HttpUtils.doGet(url, headers);
	}
	
	/**
	 * èŽ·å�–æ‰€æœ‰å¯¹è±¡
	 * @param object å¯¹è±¡å��ç§°
	 * @return
	 */
	public JSONObject getObjects(String object){
		
		String url = domain+"/mcm/api/"+object;
		
		return HttpUtils.doGet(url, headers);
	}
	
	/**
	 * æ›´æ–°å¯¹è±¡
	 * @param object
	 * @param id
	 * @param property
	 * @return
	 */
	public JSONObject updateObject(String object,String id,JSONObject property){
		
		headers.put("Content-Type", "application/json");

		if(property==null||property.size()==0){
			return JSONObject.parseObject("{status:0,msg:\"è¯·è‡³å°‘æ›´æ–°ä¸€ä¸ªå­—æ®µ\"}");
		}
		
		String url = domain+"/mcm/api/"+object+"/"+id;
		
		return HttpUtils.doPut(url,headers,property.toJSONString());
	}
	
	/**
	 * åˆ é™¤å¯¹è±¡
	 * @param object å¯¹è±¡å��ç§°
	 * @param id å¯¹è±¡Id
	 * @return
	 */
	public JSONObject deleteObject(String object,String id){
		
		String url = domain+"/mcm/api/"+object+"/"+id;
		
		return HttpUtils.doDelete(url, headers);
	}
	
	/**
	 * ç»Ÿè®¡å¯¹è±¡æ•°é‡�
	 * @param object å¯¹è±¡å��ç§°
	 * @return
	 */
	public JSONObject getObjectCount(String object){
		
		String url = domain+"/mcm/api/"+object+"/count";
		
		return HttpUtils.doGet(url, headers);
	}
	
	/**
	 * åˆ¤æ–­å¯¹è±¡æ˜¯å�¦å­˜åœ¨
	 * @param object å¯¹è±¡å��ç§°
	 * @param id å¯¹è±¡Id
	 * @return
	 */
	public JSONObject checkObjectExists(String object,String id){
		
		String url = domain+"/mcm/api/"+object+"/"+id+"/exists";
		
		return HttpUtils.doGet(url, headers);
	}
	/** å¯¹è±¡ ------end------ **/

	/** Relationå¯¹è±¡ ------begin------ **/
	
	/**
	 * èŽ·å�–å…³è�”å¯¹è±¡
	 * @param object
	 * @param id
	 * @param relationObject
	 * @return
	 */
	public JSONObject getRelationObject(String object,String id,String relationObject){
		
		String url = domain+"/mcm/api/"+object+"/"+id+"/"+relationObject;
		
		return HttpUtils.doGet(url, headers);	
	}
	
	/**
	 * åˆ›å»ºå…³è�”å¯¹è±¡
	 * @param object
	 * @param id
	 * @param relationObject
	 * @return
	 */
	public JSONObject createRelationObject(String object,String id,String relationObject,JSONObject property){
		
		//å¤„ç�†æ–‡ä»¶å�‚æ•°
		handleFile(property);
		
		String url = domain+"/mcm/api/"+object+"/"+id+"/"+relationObject;
		
		Map<String,String> propertyMap = new HashMap<String,String>();
		Set<String> propertySet = property.keySet();
		Iterator<String> iterProperty = propertySet.iterator();
		while(iterProperty.hasNext()){
			String key = iterProperty.next();
			propertyMap.put(key, property.getString(key));
		}
		
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		
		return HttpUtils.doPost(url, headers,propertyMap,"");	
	}
	
	/**
	 * ç»Ÿè®¡å…³è�”å¯¹è±¡æ•°é‡�
	 * @param object
	 * @param id
	 * @param relationObject
	 * @return
	 */
	public JSONObject getRelationObjectCount(String object,String id,String relationObject){
		
		String url = domain+"/mcm/api/"+object+"/"+id+"/"+relationObject+"/count";
		
		return HttpUtils.doGet(url, headers);	
	}
	
	/**
	 * åˆ é™¤æ‰€æœ‰å…³è�”å¯¹è±¡
	 * @param object
	 * @param id
	 * @param relationObject
	 * @return
	 */
	public JSONObject deleteRelationObject(String object,String id,String relationObject){
		
		String url = domain+"/mcm/api/"+object+"/"+id+"/"+relationObject;
		
		return HttpUtils.doDelete(url, headers);	
	}
	/** Relationå¯¹è±¡ ------end------ **/

	/** ç”¨æˆ· ------begin------ **/
	
	/**
	 * åˆ›å»ºç”¨æˆ·
	 * @param property
	 * @return
	 */
	public JSONObject createUser(JSONObject property){
		
		//æ ¡éªŒæ˜¯å�¦ä¼ é€’å�‚æ•°
		if(property==null){
			return JSONObject.parseObject("{status:0,msg:\"è¯·ä¼ é€’å�‚æ•°\"}");
		}
		
		String userName = property.getString("username");
		if(null==userName||"".equals(userName)){
			return JSONObject.parseObject("{status:0,msg:\"å§“å��ä¸�èƒ½ä¸ºç©º\"}");
		}
		
		String password = property.getString("password");
		if(null==password||"".equals(password)){
			return JSONObject.parseObject("{status:0,msg:\"å¯†ç �ä¸�èƒ½ä¸ºç©º\"}");
		}
		
		//å¤„ç�†æ–‡ä»¶å�‚æ•°
		handleFile(property);
		
		headers.put("Content-Type", "application/json");
		
		String url = domain+"/mcm/api/user";
		
		return HttpUtils.doPost(url, headers, null,property.toJSONString());
	}
	
	/**
	 * ç”¨æˆ·ç™»å½•
	 * @param userName
	 * @param password
	 * @return
	 */
	public JSONObject userLogin(String userName,String passWord){
		
		headers.put("Content-Type", "application/json");
		
		if(null==userName||"".equals(userName)){
			return JSONObject.parseObject("{status:0,msg:\"å§“å��ä¸�èƒ½ä¸ºç©º\"}");
		}
		
		if(null==passWord||"".equals(passWord)){
			return JSONObject.parseObject("{status:0,msg:\"å¯†ç �ä¸�èƒ½ä¸ºç©º\"}");
		}
		
		String url = domain+"/mcm/api/user/login";
		
		JSONObject property = new JSONObject();
		property.put("username", userName);
		property.put("password", passWord);
		
		JSONObject returnJson = HttpUtils.doPost(url, headers, null,property.toJSONString());
		
		handleAuthorization(returnJson);
		
		return returnJson;
	}
	
	/**
	 * è¯·æ±‚éªŒè¯�Email
	 * @param property
	 * @return
	 */
	public JSONObject verifyEmail(JSONObject property){
		
		headers.put("Content-Type", "application/json");
		
		//æ ¡éªŒæ˜¯å�¦ä¼ é€’å�‚æ•°
		if(property==null){
			return JSONObject.parseObject("{status:0,msg:\"è¯·ä¼ é€’å�‚æ•°\"}");
		}
		
		String userName = property.getString("username");
		if(null==userName||"".equals(userName)){
			return JSONObject.parseObject("{status:0,msg:\"å§“å��ä¸�èƒ½ä¸ºç©º\"}");
		}
		
		String email = property.getString("email");
		if(null==email||"".equals(email)){
			return JSONObject.parseObject("{status:0,msg:\"é‚®ç®±ä¸�èƒ½ä¸ºç©º\"}");
		}
		
		String url = domain+"/mcm/api/user/verifyEmail";
		
		return HttpUtils.doPost(url, headers, null,property.toJSONString());
	}
	
	/**
	 * å¯†ç �é‡�ç½®
	 * @param property
	 * @return
	 */
	public JSONObject resetRequest(JSONObject property){
		
		headers.put("Content-Type", "application/json");
		
		//æ ¡éªŒæ˜¯å�¦ä¼ é€’å�‚æ•°
		if(property==null){
			return JSONObject.parseObject("{status:0,msg:\"è¯·ä¼ é€’å�‚æ•°\"}");
		}
		
		String userName = property.getString("username");
		if(null==userName||"".equals(userName)){
			return JSONObject.parseObject("{status:0,msg:\"å§“å��ä¸�èƒ½ä¸ºç©º\"}");
		}
		
		String email = property.getString("email");
		if(null==email||"".equals(email)){
			return JSONObject.parseObject("{status:0,msg:\"é‚®ç®±ä¸�èƒ½ä¸ºç©º\"}");
		}
		
		String url = domain+"/mcm/api/user/resetRequest";
		
		return HttpUtils.doPost(url, headers, null,property.toJSONString());
	}
	
	/**
	 * èŽ·å�–ç”¨æˆ·
	 * @param authorization login è¿”å›žçš„id
	 * @param userId
	 * @return
	 */
	public JSONObject getUser(String userId){
		
		headers.put("Content-Type", "application/json");

		String url = domain+"/mcm/api/user/"+userId;
		
		return HttpUtils.doGet(url, headers);
	}
	
	/**
	 * æ›´æ–°ç”¨æˆ·
	 * @param authorization
	 * @param userId
	 * @param property éœ€è¦�æ›´æ–°çš„å±žæ€§
	 * @return
	 */
	public JSONObject updateUser(String userId,JSONObject property){
		
		headers.put("Content-Type", "application/json");

		if(null==property){
			property = new JSONObject();
		}
		
		String url = domain+"/mcm/api/user/"+userId;
		
		return HttpUtils.doPut(url, headers, property.toJSONString());
	}
	
	/**
	 * åˆ é™¤ç”¨æˆ·
	 * @param authorization
	 * @param userId
	 * @return
	 */
	public JSONObject deleteUser(String userId){
		
		headers.put("Content-Type", "application/json");
		
		String url = domain+"/mcm/api/user/"+userId;
		
		return HttpUtils.doDelete(url, headers);
	}
	
	/**
	 * ç™»å‡º
	 * @param authorization
	 * @return
	 */
	public JSONObject loginOut(){
		
		headers.put("Content-Type", "application/json");
		
		String url = domain+"/mcm/api/user/logout";
		
		return HttpUtils.doPost(url, headers, null, "");
	}
	
	/** ç”¨æˆ· ------end------ **/
	
	/** è§’è‰² ------end------ **/
	
	/**
	 * åˆ›å»ºè§’è‰²
	 * @param property
	 * @return
	 */
	public JSONObject createRole(JSONObject property){
		
		//æ ¡éªŒæ˜¯å�¦ä¼ é€’å�‚æ•°
		if(property==null){
			return JSONObject.parseObject("{status:0,msg:\"è¯·ä¼ é€’å�‚æ•°\"}");
		}
		
		//å¤„ç�†æ–‡ä»¶å�‚æ•°
		handleFile(property);
		
		headers.put("Content-Type", "application/json");
		
		String url = domain+"/mcm/api/role";
		
		return HttpUtils.doPost(url, headers, null,property.toJSONString());
	}
	
	/**
	 * æ ¹æ�®IdèŽ·å�–è§’è‰²
	 * @param id
	 * @return
	 */
	public JSONObject getRole(String id){
		
		String url = domain+"/mcm/api/role/"+id;
		
		return HttpUtils.doGet(url, headers);	
	}
	
	/**
	 * æ ¹æ�®idæ›´æ–°è§’è‰²
	 * @param id
	 * @param property
	 * @return
	 */
	public JSONObject updateRole(String id,JSONObject property){
		
		//æ ¡éªŒæ˜¯å�¦ä¼ é€’å�‚æ•°
		if(property==null){
			return JSONObject.parseObject("{status:0,msg:\"è¯·ä¼ é€’å�‚æ•°\"}");
		}
		
		headers.put("Content-Type", "application/json");
		
		String url = domain+"/mcm/api/role/"+id;
		
		return HttpUtils.doPut(url, headers,property.toJSONString());
	}
	
	/**
	 * æ ¹æ�®Idåˆ é™¤è§’è‰²
	 * @param id
	 * @return
	 */
	public JSONObject deleteRole(String id){
		
		String url = domain+"/mcm/api/role/"+id;
		
		return HttpUtils.doDelete(url, headers);
	}
	/** è§’è‰² ------end------ **/
		
	/** æ‰¹é‡�æ“�ä½œ------begin------ **/
	
	/**
	 * @param prams
	 * @return
	 */
	public JSONObject batch(JSONObject params){
		
		//æ ¡éªŒæ˜¯å�¦ä¼ é€’å�‚æ•°
		if(params==null){
			return JSONObject.parseObject("{status:0,msg:\"è¯·ä¼ é€’å�‚æ•°\"}");
		}
		
		String url = domain+"/mcm/api/batch";
		headers.put("Content-Type", "application/json");

		return HttpUtils.doPost(url, headers, null, params.toJSONString());
	}
	
	/** æ‰¹é‡�æ“�ä½œ------end------ **/

	/** æ–‡ä»¶(file)------begin------ **/
	
	/**
	 * æ–‡ä»¶ä¸Šä¼ 
	 * @param fileName
	 * @return
	 */
	public JSONObject upload(String filePath){
		
		if(null==filePath||"".equals(filePath)){
			return JSONObject.parseObject("{status:0,msg:\"è·¯å¾„ä¸�èƒ½ä¸ºç©º\"}");
		}
		
		String url = domain+"/mcm/api/file";
		
		return HttpUtils.doUpload(url, filePath , headers, new HashMap<String,String>());
	}
	
	/** æ–‡ä»¶(file)------end------ **/
	
	/** æ›´æ–°æ“�ä½œç¬¦------begin------ **/
	
	/**
	 * @param id
	 * @param params
	 * @return
	 */
	public JSONObject updateModel(String object,String id,JSONObject params){
		
		//æ ¡éªŒæ˜¯å�¦ä¼ é€’å�‚æ•°
		if(params==null){
			return JSONObject.parseObject("{status:0,msg:\"è¯·ä¼ é€’å�‚æ•°\"}");
		}
		
		String url = domain+"/mcm/api/"+object+"/"+id;
		
		return HttpUtils.doPut(url, headers, params.toJSONString());
	}
	
	/** æ›´æ–°æ“�ä½œç¬¦------end------ **/
	
	/** æ�¡ä»¶è¿‡æ»¤------begin------ **/
	
	/**
	 * æŒ‰ç…§æ�¡ä»¶è¿‡æ»¤
	 * @param object
	 * @param filter
	 * @return
	 */
	public JSONObject doFilterSearch(String object,String filter){
		
		if(null==object||"".equals(object)){
			return JSONObject.parseObject("{status:0,msg:\"è¯·ç¡®å®šæŸ¥è¯¢å¯¹è±¡\"}");
		}
		
		String url = domain+"/mcm/api/"+object+"?filter=";
		try {
			url += URLEncoder.encode(filter, "utf-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		return HttpUtils.doGet(url, headers);
	}
	
	/** æ�¡ä»¶è¿‡æ»¤------end------ **/
	
	/** å®‰å…¨ç›¸å…³------begin------**/
	 
	/**
	 * è®¾ç½®æ�ƒé™�éªŒè¯�ç �
	 * @param authorization
	 */
	public void setAuthorization(String authorization){
		if(null!=authorization&&!"".equals(authorization)){
			headers.put("authorization", authorization);
		}
	}
	
	/** å®‰å…¨ç›¸å…³------end------**/
	/**
	 * æŸ¥çœ‹å�‚æ•°ä¸­æ˜¯å�¦å�«æœ‰fileå¯¹è±¡ï¼Œå¦‚æžœæœ‰çš„è¯�ï¼Œå…ˆä¸Šä¼ ï¼Œåœ¨å°†è¿”å›žçš„ä¿¡æ�¯æ›¿æ�¢æŽ‰åŽŸæ�¥çš„fileå¯¹è±¡
	 * @param property
	 */
	private void handleFile(JSONObject property) {
		//æŸ¥çœ‹valuesä¸­æ˜¯å�¦æœ‰fileå¯¹è±¡
		Set<String> keySet = property.keySet();
		Iterator<String> keyIter = keySet.iterator();
		while(keyIter.hasNext()){
			String key = keyIter.next();
			Object obj = property.get(key);
			if(null!=obj&&obj instanceof File){
				File file = (File)obj;
				if(file.exists()&&file.isFile()){
					JSONObject fileJson = upload(file.getPath());
					property.put(key, fileJson.toJSONString());
				}
			}
		}
	}
	
	/**
	 * @param returnJson
	 * å¤„ç�†loginè¿”å›žæ�¥çš„authorization,ç™»å½•ä»¥å�Žç¼“å­˜ç”¨æˆ·çš„æ ¡éªŒç �
	 */
	private void handleAuthorization(JSONObject returnJson) {
		String key = returnJson.getString("id");
		if(null!=key&&!"".equals(key)){
			headers.put("authorization", key);
		}
	}
}
