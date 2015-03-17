package testHttpClient;



import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

public class TestDemo {
	private int port;
	private String host;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	protected ByteArrayInputStream byteStream;
	private int responseCode=-1;
	private String responseMessage="";
	private String serverVersion="";
	private Properties header = new Properties();
	

	
	
	public TestDemo(String host, int port) {
//		想要成为支持HTTPS客户端, 只要在创建Socket的时候如下:	
//		socket = (SSLSocket)((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket(this.host, port);
	
		socket = new Socket();
		this.host = host;
		this.port = port;
	}
	
	
//	GET http://www.google.com/cse/api/<USER_ID>/cse/<CSE_ID>/annotations/
//	Authorization: GoogleLogin auth="IM6F7Cx2fo0TAiwlhNVdSE8Ov8hw6aHV"
//GET  "http://www.google.com/cse/api/014132305786414512501/cse/prmarh9sn8k/annotations/"
//"Authorization: GoogleLogin auth=\"4/MTbmwWh7aakH8FANk0mNcOZsCx67hvpOXxBGpjBsbgc.4kVfCtGD8yoR3oEBd8DOtNBtFg1klwI\"\r\n" ;

//	GET http://www.google.com/cse/api/<USER_ID>/cse/<CSE_ID>
//		Authorization: GoogleLogin auth="IM6F7Cx2fo0TAiwlhNVdSE8Ov8hw6aHV"
	
	

	public BufferedInputStream sendGet() throws IOException
	{
		
		
		String path = "http://www.google.com/cse/api/014132305786414512501/cse/prmarh9sn8k";
		
		SocketAddress dest = new InetSocketAddress(this.host, this.port);
		socket.connect(dest);
		OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream());
		bufferedWriter = new BufferedWriter(streamWriter);
		
		bufferedWriter.write("GET " + path + " HTTP/1.1\r\n");

		//		only find host
//		bufferedWriter.write("Host: " + this.host + "\r\n"); 

		//		to retrieving g cse info. 
//		bufferedWriter.write("Authorization: GoogleLogin auth=\"4/MTbmwWh7aakH8FANk0mNcOZsCx67hvpOXxBGpjBsbgc.4kVfCtGD8yoR3oEBd8DOtNBtFg1klwI\"\r\n");
		
	
		
		
		bufferedWriter.write("\r\n");
		bufferedWriter.flush();
		
		BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
		bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));
		String line = null;
		while((line = bufferedReader.readLine())!= null)
		{
			System.out.println(line);
		}
		bufferedReader.close();
		bufferedWriter.close();
		socket.close();
		
		return streamReader;
	}
	
//	POST http://www.google.com/cse/api/<USER_ID>/cse/<CSE_ID>my_first_cse
//	Content-Type: text/xml
//	Authorization: GoogleLogin auth="IM6F7Cx2fo0TAiwlhNVdSE8Ov8hw6aHV"
//	<CustomSearchEngine id="prmarh9sn8k" creator="014132305786414512501" language="en" >
//	  <Title>Helpseekingfdu</Title>
//	  <Description>Car Helpseeking fdu</Description>
//	  <Context>
//	    <BackgroundLabels>
//	      <Label name="_cse_prmarh9sn8k" mode="FILTER" />
//	      <Label name="_cse_exclude_prmarh9sn8k" mode="ELIMINATE" />
//	    </BackgroundLabels>
//	  </Context>
//	  <LookAndFeel nonprofit="false" />
//	</CustomSearchEngine>
	
	public BufferedInputStream sendPost() throws IOException
	{
		
		String path = "http://www.google.com/cse/api/014132305786414512501/cse/prmarh9sn8k";
		String data = 
//				URLEncoder.encode("Content-Type:", "utf-8") + URLEncoder.encode(" text/xml", "utf-8") + "\r\n"
//				+ "\r\n"
				 URLEncoder.encode("Authorization:", "utf-8") + URLEncoder.encode("GoogleLogin auth=\"IM6F7Cx2fo0TAiwlhNVdSE8Ov8hw6aHV\"", "utf-8")+ "\r\n"
				+ URLEncoder.encode("<CustomSearchEngine id=\"prmarh9sn8k\" creator=\"014132305786414512501\" language=\"en\">", "utf-8") + "\r\n"
				+ URLEncoder.encode("<Title>Helpseekingfdu</Title>", "utf-8") + "\r\n"
				+ URLEncoder.encode("<Description>Car Helpseeking fdu</Description>", "utf-8") + "\r\n"
				+ URLEncoder.encode("<Context>", "utf-8") + "\r\n"
				+ URLEncoder.encode("<BackgroundLabels>", "utf-8") + "\r\n"
				+ URLEncoder.encode("<Label name=\"_cse_prmarh9sn8k\" mode=\"FILTER\" />", "utf-8") + "\r\n"
				+ URLEncoder.encode("<Label name=\"_cse_exclude_prmarh9sn8k\" mode=\"ELIMINATE\" />", "utf-8") + "\r\n"
				+ URLEncoder.encode("</BackgroundLabels>", "utf-8") + "\r\n"
				+ URLEncoder.encode("</Context>", "utf-8") + "\r\n"
				+ URLEncoder.encode("<LookAndFeel nonprofit=\"false\" />", "utf-8") + "\r\n"
				+ URLEncoder.encode("</CustomSearchEngine>", "utf-8")+ "\r\n"
				
				;
		// String data = "name=zhigang_jia";
		SocketAddress dest = new InetSocketAddress(this.host, this.port);
		socket.connect(dest);
		OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream(), "utf-8");
		bufferedWriter = new BufferedWriter(streamWriter);
		
		bufferedWriter.write("POST " + path + " HTTP/1.0\r\n");
