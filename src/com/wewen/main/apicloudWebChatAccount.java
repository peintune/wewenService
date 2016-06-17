/**
* @Title: DataGetUtil.java 
* @Package application 
* @Description: To do something
* @version V1.0   
 */
package com.wewen.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;


import org.dom4j.Document;

import org.dom4j.Element;

import org.dom4j.io.SAXReader;
import org.apache.bcel.generic.DCONST;
import org.apache.bcel.generic.RETURN;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;



import org.json.JSONArray;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;


import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;





import com.wewen.sdk.Resource;
//import org.openqa.selenium.Cookie;

/**
 * @author HEKUN777
 *
 */
public class apicloudWebChatAccount {

	/**
	 * @param args
	 */
	static Resource apiCloud=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	     String appid = "A6995108789170";
         String appkey = "2905656C-675F-F099-B3A6-0C2C0F131092";
         String host = "https://d.apicloud.com";
		 apiCloud=new Resource(appid, appkey, host);
	//	JSONObject json = apiCloud.getObjects("accounts");

		apicloudWebChatAccount dataGetUtil=new apicloudWebChatAccount();
	//	dataGetUtil.insertData();
		dataGetUtil.catchAccountsToDB();
		try {
			//dataGetUtil.htmlUnit2("我们爱恶搞","auaooo");
		//	System.out.println(dataGetUtil.checkAccountsExsist());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String htmlUnit2(String name,String webAccount) throws Exception{

		String decodename =name;
		File resultFile=new File("result/"+webAccount);
		BufferedWriter bf=new BufferedWriter(new PrintWriter(resultFile));
		try {
			decodename = URLEncoder.encode(name, "GBK");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		String parseUrl="http://weixin.sogou.com/weixin?query="+decodename;
		
		
		WebClient wc=new WebClient();
		URL link=new URL(parseUrl); 
		WebRequest request=new WebRequest(link); 
		request.setCharset("UTF-8");
		 request.getAdditionalHeaders().clear();
         request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
         request.setAdditionalHeader("Cookie", "CXID=98D12A8959E9158BE94713533C290FA0; SUID=66605F6520168B0A5509220F000DE247; IPLOC=CN3100; usid=; ABTEST=0|1437013184|v1; weixinIndexVisited=1; ld=alllllllll2q$a6wlllllVQomyklllllN5NZ7Zllll9lllll9voll5@@@@@@@@@@; SUV=1437120778936331146870538; cid=newssearch2ww; ssuid=8905669144; PHPSESSID=1shu04tf21sqgfucq7fkpr18s4; SUIR=498A0E8EF9FFE00E4E933321FA98D705; ad=xQp9SZllll2qXN$blllllVQ7ut6lllllN5NZ7Zllll9lllllVllll5@@@@@@@@@@; SNUID=D91A9D1E696F7123F5F3B7E46A43F77C; wapsogou_qq_nickname=; LSTMV=458%2C66; LCLKINT=1096; sct=5");
        
         wc.getCookieManager().setCookiesEnabled(true);
          wc.getOptions().setJavaScriptEnabled(true);
          wc.getOptions().setCssEnabled(false);
          wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
          wc.getOptions().setThrowExceptionOnScriptError(false);
          wc.getOptions().setTimeout(10000);
          HtmlPage page=null;
          page = wc.getPage(request);

          
		
	    WebDriver driver = new HtmlUnitDriver(){  
	        @Override  
	        protected WebClient modifyWebClient(WebClient client)  
	        {  
	            DefaultCredentialsProvider creds = new DefaultCredentialsProvider();  
	            client.setCredentialsProvider(creds);  
	            client.getOptions().setJavaScriptEnabled(true);
	            client.getOptions().setThrowExceptionOnScriptError(false);
	            client.getOptions().setCssEnabled(false);	            
	            return client;  
	        }  
	    };

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		int randomValue=(int)Math.random()*3+1;
		//Thread.sleep(1000*randomValue);
	//	driver.get(parseUrl);
		
		Boolean continueTag=true;
 		for(int i=1;i<50;i++){
		if(!continueTag){
			break;
		}	

		List<HtmlDivision> webChatAccountElements = (List<HtmlDivision>) page.getByXPath("//div[@href and @uigs]");
		if(webChatAccountElements.size()<10){
			continueTag=false;
		}
		//if(webChatAccountElements.size()==0){
		//return false;//没有此公众号
		//}
		String textString="";
		for(HtmlDivision el:webChatAccountElements){
			//String fakeName=el.findElement(By.tagName("h3")).getText();
			String fakeName=el.getElementsByTagName("h3").get(0).asText();
			//String  webChatName=el.findElement(By.tagName("h4")).getText().split("微信号：")[1].trim();
			String  webChatName=el.getElementsByTagName("h4").get(0).asText().split("微信号：")[1].trim();
			String functionIntro="";
			String openIdString="";
			String webIconString="";
			String posIco="";
			String blogUrlString="";
		if(webChatName.equals(webAccount)&&fakeName.equals(name)){
			continueTag=false;
			//functionIntro=el.findElements(By.className("s-p3")).get(0).getText();
			functionIntro=el.getByXPath("//span[@class=\"sp-txt\"]").get(0).toString();
			//openIdString=el.getAttribute("href").split("openid=")[1].trim();
			openIdString=el.getAttribute("href").split("openid=")[1].trim();
			//webIconString=el.findElement(By.className("img-box")).findElement(By.tagName("img")).getAttribute("src");
			//	webIconString=el.gete(By.className("img-box")).findElement(By.tagName("img")).getAttribute("src");
			//posIco=el.findElement(By.className("pos-ico")).findElements(By.tagName("img")).get(1).getAttribute("src");
			
			blogUrlString="http://weixin.sogou.com/gzh?openid="+openIdString;

		     Runtime rt = Runtime.getRuntime();   
	        Process p = rt.exec("tools/phantomjs.exe js/netlog.js "+blogUrlString);
	        InputStream is = p.getInputStream();   
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));   
	        String tmp = "";  
	        while((tmp = br.readLine())!=null){ 
	        	if(tmp.contains("gzhjs?cb=sogou.weixin.gzhc")){
	        		break;
	        	}
	        }  
	        int count=1;
		//System.out.print(tmp.toString());
		for(int j=1;j<50000;j++){
			tmp=tmp.replace("page="+(j-1),"page="+j);
			driver.get(tmp);
			Thread.sleep(2000);
			String orignString=driver.getPageSource().toString().split("\n")[1];
			String resultString=orignString.substring(19,orignString.length()-1);
			if(resultString.length()>300){
				// JSONObject jb =new JSONObject(resultString);
				//JSONArray jsonArray = jb.getJSONArray("items");
				
				 JSONObject jb =null;
					JSONArray jsonArray = null;
				if(jsonArray.length()>0){
					for(int k=0;k<jsonArray.length();k++){
						SAXReader reader = new SAXReader();  
			          Document document;   
			          document = DocumentHelper.parseText(jsonArray.get(k).toString());  
			          Element root = document.getRootElement().element("item").element("display");  
					 String title = root.selectSingleNode("title").getText();
					String title1=root.selectSingleNode("title1").getText();
					String urlString=root.selectSingleNode("url").getText();
					String imageLinkString=root.selectSingleNode("imglink").getText();
					String content168=root.selectSingleNode("content168").getText();
					Long unixTimeString=Long.parseLong(root.selectSingleNode("lastModified").getText())*1000 ;
					String timeString=new java.text.SimpleDateFormat("yyyy MM-dd HH:mm:ss").format(new java.util.Date(unixTimeString));
					int random=(int)Math.random()*4+2;
					//Thread.sleep(1000*random);
					String yueduliangUrl="http://51tools.info/wx/api.ashx?uin=MjQ3MDM5OTI2MQ==";
					//String getYueduLiangUrl=yueduliangUrl+"&urls="+urlString;
					//Thread.sleep(1500);
					//String yueduliangresult=dopost(yueduliangUrl,urlString);
					// JSONObject yueDujson =new JSONObject(yueduliangresult);		 
					// JSONArray yueDujsonArrays = yueDujson.getJSONArray("data");
					// if(yueDujsonArrays.length()==0){
					//	 Thread.sleep(50000);
					// }
					// String yueduliang=yueDujsonArrays.getJSONObject(0).get("readnums").toString();					 
					// String dianzanliang=yueDujsonArrays.getJSONObject(0).get("supports").toString();
					// String yueduliang=yueDujsonArray.getString(index);
					String dianzanliang="";
					String yueduliang="";
					bf.append("第"+(count++)+"条**标题："+title+"**文章地址："+urlString+"**图片地址："+imageLinkString+"**简介："+content168+"**更新时间："+timeString+"**阅读量："+yueduliang+"**点赞量："+dianzanliang+"\n");
					System.out.println("**第"+(count)+"条***"+title+"**"+yueduliang+"**"+dianzanliang);
					}
									
				}
			}else{ 
				if(j<10){
					continue;
				}else{
					bf.close();
					break;
				}
				
			}
			
			
		}
		//String resultString=driver.getPageSource().toString();
		//List<WebElement> dfdf=	driver.findElements(By.className("news_lst_tab"));
		//System.out.println(dfdf.get(1).getText()+"没有的啊");
		//	break;
		}
		}
		//driver.findElement(By.id("sogou_next")).click();
		}
		
