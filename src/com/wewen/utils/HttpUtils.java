package com.wewen.utils;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 负责和主站的http交互
 * @author wangjinzhen
 * @time 19/05/2015
 * @version 0.0.1
 */
public class HttpUtils {
	
	//cad domain
	public static final String CAD_MOUDLE_DOMAIN = "http://192.168.13.183:8001";
	
	public static final Logger log = Logger.getLogger(HttpUtils.class);
	/**
	 * 处理Post请求
	 * @param url
	 * @param params post请求参数
	 * @param jsonString post传递json数据
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static JSONObject doPost(String url,Map<String,String> headers,Map<String,String> params,String jsonString) {
		
		HttpClient client = new HttpClient();
		//post请求
		PostMethod postMethod = new PostMethod(url);
		
		//设置header
		setHeaders(postMethod,headers);
		
		//设置post请求参数
		setParams(postMethod,params);
		
		//设置post传递的json数据
		if(jsonString!=null&&!"".equals(jsonString)){
			postMethod.setRequestEntity(new ByteArrayRequestEntity(jsonString.getBytes()));
		}
		
		String responseStr = "";
		
		try {
			client.executeMethod(postMethod);
			responseStr = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			responseStr="{status:0}";
		} 
		
		return JSONObject.parseObject(responseStr);
		
	}

	/**
	 * 处理get请求
	 * @param url
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static JSONObject doGet(String url,Map<String,String> headers) {
		
		HttpClient httpClient = new HttpClient();
		//get请求
		GetMethod getMethod = new GetMethod(url);
		
		//设置请求头部信息
		setHeaders(getMethod,headers);
		
		String responseStr = "";
		try {
			httpClient.executeMethod(getMethod);
			responseStr = getMethod.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			responseStr = "{status:0,msg:查询信息报错,请检查参数是否正确}";
		} 
		try{
			return JSONObject.parseObject(responseStr);
		}catch(ClassCastException e){
			JSONObject json = new JSONObject();
		    json.put("data",JSONArray.parseArray(responseStr));
		    return json;
		}
	}
	
	/**
	 * 处理Put请求
	 * @param url
	 * @param headers
	 * @param object
	 * @param property
	 * @return
	 */
	public static JSONObject doPut(String url, Map<String, String> headers,String jsonString) {

		HttpClient httpClient = new HttpClient();
		
		PutMethod putMethod = new PutMethod(url);
		
		//设置header
		setHeaders(putMethod,headers);
		
		//设置put传递的json数据
		if(jsonString!=null&&!"".equals(jsonString)){
			putMethod.setRequestEntity(new ByteArrayRequestEntity(jsonString.getBytes()));
		}
		
		String responseStr = "";
		
		try {
			httpClient.executeMethod(putMethod);
			responseStr = putMethod.getResponseBodyAsString();
		} catch (Exception e) {
			log.error(e);
			responseStr="{status:0}";
		} 
		return JSONObject.parseObject(responseStr);
	}
	
	/**
	 * 处理delete请求
	 * @param url
	 * @param headers
	 * @return
	 */
	public static JSONObject doDelete(String url, Map<String, String> headers){
		
		HttpClient httpClient = new HttpClient();
		
		DeleteMethod deleteMethod = new DeleteMethod(url);
		
		//设置header
		setHeaders(deleteMethod,headers);
		
		String responseStr = "";
		
		try {
			httpClient.executeMethod(deleteMethod);
			responseStr = deleteMethod.getResponseBodyAsString();
		} catch (Exception e) {
			log.error(e);
			responseStr="{status:0}";
		} 
		return JSONObject.parseObject(responseStr);
	}
	
	/**
	 * 处理文件上传
	 * @param url
	 * @param filePath
	 * @param headers
	 * @param params
	 * @return
	 */
	public static JSONObject doUpload(String url,Map<String,String> pathsMap,Map<String,String> headers,Map<String,String> params){
		
		//post请求
		PostMethod filePost = new PostMethod(url);
		
		//设置请求头部信息
		setHeaders(filePost,headers);
		
		int length = params.size()+pathsMap.size();
		Part[] parts = new Part[length]; 
		//设置请求参数
		Set<String> paramsKey = params.keySet();
		
		Iterator<String> iterKey = paramsKey.iterator();
		int num = 0 ;
		while(iterKey.hasNext()){
			String key = iterKey.next();
			//StringPart:普通的文本参数
			parts[num++] = new StringPart(key, params.get(key));
		}
		
		//返回信息
		String responseStr = "";
		try {
			
			//设置上传文件
			Set<String> pathsKey = pathsMap.keySet();
			Iterator<String> iterPath = pathsKey.iterator();
			while(iterPath.hasNext()){
				String key = iterPath.next();
				File file = new File(pathsMap.get(key));
				parts[num++] = new FilePart(key, file);
			}
			
            MultipartRequestEntity mre=new MultipartRequestEntity(parts,filePost.getParams());
            
            filePost.setRequestEntity(mre);
			
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
			
			client.executeMethod(filePost);
			
			responseStr = filePost.getResponseBodyAsString();
		} catch (Exception e) {
			log.error(e);
			responseStr = "{status:0}";
		}
		return JSONObject.parseObject(responseStr);

	}
	
	/**
	 * 上传单个文件
	 * @param url
	 * @param filePath
	 * @param headers
	 * @param params
	 * @return
	 */
	public static JSONObject doUpload(String url, String filePath,Map<String, String> headers, Map<String, String> params) {
		Map<String,String> paths = new HashMap<String,String>();
		paths.put("file", filePath);
		return doUpload(url, paths,headers, params);
	}
	
	/**
	 * 执行加密算法
	 * @param strSrc 需要加密的字符串
	 * @param encName 加密规则
	 * @return
	 */
	public static String encrypt(String appId,String appKey, String encName) {
		
		Date date = new Date();
		Long time = date.getTime();
		
		String strSrc = appId+"UZ"+appKey+"UZ" + time;
		
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			if (encName == null || encName.equals("")) {
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest())+ "." + time; // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}
	
	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	/**
	 * 检验路径是否有效
	 * @param filePath
	 * @return
	 */
	public static boolean checkPath(String filePath) {
		if(null==filePath||"".equals(filePath)){
			return false;
		}
		File file = new File(filePath);
		if(!file.exists()){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 设置请求头部信息
	 * @param method
	 * @param headers
	 */
	private static void setHeaders(HttpMethod method,Map<String, String> headers) {
		Set<String> headersKeys = headers.keySet();
		Iterator<String> iterHeaders = headersKeys.iterator();
		while(iterHeaders.hasNext()){
			String key = iterHeaders.next();
			method.setRequestHeader(key, headers.get(key));
		}
	}
	
	/**
	 * 设置请求参数
	 * @param method
	 * @param params
	 */
	private static void setParams(PostMethod method,Map<String, String> params) {

		//校验参数
		if(params==null||params.size()==0){
			return;
		}
		
		Set<String> paramsKeys = params.keySet();
		Iterator<String> iterParams = paramsKeys.iterator();
		while(iterParams.hasNext()){
			String key = iterParams.next();
			method.addParameter(key, params.get(key));
		}
		
	}

}
