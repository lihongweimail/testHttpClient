
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
 
public class HttpClientTest {
 
    /**
     * get方法
     */
    public void httpGet(String url, boolean ishttps) {  
    	
    	
        CloseableHttpClient httpclient = HttpClients.createDefault(); 
        if (ishttps) {
        	httpclient = HttpClientUtil.createSSLClientDefault();
		}
       
 
        try {  
            // 创建httpget.    
            HttpGet httpget = new HttpGet(url);
             
            System.out.println("executing request " + httpget.getURI());  
            // 执行get请求.    
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {  
                // 获取响应实体    
                HttpEntity entity = response.getEntity(); 
                 
                System.out.println("--------------------------------------");  
                // 打印响应状态    
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    // 打印响应内容长度    
                    System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    System.out.println("Response content: " + EntityUtils.toString(entity));  
                }  
                System.out.println("------------------------------------");  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (ParseException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
     
    /**
     * post方法(表单提交)
     */
    public void httpPostForm(String url, boolean ishttps,List<NameValuePair> formParams) {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        
        if (ishttps) {
        	httpclient = HttpClientUtil.createSSLClientDefault();
		}

        
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);
         
        // 创建参数队列    
//        List<NameValuePair> formParams = new ArrayList<NameValuePair>();  
//        formParams.add(new BasicNameValuePair("username", "admin"));  
//        formParams.add(new BasicNameValuePair("password", "123456"));  
        UrlEncodedFormEntity uefEntity;  
        try {  
            uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");  
            httppost.setEntity(uefEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    System.out.println("--------------------------------------");  
                    System.out.println("Response content: " + EntityUtils.toString(entity, "UTF-8"));  
                    System.out.println("--------------------------------------");  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
    
    public static void main(String[] args) {
    	
      List<NameValuePair> formParams = new ArrayList<NameValuePair>();  
      formParams.add(new BasicNameValuePair("username", "admin"));  
      formParams.add(new BasicNameValuePair("password", "123456"));  
    //GET  "http://www.google.com/cse/api/014132305786414512501/cse/prmarh9sn8k/annotations/"
    //"Authorization: GoogleLogin auth=\"4/MTbmwWh7aakH8FANk0mNcOZsCx67hvpOXxBGpjBsbgc.4kVfCtGD8yoR3oEBd8DOtNBtFg1klwI\"\r\n" ;

      HttpClientTest myhct=new HttpClientTest();
      myhct.httpGet("http://www.google.com/cse/api/014132305786414512501/cse/prmarh9sn8k/annotations/", false);

		
	}
     
}