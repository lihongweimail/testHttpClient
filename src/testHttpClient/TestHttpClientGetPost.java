package testHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TestHttpClientGetPost {
	
public void myGet()
{
	//先将参数放入List，再对参数进行URL编码 
	List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>(); 
	params.add(new BasicNameValuePair("param1", "中国")); 
	params.add(new BasicNameValuePair("param2", "value2")); 
	//对参数编码 
	String param = URLEncodedUtils.format(params, "UTF-8"); 
	//baseUrl 
	String baseUrl = "http://ubs.free4lab.com/php/method.php"; 
	//将URL与参数拼接 
	HttpGet getMethod = new HttpGet(baseUrl + "?" + param); 

	 httpClient = new DefaultHttpClient(); 
	try { 
	HttpResponse response = httpClient.execute(getMethod); //发起GET请求 
	System.out.println("resCode = " + response.getStatusLine().getStatusCode()); //获取响应码 
	System.out.println("result = " + EntityUtils.toString(response.getEntity(), "utf-8"));//获取服务器响应内容 
	} catch (ClientProtocolException e) { 
	// TODO Auto-generated catch block 
	e.printStackTrace(); 
	} catch (IOException e) { 
	// TODO Auto-generated catch block 
	e.printStackTrace(); 
	} 
  
}
	
List <BasicNameValuePair> params;
String baseUrl;
HttpClient httpClient;
public void myPost() {
	
	//和GET方式一样，先将参数放入List 
	params = new LinkedList<BasicNameValuePair>(); 
	params.add(new BasicNameValuePair("param1", "Post方法")); 
	params.add(new BasicNameValuePair("param2", "第二个参数")); 

	try { 
	HttpPost postMethod = new HttpPost(baseUrl); 
	postMethod.setEntity(new UrlEncodedFormEntity(params, "utf-8")); //将参数填入POST Entity中 

	HttpResponse response = httpClient.execute(postMethod); //执行POST方法 
	System.out.println("resCode = " + response.getStatusLine().getStatusCode()); //获取响应码 
	System.out.println( "result = " + EntityUtils.toString(response.getEntity(), "utf-8")); //获取响应内容 

	} catch (UnsupportedEncodingException e) { 
	// TODO Auto-generated catch block 
	e.printStackTrace(); 
	} catch (ClientProtocolException e) { 
	// TODO Auto-generated catch block 
	e.printStackTrace(); 
	} catch (IOException e) { 
	// TODO Auto-generated catch block 
	e.printStackTrace(); 
	} 
}
	
	
}
