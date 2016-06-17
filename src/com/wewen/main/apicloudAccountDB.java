package com.wewen.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.openqa.selenium.browserlaunchers.Sleeper;

import com.alibaba.fastjson.JSONObject;
import com.wewen.sdk.Resource;

public class apicloudAccountDB {
	String appid = "A6995108789170";
    String appkey = "2905656C-675F-F099-B3A6-0C2C0F131092";
    String host = "https://d.apicloud.com";
   // Resource  apiCloud=new Resource(appid, appkey, host);
	 
	 
	 public void insertOneData(String name,String intoduce,String typeId,String accountId,String imgeLink){
		 Resource  apiCloud=new Resource(appid, appkey, host);
 		boolean isExist=checkAccountExsit(accountId);
 		if(!isExist){
 			JSONObject property = new JSONObject();
 			property.put("accountName",name);
 			property.put("introduce", intoduce);
 			property.put("typeId", typeId);
 			property.put("accountId", accountId);
 			property.put("logo", "null");
 			
 			File file = null;
				try {
					file = getRemoteFile(imgeLink);
					property.put("image", file);
					JSONObject json = apiCloud.createObject("accounts", property);
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					
					if(null!=file&&file.exists())
			 			file.delete();
				}
//
// 			if(null!=file&&file.exists())
// 			file.delete();
 		}
 		
 	
 }
	  public boolean checkAccountExsit(String accountId){
		  Resource  apiCloud=new Resource(appid, appkey, host);
	    	JSONObject json = apiCloud.doFilterSearch("accounts","{\"where\":{\"accountId\":\""+accountId+"\"}}");
	    	if(json.toJSONString().length()>20){
	    		return true;
	    	}else{
	    		return false;
	    	}
	    }
	    
	    private File getRemoteFile(String strUrl) throws IOException { 
	    	URL url = new URL(strUrl); 
	    	long timeString=System.currentTimeMillis();
	    	int random=(int)(Math.random()*99) + 1;
	    	String fileName=timeString+"tomiyo"+random+".jpg";
	    	File file=new File(fileName);
	    	
	    	HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
	    	DataInputStream input = new DataInputStream(conn.getInputStream()); 
	    	DataOutputStream output = new DataOutputStream(new FileOutputStream(file)); 
	    	byte[] buffer = new byte[1024 * 8]; 
	    	int count = 0; 
	    	while ((count = input.read(buffer)) > 0) { 
	    	output.write(buffer, 0, count); 
	    	} 
	    	output.close(); 
	    	input.close(); 
	    	return file;
	    	}
}
