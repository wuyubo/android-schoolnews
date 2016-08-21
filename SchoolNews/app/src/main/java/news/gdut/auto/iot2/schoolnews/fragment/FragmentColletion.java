package news.gdut.auto.iot2.schoolnews.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import news.gdut.auto.iot2.ormlite.bean.Collection;
import news.gdut.auto.iot2.ormlite.bean.NewsInform;
import news.gdut.auto.iot2.ormlite.db.DataBaseOpt;
import news.gdut.auto.iot2.schoolnews.GlobalData;
import news.gdut.auto.iot2.Activity.NewsDetailsActivity;
import news.gdut.auto.iot2.schoolnews.R;
import news.gdut.auto.iot2.schoolnews.adapter.NewsListViewAdapter;
import news.gdut.auto.iot2.schoolnews.domain.News;


public class FragmentColletion extends android.app.Fragment implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener {

    private Activity activity;
    private NewsListViewAdapter adapter;
    private ListView listView;
    private boolean isLoading = true;
    private int page = 0;
    private DataBaseOpt dbOpt;
    private View mFooterView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_colletion, container, false);
        this.activity = getActivity();
        mFooterView = View.inflate(activity, R.layout.foot, null);
        return view;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {//判断fragment是否显示。这里写从服务器拿数据的方法就可以了。
            NewsUpdate();
        }
    }

    @Override
    public void onResume() {
        NewsUpdate();
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewInit();

    }

    //ListView init
    private void listViewInit(){
        listView = (ListView)activity.findViewById(R.id.fragment_news_listviewc);
        adapter = new NewsListViewAdapter(activity, null);
        listView.addFooterView(mFooterView);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);
        new MyAsyncTaskGetNews().execute(page);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    /**
     *滑动到底时自动加载更多
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if(totalItemCount <= firstVisibleItem+visibleItemCount+1&&isLoading==false){

            new MyAsyncTaskGetNews().execute(page);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if(view.getId()!=R.id.foot_view){
            Intent intent = new Intent(activity, NewsDetailsActivity.class);
            intent.putExtra("url", adapter.getNewss().get(position).getUrl());
            startActivity(intent);
        }
    }

    /**
     * 异步获取新闻列表集合
     * @author linpeng123l
     *
     */
    public class MyAsyncTaskGetNews extends AsyncTask<Integer, String, List<News>> {
        @Override
        protected List<News> doInBackground(Integer... pages) {
            List<News> newss = getNews(pages[0]);
            return newss;
        }
        @Override
        protected void onPostExecute(List<News> newss) {
            if(newss.size()>0){
                adapter.addNews(newss);
                adapter.notifyDataSetChanged();
                page++;
            }
            isLoading = false;
        }
    }
    private void NewsUpdate(){
        List<News> newss = getNews(0);
        adapter.updateNews(newss);
        adapter.notifyDataSetChanged();
    }
    private List<News> getNews(int showpage){
        int i, skip, take;
        List<News> newss = new ArrayList<News>();
        List<Collection> collectionList;
        skip = showpage*5;
        take = 5;
        i = 0;
        collectionList = dbOpt.getColletionByUser(GlobalData.currentUser);
        if(collectionList == null){
            return newss;
        }
        for(Collection collection : collectionList){
            if(i++ < skip && showpage!= -1) {
                continue;
            }
            NewsInform newsInform = collection.getNewsInform();
            String date = newsInform.gettime();

            String department = newsInform.getdepartment().getName();

            String detail = newsInform.getcontent();
            if(detail.length() > GlobalData.newsContextShow){
                detail = detail.substring(0,GlobalData.newsContextShow-6);
                detail += "......";
            }
            newss.add(new News(newsInform.gettitle(), newsInform.getsrc_url(),
                    newsInform.getimg_url(), detail,date, department));
            if(i >= take+skip && showpage != -1){
                break;
            }
        }
        setFooterView(collectionList.size());
        return  newss;
    }
    private void setFooterView(int rows){
        if(rows < GlobalData.listViewShowRowsCount){
            if(listView.getFooterViewsCount() > 0) {
                listView.removeFooterView(mFooterView);
            }
        }
        else {
            if(listView.getFooterViewsCount() == 0) {
                listView.addFooterView(mFooterView);
            }
        }
    }
}
