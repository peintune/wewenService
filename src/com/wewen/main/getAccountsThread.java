package com.wewen.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.gargoylesoftware.htmlunit.html.HtmlUnorderedList;

class getAccountsThread extends Thread{
	String urlString="";
	int pageSize=0;
	List<String[]> listsList=null;
	String typeString="";
	apicloudAccountDB apicloudAccountDB=new apicloudAccountDB();
	public  getAccountsThread (String url,int pageSize,List<String[]> lists,String type){
		this.urlString=url;
		this.pageSize=pageSize;
		this.listsList=lists;
		this.typeString=type;
	}
    public void run(){
    	try {
			getAccounts(urlString,pageSize,listsList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
    
    
    public List<String[]> getAccounts(String url,int pageSize,List<String[]> lists) throws Exception{
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
          if(rootDom.size()>0){
           rootDomDivision = rootDom.get(0);
          }else{
        	  Thread.sleep(3000);
        	   System.out.println(j+"页***"+"*****"+m+++"****sleep 3s **********************");
        	  page = wc.getPage(request);
        	 rootDom = (List<HtmlUnorderedList>) page.getByXPath("/html/body/div[5]/div/div[5]/ul");
        	 if(rootDom.size()>0){
        	 rootDomDivision = rootDom.get(0);
        	 }else{
        		   System.out.println(j+"页***"+"*****"+m+++"****continue;**********************");
        		 continue;
        	 }
          }
       }catch(IndexOutOfBoundsException e){
    	   e.printStackTrace();
    	   System.out.println(j+"页***"+"*****"+m+++"****跳出");
    	   continue;
       }
          DomNodeList<DomNode> childs = rootDomDivision.getChildNodes();         	
         	for(int i=0;i<childs.size();i++){
         		try{
         		String[]  array=new String[5];
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
           array[4]=typeString;
           
           apicloudAccountDB.insertOneData(accountNameString, introString, typeString, accountId, imgLinkString);
          // accountList.add(array);
           System.out.println(j+"页***"+array[0]+"*****"+m++);
           array=null;
           pagesingle=null;
         		}catch (Exception e) {
         			e.printStackTrace();
         			  System.out.println(j+"页***"+"*****"+m+++"****异常退出");
					continue;
				}
          	}
         	page=null;
    }
    	return accountList;
    }
}

