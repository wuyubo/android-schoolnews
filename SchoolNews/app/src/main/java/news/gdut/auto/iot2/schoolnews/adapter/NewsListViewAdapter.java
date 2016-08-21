package news.gdut.auto.iot2.schoolnews.adapter;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import news.gdut.auto.iot2.schoolnews.R;
import news.gdut.auto.iot2.schoolnews.domain.News;
import news.gdut.auto.iot2.schoolnews.netutil.GetBitmapUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsListViewAdapter extends BaseAdapter {

	private List<News> newss;
	private Context context;
	private Map<String, SoftReference<Bitmap>> map = new HashMap<String, SoftReference<Bitmap>>();

	public NewsListViewAdapter(Context context,List<News> news) {
		super();
		this.context = context;
		if(newss==null){
			newss = new ArrayList<News>();
		}else{
			this.newss = news;
		}
	}

	public void addNews(List<News> newNewss){
		newss.addAll(newNewss);
	}
	public void updateNews(List<News> newNewss){
		newss.clear();
		newss.addAll(newNewss);
	}
	public List<News> getNewss() {
		return newss;
	}

	@Override
	public int getCount() {
		return newss.size();
	}

	@Override
	public Object getItem(int position) {
		return newss.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		News news = newss.get(position);
		if(convertView == null){
			convertView = View.inflate(context,R.layout.listview_fragment_news, null);
		}
		((TextView)convertView.findViewById(R.id.fragment_news_listview_title)).setText(news.getTitle());
		((TextView)convertView.findViewById(R.id.fragment_news_listview_content)).setText(news.getSource());
		((TextView)convertView.findViewById(R.id.fragment_news_listview_time)).setText(news.getDate());
		((TextView)convertView.findViewById(R.id.fragment_news_listview_department)).setText(news.getDepartment());

		return convertView;
	}

}
