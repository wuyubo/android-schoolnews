package news.gdut.auto.iot2.Activity;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import news.gdut.auto.iot2.ormlite.bean.Collection;
import news.gdut.auto.iot2.ormlite.bean.NewsInform;
import news.gdut.auto.iot2.ormlite.db.DataBaseOpt;
import news.gdut.auto.iot2.schoolnews.GlobalData;
import  news.gdut.auto.iot2.schoolnews.R;

public class NewsDetailsActivity extends AppCompatActivity {
	public final static String UESRAGENT_PHONE = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A405 Safari/8536.25";
	private Handler handler = new myHandler();
	private WebView detailNews = null;
	private String GdutUrl = "http://gdutnews.gdut.edu.cn";
	private String detailUrl;
	private NewsInform newsInform = null;
	private DataBaseOpt dbOpt = null;
	private Menu mMenu;
	private boolean isCollection;
	private RelativeLayout isloading;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_details);
		initView();

		Bundle bb = getIntent().getExtras();
		detailUrl = bb.getString("url");
		System.out.println(detailUrl);
		initData();
	}
	private void initView(){
		isloading = (RelativeLayout) findViewById(R.id.activity_news_details_relative_is_loading);
		detailNews = (WebView) findViewById(R.id.detailNews);
		detailNews.setBackgroundColor(0);
		this.getSupportActionBar().setTitle(GlobalData.APP_NAME);
	}
	private void initData(){
		dbOpt =  new DataBaseOpt(this);
		newsInform = dbOpt.getNewsInformBySrcUrl(detailUrl);
		isCollection = checkColletiion();
		Thread t = new getNewDetailThread(detailUrl);
		t.start();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		mMenu = menu;
		if(isCollection){
			mMenu.findItem(R.id.menu_collection).setIcon(R.drawable.icon_collection_press);
		}

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}
	@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.menu_share:
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("text/plain");
						intent.putExtra(Intent.EXTRA_TEXT, GlobalData.APP_NAME+":"+newsInform.gettitle()
								+"\r\n——"+newsInform.getcontent()+"详情见："+detailUrl);
						startActivity(Intent.createChooser(intent, getTitle()));
						break;
			case R.id.menu_collection:
				if(GlobalData.currentUser==null){
					toastShow("请填写个人信息！");
					break;
				}
				if(isCollection){
					deleteColletion();
					isCollection = false;
					mMenu.findItem(R.id.menu_collection).setIcon(R.drawable.icon_collection_normal);
					break;
				}
				mMenu.findItem(R.id.menu_collection).setIcon(R.drawable.icon_collection_press);
				collection();
				isCollection = true;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	public static Document getDocumentByUrl(String url) {

		try {
			Document document = Jsoup.connect(url).userAgent(UESRAGENT_PHONE).get();
			return document;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	class myHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			if(msg.what == 0){
				toastShow("你的网络不给力，请检查你的网络设置");
			}

			detailNews.loadData(msg.obj.toString(),"text/html; charset=UTF-8;",null);
			System.out.println(msg.obj.toString());
			isloading.setVisibility(View.INVISIBLE);
			super.handleMessage(msg);
		}
	}


	class getNewDetailThread extends Thread {
		String url = null;

		public getNewDetailThread(String url) {
			// TODO Auto-generated constructor stub
			this.url = url;
		}

		@Override
		public void run() {

			getNewDetail();
		}
		private void getNewDetail(){
			int tryCount = 0;
			Document document = null;
			while(document == null){
				tryCount++;
				document = getDocumentByUrl(url);
				if(++tryCount >= 3){
					break;
				}
			}
			if(document == null){
				noNetWork();
				return;
			}
			Elements title = document.select(".title");
			Elements time = document.select("#ctl00_ContentPlaceHolder1_tbxUpdateTime");
			Elements from = document.select("#ctl00_ContentPlaceHolder1_tbxCopyFrom");
			Elements autho = document.select("#ctl00_ContentPlaceHolder1_tbxEditor");
			Elements content = document.select("#contentText");
			Elements jpgs = content.select("img[src$=.jpg]"); // 所有 jpg 的图片
			String imgUrl = null;
			for (Element links : jpgs) {
				imgUrl = GdutUrl + links.attr("src").toString();
				links.attr("src", imgUrl);
				links.attr("width", "100%");
			}
			Elements pngs = content.select("img[src$=.png]"); // 所有 jpg 的图片
			for (Element links : pngs) {
				imgUrl = GdutUrl + links.attr("src").toString();
				links.attr("src", imgUrl);
				links.attr("width", "100%");
			}
			String fin =
					title.toString() + //标题
							"时间：" + time.toString() + "<br>"//时间
							+ from.toString() + "<br>"//来源
							+ autho.toString() + "<br>"//作者
							+ content.toString();//正文
			showNewDetail(fin, 1);
		}
		private void noNetWork(){
			String text = "<p>=_=咦，你的网络不给力，请检查你的网络设置。</p>"
							+"<h1>"+newsInform.gettitle()+"</h1>"
							+ "<p>来源："+newsInform.getdepartment().getName()+"</p>"
							+ "<p>时间："+newsInform.gettime()+"</p>"
							+"<p>"+newsInform.getcontent()+"</p>";
			showNewDetail(text, 0);
		}

	}
	private void showNewDetail(String text, int what){
		Message msg = new Message();
		msg.obj=text;
		msg.what = what;
		handler.sendMessage(msg);
	}

	private boolean checkColletiion(){
		List<Collection> collectionList = dbOpt.getColletionByNewsInfom(dbOpt.getNewsInformBySrcUrl(detailUrl));
		if(collectionList == null || collectionList.isEmpty()){
			return false;
		}
		return true;
	}
	private boolean collection(){
		dbOpt.addCollection(null,GlobalData.currentUser, dbOpt.getNewsInformBySrcUrl(detailUrl), "2016-6-24");
		toastShow("收藏成功");
		return true;
	}
	private boolean deleteColletion(){
		if(dbOpt.deleteColletionByUN(GlobalData.currentUser, dbOpt.getNewsInformBySrcUrl(detailUrl))){
			toastShow("取消收藏");
			return true;
		}
		return false;
	}
	private void toastShow(String msg){
		Toast toast = Toast.makeText(NewsDetailsActivity.this, msg, Toast.LENGTH_SHORT);
		toast.show();
	}
}