		return "";
	}


	public String dopost(String url,String para){
		String result="";
		HttpPost httpRequst = new HttpPost(url);
		 List <NameValuePair> params = new ArrayList<NameValuePair>();  
		 params.add(new BasicNameValuePair("urls", para));  
		 try {
			httpRequst.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);  
			if(httpResponse.getStatusLine().getStatusCode() == 200)  
            {  
                HttpEntity httpEntity = httpResponse.getEntity();  
                result = EntityUtils.toString(httpEntity);//取出应答字符串  
            }  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result = e.getMessage().toString();  
			e.printStackTrace();
		} 
		return result;
	}
	

    public static String getSha1(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i]& 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
}

    public static String checkAccountsExsist() throws NoSuchAlgorithmException, ClientProtocolException, IOException
    {
            String appid = "A6995108789170";
            String appkey = "2905656C-675F-F099-B3A6-0C2C0F131092";
            String host = "https://d.apicloud.com";
            
            String  getParam =  "?filter[order]=createdAt DESC";
           // getParam += "&filter[where][id][lt]=' + latestDomId";
            
            HttpClient client = HttpClients.createDefault();
            HttpGet get = new HttpGet(host+"/mcm/api/accounts"+getParam);
            get.addHeader("X-APICloud-AppId",appid);
            long time = System.currentTimeMillis();
            get.addHeader("X-APICloud-AppKey",getSha1(appid+"UZ"+appkey+"UZ"+time)+"."+time);
            HttpResponse response = client.execute(get);
            InputStream is=response.getEntity().getContent();
            //创建包装流
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            //定义String类型用于储存单行数据
            String line=null;
            //创建StringBuffer对象用于存储所有数据
            StringBuffer sb=new StringBuffer();
            while((line=br.readLine())!=null){
            sb.append(line);
            }
            
            return sb.toString();
            
    }

    
    public boolean checkAccountExsit(String accountId){
    	
    	JSONObject json = apiCloud.doFilterSearch("accounts","{\"where\":{\"accountId\":\""+accountId+"\"}}");
    	if(json.toJSONString().length()>20){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public void insertData(){
    	
    	List<String[]> lists=	catchAccountsToDB();
    	for(String[] ss:lists){
    		boolean isExist=checkAccountExsit(ss[1]);
    		if(!isExist){
    			JSONObject property = new JSONObject();
    			property.put("accountName", ss[0]);
    			property.put("introduce", ss[3]);
    			property.put("typeId", ss[4]);
    			property.put("accountId", ss[1]);
    			property.put("logo", "null");
    			
    			File file = null;
				try {
					file = getRemoteFile(ss[2]);
					property.put("image", file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					file.delete();
				}

    			JSONObject json = apiCloud.createObject("accounts", property);
    		}
    		
    	}
    }
    

    
    
    private File getRemoteFile(String strUrl) throws IOException { 
    	URL url = new URL(strUrl); 
    	long timeString=System.currentTimeMillis();
    	String fileName=timeString+".jpg";
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
    	DataInputStream input = new DataInputStream(conn.getInputStream()); 
    	DataOutputStream output = new DataOutputStream(new FileOutputStream(fileName)); 
    	byte[] buffer = new byte[1024 * 8]; 
    	int count = 0; 
    	while ((count = input.read(buffer)) > 0) { 
    	output.write(buffer, 0, count); 
    	} 
    	output.close(); 
    	input.close(); 
    	return new File(fileName);
    	}
    public List<String[]> catchAccountsToDB(){
    	String urlString="http://www.anyv.net/index.php/category-2-page-1";
    	int pageSize=75;
    	List<String[]> accountList=new ArrayList<String[]>();
    	try {
    		
    		//news
        	getAccountsThread getAccountsThread1=new getAccountsThread(urlString, 75, accountList,"55eaf9626e276ac44e0c8ff4");
        	getAccountsThread1.start();
      

        	
//        	//tech
        	getAccountsThread getAccountsThread2=new getAccountsThread("http://www.anyv.net/index.php/category-19-page-1", 109, accountList,"55ec0f44a85dc51b1dd71b8c");
        	getAccountsThread2.start();
//        	
//        	
//        	//read
        	getAccountsThread getAccountsThread3=new getAccountsThread("http://www.anyv.net/index.php/category-25-page-1", 70, accountList,"55ec0f82309d2e5417647d83");
        	getAccountsThread3.start();
//        	
//        	
//        	//study
        	getAccountsThread getAccountsThread4=new getAccountsThread("http://www.anyv.net/index.php/category-21-page-1", 150, accountList,"55ec1051a85dc51b1dd71b98");
        	getAccountsThread4.start();
//        	
//        	
//        	//funny
        	getAccountsThread getAccountsThread5=new getAccountsThread("http://www.anyv.net/index.php/category-3-page-1", 80, accountList,"55ec10b9f386b83d6a40bf01");
        	getAccountsThread5.start();
        	
        	
//        	//lifeandtravel
        	getAccountsThread getAccountsThread6=new getAccountsThread("http://www.anyv.net/index.php/category-23-page-1", 405, accountList,"55ec10f0f386b83d6a40bf02");
        	getAccountsThread6.start();
//        	
//        	//lifeandtravel
        	getAccountsThread getAccountsThread7=new getAccountsThread("http://www.anyv.net/index.php/category-27-page-1", 71, accountList,"55ec10f0f386b83d6a40bf02");
        	getAccountsThread7.start();
        	
//        	//lifeandtravel
        	getAccountsThread getAccountsThread8=new getAccountsThread("http://www.anyv.net/index.php/category-20-page-1", 63, accountList,"55ec10f0f386b83d6a40bf02");
        	getAccountsThread8.start();
//        	
//        	//lifeandtravel
        	getAccountsThread getAccountsThread9=new getAccountsThread("http://www.anyv.net/index.php/category-26-page-1", 159, accountList,"55ec10f0f386b83d6a40bf02");
        	getAccountsThread9.start();
        	
        	//lifeandtravel
        	getAccountsThread getAccountsThread10=new getAccountsThread("http://www.anyv.net/index.php/category-28-page-1", 21, accountList,"55ec10f0f386b83d6a40bf02");
        	getAccountsThread10.start();
        	
        	//lifeandtravel
        	getAccountsThread getAccountsThread11=new getAccountsThread("http://www.anyv.net/index.php/category-29-page-1", 32, accountList,"55ec10f0f386b83d6a40bf02");
        	getAccountsThread11.start();
        	
//        	//other
        	getAccountsThread getAccountsThread12=new getAccountsThread("http://www.anyv.net/index.php/category-51-page-1", 114, accountList,"55ec11012445c076260caadf");
        	getAccountsThread12.start();
        	
        	//other
        	getAccountsThread getAccountsThread13=new getAccountsThread("http://www.anyv.net/index.php/category-24-page-1", 36, accountList,"55ec11012445c076260caadf");
        	getAccountsThread13.start();
        	
//        	//other
        	getAccountsThread getAccountsThread14=new getAccountsThread("http://www.anyv.net/index.php/category-22-page-1", 113, accountList,"55ec11012445c076260caadf");
        	getAccountsThread14.start();
//        	
//        	//other
        	getAccountsThread getAccountsThread15=new getAccountsThread("http://www.anyv.net/index.php/category-70-page-1", 161, accountList,"55ec11012445c076260caadf");
        	getAccountsThread15.start();
//        	
//        	//other
        	getAccountsThread getAccountsThread16=new getAccountsThread("http://www.anyv.net/index.php/category-80-page-1", 166, accountList,"55ec11012445c076260caadf");
        	getAccountsThread16.start();
        	
        	//other
        	getAccountsThread getAccountsThread17=new getAccountsThread("http://www.anyv.net/index.php/category-1-page-1", 6, accountList,"55ec11012445c076260caadf");
        	getAccountsThread17.start();
        	
        	//other
        	getAccountsThread getAccountsThread18=new getAccountsThread("http://www.anyv.net/index.php/category-18-page-1", 24, accountList,"55ec11012445c076260caadf");
        	getAccountsThread18.start();

    	//	getAccountsLink(urlString,pageSize,accountList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accountList;
    }

    public List<String[]> getAccountsLink(String url,int pageSize,List<String[]> lists) throws Exception{
    	List<String[]> accountList=lists;
    	int m=1;
    	for(int j=1;j<pageSize;j++){
    		url=url.substring(0, url.length()-1);
    		url=url+j;
    	WebClient wc=new WebClient();
		URL link=new URL(url); 
		WebRequest request=new WebRequest(link); 
		request.setCharset("UTF-8");
		 request.getAdditionalHeaders().clear();
         request.setAdditionalHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
         request.setAdditionalHeader("Cookie", "CXID=98D12A8959E9158BE94713533C290FA0; SUID=66605F6520168B0A5509220F000DE247; IPLOC=CN3100; usid=; ABTEST=0|1437013184|v1; weixinIndexVisited=1; ld=alllllllll2q$a6wlllllVQomyklllllN5NZ7Zllll9lllll9voll5@@@@@@@@@@; SUV=1437120778936331146870538; cid=newssearch2ww; ssuid=8905669144; PHPSESSID=1shu04tf21sqgfucq7fkpr18s4; SUIR=498A0E8EF9FFE00E4E933321FA98D705; ad=xQp9SZllll2qXN$blllllVQ7ut6lllllN5NZ7Zllll9lllllVllll5@@@@@@@@@@; SNUID=D91A9D1E696F7123F5F3B7E46A43F77C; wapsogou_qq_nickname=; LSTMV=458%2C66; LCLKINT=1096; sct=5");
        
         wc.getCookieManager().setCookiesEnabled(true);
          wc.getOptions().setJavaScriptEnabled(true);
          wc.getOptions().setCssEnabled(false);
          wc.getOptions().setThrowExceptionOnFailingStatusCode(false);
          wc.getOptions().setThrowExceptionOnScriptError(false);
          wc.getOptions().setTimeout(10000);
          HtmlPage page=null;
          page = wc.getPage(request);
          HtmlUnorderedList rootDomDivision=null;
       try{
          List<HtmlUnorderedList> rootDom = (List<HtmlUnorderedList>) page.getByXPath("/html/body/div[5]/div/div[5]/ul");
           rootDomDivision = rootDom.get(0);
       }catch(IndexOutOfBoundsException e){
    	   Thread.sleep(3000);
    	      List<HtmlUnorderedList> rootDom = (List<HtmlUnorderedList>) page.getByXPath("/html/body/div[5]/div/div[5]/ul");
              rootDomDivision = rootDom.get(0);
       }catch(Exception e1){
    	   System.out.println(j+"页***"+"*****"+m+++"****跳出");
    	   continue;
       }
          DomNodeList<DomNode> childs = rootDomDivision.getChildNodes();         	
         	for(int i=0;i<childs.size();i++){
         		try{
         		String[]  array=new String[4];
         		 DomNode dfdString = childs.get(i).getFirstChild();
         		 String linkString=dfdString.toString().split("href=\"")[1].split("\" title=")[0];
         	//	accountList.add(linkString);
         		// System.out.println(linkString+"***"+m++);
         		 
         		  HtmlPage pagesingle=null;
         		 pagesingle = wc.getPage(linkString);
         		DomElement info = pagesingle.getElementById("article_extinfo");
         	String accountNameString=	info.getElementsByTagName("h1").get(0).asText();
          	String accountId=	info.getElementsByTagName("h5").get(0).asText().split(":")[1].split("\n")[0].trim();
          	
          	 List<HtmlParagraph> imageDom = (List<HtmlParagraph>) pagesingle.getByXPath("//*[@id=\"article\"]/p[1]");
             HtmlParagraph imageDome = imageDom.get(0);
             String imgLinkString="";
             if(imageDome.getElementsByTagName("img").size()>0){
           HtmlElement img = imageDome.getElementsByTagName("img").get(0);
            imgLinkString=img.toString().split("src=\"")[1].split("\" borde")[0];
             }else{
            	 continue;
             }
      
           String introString="";
           if(imageDome.getElementsByTagName("span").size()>0){
        	   imageDome.getElementsByTagName("span").get(0).remove();
        	   introString=imageDome.asText().split("：")[1].trim();
           }

           String intro=  imageDome.asText();
           array[0]=accountNameString;
           array[1]=accountId;
           array[2]=imgLinkString;
           array[3]=introString;
           accountList.add(array);
           System.out.println(j+"页***"+array[0]+"*****"+m++);
           array=null;
           pagesingle=null;
         		}catch (Exception e) {
         			  System.out.println(j+"页***"+"*****"+m+++"****异常退出");
					continue;
				}
          	}
         	page=null;
    }
    	return accountList;
    }
    
}