//		bufferedWriter.write("Host: " + this.host + "\r\n");
		bufferedWriter.write("Content-Length: "+data.length()+"\r\n");
//		bufferedWriter.write("Content-Type: application/x-www-form-urlencoded\r\n");
		
		bufferedWriter.write("Content-Type: text/xml\r\n");
		bufferedWriter.write("Authorization: GoogleLogin auth=\"IM6F7Cx2fo0TAiwlhNVdSE8Ov8hw6aHV\"\r\n");
		bufferedWriter.write("<CustomSearchEngine id=\"prmarh9sn8k\" creator=\"014132305786414512501\" language=\"en\">\r\n");
		bufferedWriter.write("<Title>Helpseekingfdu</Title>\r\n");
		bufferedWriter.write("<Description>Car Helpseeking fdu</Description>\r\n");
		bufferedWriter.write("<Context>\r\n");
		bufferedWriter.write("<BackgroundLabels>\r\n");
		bufferedWriter.write("<Label name=\"_cse_prmarh9sn8k\" mode=\"FILTER\" />\r\n");
		bufferedWriter.write("<Label name=\"_cse_exclude_prmarh9sn8k\" mode=\"ELIMINATE\" />\r\n");
		bufferedWriter.write("</BackgroundLabels>\r\n");
		bufferedWriter.write("</Context>\r\n");
		bufferedWriter.write("<LookAndFeel nonprofit=\"false\" />\r\n");
		bufferedWriter.write("</CustomSearchEngine>\r\n");
		bufferedWriter.write("\r\n");	
		bufferedWriter.flush();
//		bufferedWriter.write("\r\n");
//		bufferedWriter.flush();
	
		BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
		bufferedReader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));
		String line = null;
		while((line = bufferedReader.readLine())!= null)
		{
			System.out.println(line);
		}
		bufferedReader.close();
		bufferedWriter.close();
		socket.close();
		return streamReader;
	}
	
	
	private byte[] addCapacity(byte rece[]) {
		byte temp[] = new byte[rece.length + 1024];
		System.arraycopy(rece, 0, temp, 0, rece.length);
		return temp;
	}


	/* 接收来自Web服务器的数据 */
	protected void receiveMessage(BufferedInputStream receiver) throws IOException {
		byte data[] = new byte[1024];
		int count = 0;
		int word = -1;
		// 解析第一行
		while ((word = receiver.read()) != -1) {
			if (word == '\r' || word == '\n') {
				word = receiver.read();
				if (word == '\n')
					word = receiver.read();
				break;
			}
			if (count == data.length)
				data = addCapacity(data);
			data[count++] = (byte) word;
		}
		String message = new String(data, 0, count);
		int mark = message.indexOf(32);
		serverVersion = message.substring(0, mark);
		while (mark < message.length() && message.charAt(mark + 1) == 32)
			mark++;
		responseCode = Integer.parseInt(message.substring(mark + 1, mark += 4));
		responseMessage = message.substring(mark, message.length()).trim();
		// 应答状态码和处理请读者添加
		switch (responseCode) {
		case 401:
			 throw new IOException("Authorization failure 认证失败");
		case 403:
			 throw new IOException("Forbidden禁止");
		case 405:
			 throw new IOException("method not allowed");
		case 413:
			 throw new IOException("file to large");

		case 500:
			 throw new IOException("server error");


		case 400:
			throw new IOException("错误请求");
		case 404:
			throw new IOException("url error! NOT find location file");
		case 503:
			throw new IOException("服务器不可用");
		}
		if (word == -1)
			throw new ProtocolException("信息接收异常终止");
		int symbol = -1;
		count = 0;
		// 解析元信息
		while (word != '\r' && word != '\n' && word > -1) {
			if (word == '\t')
				word = 32;
			if (count == data.length)
				data = addCapacity(data);
			data[count++] = (byte) word;
			parseLine: {
				while ((symbol = receiver.read()) > -1) {
					switch (symbol) {
					case '\t':
						symbol = 32;
						break;
					case '\r':
					case '\n':
						word = receiver.read();
						if (symbol == '\r' && word == '\n') {
							word = receiver.read();
							if (word == '\r')
								word = receiver.read();
						}
						if (word == '\r' || word == '\n' || word > 32)
							break parseLine;
						symbol = 32;
						break;
					}
					if (count == data.length)
						data = addCapacity(data);
					data[count++] = (byte) symbol;
				}
				word = -1;
			}
			message = new String(data, 0, count);
			mark = message.indexOf(':');
			String key = null;
			if (mark > 0)
				key = message.substring(0, mark);
			mark++;
			while (mark < message.length() && message.charAt(mark) <= 32)
				mark++;
			String value = message.substring(mark, message.length());
			header.put(key, value);
			count = 0;
		}
		// 获得正文数据
		while ((word = receiver.read()) != -1) {
			if (count == data.length)
				data = addCapacity(data);
			data[count++] = (byte) word;
		}
		if (count > 0)
			byteStream = new ByteArrayInputStream(data, 0, count);
		data = null;
//		closeServer();
	}

	
	public static void main(String[] args)
	{
		
		URL url;
		String host="127.0.0.1";
		int port=80;
		

		
		try {
			url = new URL("http://www.google.com/cse/api/014132305786414512501/cse/prmarh9sn8k");
			  host = url.getHost();
			    port = url.getDefaultPort();

			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			System.out.println("error in construct url");
			e1.printStackTrace();
			
		}
			  
		BufferedInputStream receiver;
		TestDemo td = new TestDemo(host,port);
		try {
//			receiver=td.sendGet(); //send HTTP GET Request
			
			receiver=td.sendPost(); // send HTTP POST Request
			
			td.receiveMessage(receiver);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


