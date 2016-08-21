package news.gdut.auto.iot2.schoolnews.netservice;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import news.gdut.auto.iot2.ormlite.bean.Department;
import news.gdut.auto.iot2.ormlite.bean.School;
import news.gdut.auto.iot2.ormlite.db.DataBaseOpt;

import news.gdut.auto.iot2.schoolnews.util.FileUtil;

public class GetNewsService extends NetService{
	private  DataBaseOpt dbOpt;
	private Context mcontext;
	private final static String newsUrl = "http://gdutnews.gdut.edu.cn/";
	private final static String HomeJsp = "liebiaotupian.jsp?";
	private final static String ContextJsp = "neirongye.jsp?";
	private static School school;
	private int tatolPage;

	public GetNewsService(Context context){
		mcontext = context;
		dbOpt = new DataBaseOpt(mcontext);
		tatolPage = 3;
	}
	public Boolean getNewsByPage( int page){
		String[] strtemp;
		try{
			Document document = getDocumentByUrl(newsUrl+HomeJsp+"&PAGENUM="+page+"&urltype=tree.TreeTempUrl&wbtreeid=1013");
			if(document == null) return false;
			Elements ul = document.select(".listtextul");
			Document divcontions = Jsoup.parse(ul.toString());
			Elements element = divcontions.getElementsByTag("li");
			for(Element links : element)
			{
				String title = links.getElementsByTag("a").text();
				String link   = links.select("a").attr("href").replace("/", "").trim();
				String url  = school.geturl()+ContextJsp+link;
				String date = links.getElementsByTag("span").text();
				if(title == null || link==null || url==null || date==null){
					System.out.println("-----------------------------网络故障------------------------------");
					continue;
				}
				if(title.isEmpty() || link.isEmpty() || url.isEmpty() || date.isEmpty()){
					System.out.println("-----------------------------网络故障------------------------------");
					continue;
				}
				strtemp = date.split("\\[");
				if(strtemp.length == 2){
					date = strtemp[1].split("\\]")[0];
				}
//				date = date.split("\\[")[1].split("\\]")[0];
				System.out.println("------------------"+date+"---------------");
				if(!dbOpt.isNewExistByUrl(url)){
					Document cdocument = getDocumentByUrl(url);
					String detail = cdocument.select("#ctl00_ContentPlaceHolder1_tbxIntro").text();
					String depatment = cdocument.select("#ctl00_ContentPlaceHolder1_tbxCopyFrom").text();
					strtemp = depatment.split("：");
					if(strtemp.length == 2){
						depatment = strtemp[1];
					}
					Department dp = dbOpt.getDepartmentByNameOrCreate(depatment, school);
//					depatment = depatment.split("：")[1];
					System.out.println("------------------"+depatment+"---------------");
					dbOpt.addNewsInform(title,"normal", url, "..", detail, true, date, school,  dp);
				}
			}
		}catch(Exception e){
			FileUtil.addFile(e.getStackTrace().toString());
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public void StartService(School _school){
		school =_school;

		new Thread(){
			@Override
			public void run() {


				while (true){

					for (int i = 0; i < tatolPage; i++) {
						System.out.println("-------------getNews=---------------------------");
						if(!getNewsByPage(i+1)) break;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					tatolPage=1;

					try {
						Thread.sleep(360000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(){
			@Override
			public void run() {
				while (true) {
					System.out.println("-----------------service----------------------------------");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
