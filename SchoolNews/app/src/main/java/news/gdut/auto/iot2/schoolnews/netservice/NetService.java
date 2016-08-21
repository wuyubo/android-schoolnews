package news.gdut.auto.iot2.schoolnews.netservice;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import news.gdut.auto.iot2.schoolnews.netutil.NetUtil;

/**
 * 获取新闻服务类的父类，提供一些方法获取数据
 * @author linpeng123l
 *
 */
public class NetService extends Service {
	
	public final static  String UESRAGENT_PHONE = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A405 Safari/8536.25";

	/**通过URL请求得到json数组ͨ��URL����õ�json����
	 * @param url
	 * @return
	 */
	public static JSONObject[] getJsonObjectsByUrl(String url){
		try {
			String response = NetUtil.postAndGetDaet(url).replace("&quot;", "\'");
			JSONArray jsonArray = new JSONArray(response);
			JSONObject[] jsonObjects = new JSONObject[jsonArray.length()];
			for(int i = 0;i<jsonArray.length();i++){
				jsonObjects[i] = jsonArray.getJSONObject(i);
			}  
			return jsonObjects;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  通过URL请求得到json
	 * @param url
	 * @return
	 */
	public static JSONObject getJsonObjectByUrl(String url){
		try {
			String response = NetUtil.postAndGetDaet(url).replace("&quot;", "\'");
			JSONObject jsonObject = new JSONObject(response);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 通过url请求得到document
	 * @param url
	 * @return
	 */
	public static Document getDocumentByUrl(String url){
		try {
			Document document = Jsoup.connect(url).userAgent(UESRAGENT_PHONE).get();
			return document;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
