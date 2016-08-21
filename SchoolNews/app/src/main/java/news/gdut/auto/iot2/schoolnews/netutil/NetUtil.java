package news.gdut.auto.iot2.schoolnews.netutil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * 网络操作工具类
 * @author iot2
 *
 */
public class NetUtil { 
	public final static  String UESRAGENT_PHONE = "User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A405 Safari/8536.25"; 
	
	public static String postAndGetDaet(String urlString){

		String response=null;
		System.out.println(urlString);
		try{
//			URL _url = new URL(urlString);
//			URLConnection rulConnection = _url.openConnection();
//			// 此处的urlConnection对象实际上是根据URL的
//			// 请求协议(此处是http)生成的URLConnection类
//			// 的子类HttpURLConnection,故此处最好将其转化
//			// 为HttpURLConnection类型的对象,以便用到
//			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
//			// 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
//			// http正文内，因此需要设为true, 默认情况下是false;
//			httpUrlConnection.setDoOutput(true);
//
//			// 设置是否从httpUrlConnection读入，默认情况下是true;
//			 httpUrlConnection.setDoInput(true);
//
//			// Post 请求不能使用缓存
//			 httpUrlConnection.setUseCaches(false);
//
//			// 设定传送的内容类型是可序列化的java对象
//			// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
//			httpUrlConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");

			HttpPost httpPost=new HttpPost(urlString);
			httpPost.setHeader("User-Agent", UESRAGENT_PHONE);
			DefaultHttpClient httpClient=new DefaultHttpClient();
			HttpResponse httpResponse=httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				response=EntityUtils.toString(httpResponse.getEntity());
			}

		}catch (Exception e) {
			System.out.println("error ");
			response="connect_error";
			e.printStackTrace();
		}
		return response;


//		HttpURLConnection conn = null; //连接对象
//		InputStream is = null;
//		String resultData = "";
//		try {
//			URL url = new URL(urlString); //URL对象
//			conn = (HttpURLConnection)url.openConnection(); //使用URL打开一个链接
//			conn.setDoInput(true); //允许输入流，即允许下载
//			conn.setDoOutput(true); //允许输出流，即允许上传
//			conn.setUseCaches(false); //不使用缓冲
//			conn.setRequestMethod("GET"); //使用get请求
//			is = conn.getInputStream();   //获取输入流，此时才真正建立链接
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader bufferReader = new BufferedReader(isr);
//			String inputLine  = "";
//			while((inputLine = bufferReader.readLine()) != null){
//				resultData += inputLine + "\n";
//			}
//
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally{
//			if(is != null){
//				try {
//					is.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			if(conn != null){
//				conn.disconnect();
//			}
//		}
//
//		return resultData;
	}

}
