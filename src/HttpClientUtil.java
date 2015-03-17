import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
 
 
@SuppressWarnings("deprecation")
public class HttpClientUtil {
 
//	嗯，把她写在util工具类中，到时候如果要发送https请求时，把原本get、post请求中
//    CloseableHttpClient httpclient = HttpClients.createDefault();创建实例的方法替换成：
//    CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();接下来该怎么写的还是按原样写。
	
	
    public static CloseableHttpClient createSSLClientDefault(){
 
        try {
 
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
 
                //信任所有
 
                public boolean isTrusted(X509Certificate[] chain,
 
                        String authType) throws CertificateException {
 
                    return true;
 
                }
 
            }).build();
 
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
 
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
 
        } catch (KeyManagementException e) {
 
            e.printStackTrace();
 
        } catch (NoSuchAlgorithmException e) {
 
            e.printStackTrace();
 
        } catch (KeyStoreException e) {
 
            e.printStackTrace();
 
        }
 
        return  HttpClients.createDefault();
 
    }
 
}