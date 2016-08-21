package news.gdut.auto.iot2.schoolnews.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import news.gdut.auto.iot2.ormlite.bean.Department;
import news.gdut.auto.iot2.ormlite.bean.NewsInform;
import news.gdut.auto.iot2.ormlite.db.DataBaseOpt;
import news.gdut.auto.iot2.ormlite.db.NewsInformOptions;
import news.gdut.auto.iot2.schoolnews.GlobalData;
import news.gdut.auto.iot2.schoolnews.R;
import news.gdut.auto.iot2.Activity.NewsDetailsActivity;
import news.gdut.auto.iot2.schoolnews.domain.News;

import news.gdut.auto.iot2.schoolnews.adapter.NewsListViewAdapter;

public class FragmentNews extends Fragment implements OnItemClickListener,OnScrollListener,SearchView.OnQueryTextListener{

	private NewsListViewAdapter adapter;
	private ListView listView;
	private boolean isLoading = true;
	private boolean isSerching = false;
	private Activity activity;
	private int page = 0;
	private SearchView search;
	private Button bt_search;
	private DataBaseOpt dbOpt;
	private NewsInformOptions opt;
	private Spinner  apFilter;
	private Spinner  daFilter;
	private Spinner  spOrder;
	private ArrayAdapter<String> apAdapter;
	private List<String> apStrs;
	private ArrayAdapter<String> daAdapter;
	private List<String> daStrs;
	private ArrayAdapter<String>  orderAdapter;
	private List<String> orderStrs;
	private View mFooterView;
	private mOnClickListener onclickListener;
	private static final String [] orderTypeStr = {
			"默认排序","日期降序","日期升序","发布单位"
	};
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_news, container, false);
		this.activity = getActivity();
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		rootInit();
		dbOptInit();
		listViewInit();
		searchInit();
		spFilterInit();
		spOrderInit();
	}
	//Init data
	private void rootInit(){
		onclickListener = new mOnClickListener();
		mFooterView = View.inflate(activity, R.layout.foot, null);
	}
	//database option init
	private void dbOptInit(){
		opt = new NewsInformOptions();
		dbOpt = new DataBaseOpt(activity);
		opt.seachString = "%";
		opt.orderindex = NewsInformOptions.TIME;
		opt.ordertype = false;
		opt.isFilter = false;
		opt.school = GlobalData.currentSchool;
		opt.skip_take_index = NewsInformOptions.ID;
	}

	//ListView init
	private void listViewInit(){
		listView = (ListView)activity.findViewById(R.id.fragment_news_listview);
		adapter = new NewsListViewAdapter(activity, null);
		listView.addFooterView(mFooterView);

		listView.setAdapter(adapter);
		listView.setOnScrollListener(this);
		listView.setOnItemClickListener(this);
		new MyAsyncTaskGetNews().execute(page);
	}
	//Search Options init
	private void searchInit(){
		search = (SearchView)activity.findViewById(R.id.searchView);
		search.clearFocus();
		search.setIconifiedByDefault(false);
		search.setOnQueryTextListener(this);
		search.setSubmitButtonEnabled(true);
		search.setQueryHint("请输入关键字");
		bt_search = (Button) activity.findViewById(R.id.bt_search);
		bt_search.setOnClickListener(onclickListener);
	}

	//spinner filter init
	private void spFilterInit(){
		apFilter = (Spinner) activity.findViewById(R.id.spinner_department);
		daFilter = (Spinner) activity.findViewById(R.id.spinner_date);
		apStrs = new ArrayList<String>();
		daStrs = new ArrayList<String>();
		apStrs.add("发布单位");
		daStrs.add("发布日期");
		apAdapter = new ArrayAdapter<String>(activity,R.layout.spinner_item_normal,apStrs);
		daAdapter = new ArrayAdapter<String>(activity,R.layout.spinner_item_normal,daStrs);
		apAdapter.setDropDownViewResource(R.layout.spinner_item_normal);
		daAdapter.setDropDownViewResource(R.layout.spinner_item_normal);
		apFilter.setAdapter(apAdapter);
		daFilter.setAdapter(daAdapter);
		apFilter.setOnItemSelectedListener(new SpinnerItemSelectListener(1));
		daFilter.setOnItemSelectedListener(new SpinnerItemSelectListener(2));
		sourceFilter();
	}
	private void sourceFilter(){
		List<Department> departmentList = dbOpt.getDepartmentAllBySchool(GlobalData.currentSchool);
		if(departmentList == null || departmentList.isEmpty()){
			return;
		}
		List<News> newsList = getNews(-1);

		for(Department department:departmentList){
			if(department.getName().isEmpty()) continue;
			apStrs.add(department.getName());
		}
		String date = "";
		for(News news:newsList){
			if( date.equals(news.getDate()) || news.getDate().isEmpty()) continue;
			date = news.getDate();
			daStrs.add(date);
		}

		apAdapter.setNotifyOnChange(true);
		daAdapter.setNotifyOnChange(true);
	}
	//spinner order type option init
	private void spOrderInit(){
		spOrder = (Spinner) activity.findViewById(R.id.spinner_order);
		orderStrs = new ArrayList<String>();
		for(int i=0; i<orderTypeStr.length; i++){
			orderStrs.add(orderTypeStr[i]);
		}
		orderAdapter = new ArrayAdapter<String>(activity,R.layout.spinner_item_normal,orderStrs);
		orderAdapter.setDropDownViewResource(R.layout.spinner_item_normal);
		spOrder.setAdapter(orderAdapter);
		spOrder.setOnItemSelectedListener(new SpinnerItemSelectListener(3));
	}

	///keyBoard action
	private void keyBoardHide(){
		//隐藏软键盘
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	///ListView action
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(view.getId()!=R.id.foot_view){
			Intent intent = new Intent(activity, NewsDetailsActivity.class);
			intent.putExtra("url", adapter.getNewss().get(position).getUrl());
			startActivity(intent);
		}
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
			isLoading = true;
			new MyAsyncTaskGetNews().execute(page);
		}
	}
	///////SearchView action
	@Override
	public boolean onQueryTextSubmit(String query) {
		NewsUpdate();
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		opt.seachString = "%"+newText+"%";

		if (newText.isEmpty()) {
			NewsUpdate();
		}

		return false;
	}

	private void NewsUpdate(){
		search.clearFocus();
		List<News> newss = getNews(0);
		adapter.updateNews(newss);
		adapter.notifyDataSetChanged();
	}

	private List<News> getNews(int showpage){
		int i, skip, take;
		List<News> newss = new ArrayList<News>();
		List<NewsInform> informList;
		skip = showpage*5;
		take = 5;
		i = 0;
		informList = dbOpt.getNewsInformList(opt);
		if(informList == null || informList.isEmpty()) {
			newss.add(new News("没有新闻，请连接网络重启", "",
					"", "....","今天", "..."));
			return newss;
		}
		for(NewsInform newsInform : informList){
			if(i++ < skip && showpage!= -1) {
				continue;
			}
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
		setFooterView(informList.size());
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
	//Spinner actions
	private class SpinnerItemSelectListener implements AdapterView.OnItemSelectedListener {

		private int spinnerType;
		public SpinnerItemSelectListener(int Spinner){
			spinnerType = Spinner;
		}
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			String str = parent.getItemAtPosition(position).toString();
			switch (spinnerType){
				case 1:
					if(str == "发布单位"){
						opt.isFilter = false;
					}
					else {
						opt.isFilter = true;
						opt.filterindex = NewsInformOptions.DEPARTMENT;
						opt.department = dbOpt.getDepartmentByNameOrCreate(str, GlobalData.currentSchool);
					}
					break;
				case 2:
					if(str == "发布日期"){
						opt.isFilter = false;
					}
					else {
						opt.isFilter = true;
						opt.filterindex = NewsInformOptions.TIME;
						opt.time = str;
					}
					break;
				case 3:
					if(str == "默认排序"){
						opt.orderindex = NewsInformOptions.TIME;
						opt.ordertype = false;
					}
					else if(str == "日期降序"){
						opt.orderindex = NewsInformOptions.TIME;
						opt.ordertype = false;
					}
					else if(str == "日期升序"){
						opt.orderindex = NewsInformOptions.TIME;
						opt.ordertype = true;
					}
					else if(str == "发布单位"){
						opt.orderindex = NewsInformOptions.DEPARTMENT;
						opt.ordertype = true;
					}
					break;
			}
			NewsUpdate();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}
	}

	private class mOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			switch (view.getId())
			{
				case R.id.bt_search:
					NewsUpdate();
					isSerching = true;
			}
			sourceFilter();
		}
	}
	/**
	 * 异步获取新闻列表集合
	 * @author linpeng123l
	 *
	 */
	public class MyAsyncTaskGetNews extends AsyncTask<Integer, String, List<News>>{
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

}
