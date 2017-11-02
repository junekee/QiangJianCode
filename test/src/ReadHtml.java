import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ReadHtml {
	  /** 
     * 发起http get请求获取网页源代码 
     * @param requestUrl     String    请求地址
     * @return                 String    该地址返回的html字符串
     */  
	private static Map htmlMap = new HashMap();
	private static  Map downMap = new HashMap();
	private static String httpRequest(String requestUrl) {  
        
        StringBuffer buffer = null;  
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = null;
        HttpURLConnection httpUrlConn = null;
  
        try {  
            // 建立get请求
            URL url = new URL(requestUrl);  
            httpUrlConn = (HttpURLConnection) url.openConnection();  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setRequestMethod("GET");  
  
            // 获取输入流  
            inputStream = httpUrlConn.getInputStream();  
            inputStreamReader = new InputStreamReader(inputStream, "gbk");  
            bufferedReader = new BufferedReader(inputStreamReader);  
  
            // 从输入流读取结果
            buffer = new StringBuffer();  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  finally {
            // 释放资源
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStreamReader != null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(httpUrlConn != null){
                httpUrlConn.disconnect();  
            }
        }
//        System.out.println("htm:"+buffer.toString());
        return buffer.toString();  
    }  
  
    /** 
     * 过滤掉html字符串中无用的信息
     * @param html    String    html字符串
     * @return         String    有用的数据
     */ 
    private static String htmlFiter(String html) {  
        
        StringBuffer buffer = new StringBuffer();  
        String str1 = "";
        String str2 = "";
        buffer.append("今天:");
        
        // 取出有用的范围
        Pattern p = Pattern.compile("(.*)(<li class=\'dn on\' data-dn=\'7d1\'>)(.*?)(</li>)(.*)");  
        Matcher m = p.matcher(html);  
        if (m.matches()) {  
            str1 = m.group(3);
            // 匹配日期，注：日期被包含在<h2> 和 </h2>中
            p = Pattern.compile("(.*)(<h2>)(.*?)(</h2>)(.*)");
            m = p.matcher(str1);
            if(m.matches()){
                str2 = m.group(3);
                buffer.append(str2);
                buffer.append("\n天气：");
            }
            // 匹配天气，注：天气被包含在<p class="wea" title="..."> 和 </p>中
            p = Pattern.compile("(.*)(<p class=\"wea\" title=)(.*?)(>)(.*?)(</p>)(.*)");
            m = p.matcher(str1);
            if(m.matches()){
                str2 = m.group(5);
                buffer.append(str2);
                buffer.append("\n温度：");
            }
            // 匹配温度，注：温度被包含在<p class=\"tem tem2\"> <span> 和 </span><i>中
            p = Pattern.compile("(.*)(<p class=\"tem tem2\"> <span>)(.*?)(</span><i>)(.*)");
            m = p.matcher(str1);
            if(m.matches()){
                str2 = m.group(3);
                buffer.append(str2);
                buffer.append("°~");
            }
            p = Pattern.compile("(.*)(<p class=\"tem tem1\"> <span>)(.*?)(</span><i>)(.*)");
            m = p.matcher(str1);
            if(m.matches()){
                str2 = m.group(3);
                buffer.append(str2);
                buffer.append("°\n风力：");
            }
            // 匹配风，注：<i> 和 </i> 中
            p = Pattern.compile("(.*)(<i>)(.*?)(</i>)(.*)");
            m = p.matcher(str1);
            if(m.matches()){
                str2 = m.group(3);
                buffer.append(str2);
            }
        }  
        return buffer.toString();
    }
    /** 
     * 过滤掉html字符串中无用的信息
     * @param html    String    html字符串
     * @return         String    有用的数据
     */ 
    public static void htmlFiter3y( String htmlurl,String name) {  
//    	System.out.println("htmlFiter3y:"+htmlurl);
    	String htmls= "";
    	if("".equals(htmlurl)){
    		htmls= httpRequest("http://3y.uu456.com/");
    	}else{
    		htmls= httpRequest(htmlurl);
    	}
    	StringBuffer buffer = new StringBuffer();  
    	String str1 = "";
    	String str2 = "";
//    	buffer.append("今天:");
    	if(htmls.contains("go2(")){
//    		downMap.put(name, "n");
//    		Thread t = new Thread(new Runnable(){
//    		    public void run(){
    		    	try {
    		    		Pattern p = Pattern.compile("go2((.*?))\"");  //    "q\\#(.*?)\\#q"
    		        	Matcher m = p.matcher(htmls);  
    		        	
    		        	while (m.find()) {
    		        		str1 = m.group(1);
    		    			System.out.println("doc:"+str1);
    		        	}
//						downLoadFromUrl( htmlurl,UUID.randomUUID().toString().replaceAll("-", "") ,"H:\\3y");
					} catch (Exception e) {
						 e.printStackTrace();
					}
//    		}
//    		}); 
//    		t.start();
    	}
    	// 取出有用的范围
//    	Pattern p = Pattern.compile("\\s\"*(.)html\"");  
    	Pattern p = Pattern.compile("href=\"b(.*?)html\"");  //    "q\\#(.*?)\\#q"
    	Matcher m = p.matcher(htmls);  
    	while (m.find()) {
			str1 = m.group(1);
//			System.out.println("b"+str1+"html");
//			buffer.append(str1);
//			htmlMap.remove("b"+str1+"html");
//			htmlMap.put("b"+str1+"html","n");
			//htmlFiter3y("http://3y.uu456.com/"+"b"+str1+"html", "b"+str1+"html");
			new RunRad("http://3y.uu456.com/"+"b"+str1+"html", "b"+str1+"html").start();
		}  
//    	return "";
    }
    
    /** 
     *  对以上两个方法进行封装。
     * @return 
     */  
    public static String getTodayTemperatureInfo() {  
        // 调用第一个方法，获取html字符串
        String html = httpRequest("http://3y.uu456.com/");  
        System.out.println(html);
        // 调用第二个方法，过滤掉无用的信息
        String result = htmlFiter(html);  
        
        return result;  
    }  
    public static void get3y_uu456_com(){
//    	   String html = httpRequest("http://3y.uu456.com/");  
//           System.out.println(html);
           
    	  htmlFiter3y("","");
         
    }
    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void  downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
                //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();  
        //获取自己数组
        byte[] getData = readInputStream(inputStream);    

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);    
        FileOutputStream fos = new FileOutputStream(file);     
        fos.write(getData); 
        if(fos!=null){
            fos.close();  
        }
        if(inputStream!=null){
            inputStream.close();
        }


        System.out.println("info:"+url+" download success"); 

    }



    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        while((len = inputStream.read(buffer)) != -1) {  
            bos.write(buffer, 0, len);  
        }  
        bos.close();  
        return bos.toByteArray();  
    }  

//    public static void main(String[] args) {
//        try{
//            downLoadFromUrl("http://101.95.48.97:8005/res/upload/interface/apptutorials/manualstypeico/6f83ce8f-0da5-49b3-bac8-fd5fc67d2725.png",
//                    "百度.jpg","d:/resource/images/diaodiao/country/");
//        }catch (Exception e) {
//            // TODO: handle exception
//        }
//    }
  
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {  
//        String info = getTodayTemperatureInfo();
          get3y_uu456_com();
          
//        System.out.println(htmlMap.size());
//        for(Object obj : htmlMap.keySet()){
//        	if(obj != null){
//        		
//        		htmlFiter3y("http://3y.uu456.com/"+obj,obj.toString());
//        	}
//        }
        

//        Iterator<String> iter = htmlMap.keySet().iterator();
//
//        while (iter.hasNext()) {
//
//           String   key = iter.next();
//           iter.remove();
//
////            value = map.get(key);
//            htmlFiter3y("http://3y.uu456.com/"+key,key);
//        }
        
    }

 
}
class RunRad extends Thread{
	private String htmlutl;
	private String names ;
	public RunRad(String htmlutl,String names){
		this.htmlutl = htmlutl;
		this.names = names;
	}
	@Override
	public synchronized void run() {
		try {
			this.sleep(1000*3);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		ReadHtml.htmlFiter3y(htmlutl,names);
	}
	public String getHtmlutl() {
		return htmlutl;
	}
	public void setHtmlutl(String htmlutl) {
		this.htmlutl = htmlutl;
	}
	
	public void setNames(String names) {
		this.names = names;
	}
	
}
