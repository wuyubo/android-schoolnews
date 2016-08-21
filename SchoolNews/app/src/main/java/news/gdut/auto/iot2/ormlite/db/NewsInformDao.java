package news.gdut.auto.iot2.ormlite.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import news.gdut.auto.iot2.ormlite.bean.NewsInform;
import news.gdut.auto.iot2.ormlite.bean.User;

/**
 * Created by Administrator on 2016/5/15.
 */
public class NewsInformDao {
    private Context context;
    private Dao<NewsInform, Integer> newsInformDaoOpe;
    private DatabaseHelper helper;
    private static NewsInformDao instence;

    public static NewsInformDao getInstence(Context context){
        if(instence == null){
            instence = new NewsInformDao(context);
        }
        return instence;
    }

    public NewsInformDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            newsInformDaoOpe = helper.getNewsInformDao();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 增加一个用户
     *
     * @param newsInform
     * @throws SQLException
     */
    public void add(NewsInform newsInform)
    {
		/*//事务操作
		TransactionManager.callInTransaction(helper.getConnectionSource(),
				new Callable<Void>()
				{

					@Override
					public Void call() throws Exception
					{
						return null;
					}
				});*/
        try
        {
            newsInformDaoOpe.create(newsInform);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public NewsInform getById(int id)
    {
        try
        {
            return newsInformDaoOpe.queryForId(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<NewsInform> getNewsInformList(NewsInformOptions opt){

        if(!opt.isFilter){//select all
            return getListByNoFilter(opt);
        }
        //select with a filter
        if (opt.filterindex == NewsInformOptions.DEPARTMENT) { //filter is department
            return getListByFilterDepartment(opt);
        }
        if (opt.filterindex == NewsInformOptions.ID ) {
            return getListByFilterIdOrTime(opt);
        }
        //filter is ID or TIME
        return getListByFilterNormal(opt);
    }

    private List<NewsInform> getListByNoFilter(NewsInformOptions opt){
        List<NewsInform> newsInformList = null;
        try {
            newsInformList = newsInformDaoOpe.queryBuilder().orderBy(NewsInformOptions.getColumnName(opt.orderindex),
                    opt.ordertype).where().eq("school_id", opt.school).and().like("title",opt.seachString).query();
        }catch (SQLException e){

        }
        return newsInformList;
    }

    private List<NewsInform> getListByFilterDepartment(NewsInformOptions opt){
        List<NewsInform> newsInformList = null;
        try {
            newsInformList = newsInformDaoOpe.queryBuilder().orderBy(NewsInformOptions.getColumnName(opt.orderindex),
                    opt.ordertype).where().eq(NewsInformOptions.getColumnName(opt.filterindex),
                    opt.department).and().eq("school_id",
                    opt.school).and().like("title",opt.seachString).query();
        }catch (SQLException e){

        }
        return newsInformList;
    }
    private List<NewsInform> getListByFilterNormal(NewsInformOptions opt){
        List<NewsInform> newsInformList = null;
        try {
            newsInformList = newsInformDaoOpe.queryBuilder().orderBy(NewsInformOptions.getColumnName(opt.orderindex),
                    opt.ordertype).where().eq(NewsInformOptions.getColumnName(opt.filterindex),
                    opt.getColumnStringValue(opt.filterindex)).and().eq("school_id",
                    opt.school).and().like("title",opt.seachString).query();
        }catch (SQLException e){

        }
        return newsInformList;
    }

    private List<NewsInform> getListByFilterIdOrTime(NewsInformOptions opt){
        List<NewsInform> newsInformList = null;
        try {
            newsInformList = newsInformDaoOpe.queryBuilder().orderBy(NewsInformOptions.getColumnName(opt.orderindex),
                    opt.ordertype).where().eq(NewsInformOptions.getColumnName(opt.filterindex),
                    opt.getColumnIntValue(opt.filterindex)).and().eq("school_id",
                    opt.school).and().like("title",opt.seachString).query();
        }catch (SQLException e){

        }
        return newsInformList;
    }

    public NewsInform getBySrc_url(String src_url){
        List<NewsInform> newsInformList = null;
        try
        {
            newsInformList = newsInformDaoOpe.queryBuilder().where().eq("src_url", src_url).query();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        if(!newsInformList.isEmpty()) {
//            System.out.println("======================"+src_url+"======================");
            return newsInformList.get(0);
        }
        return null;
    }

    public  List<NewsInform> getAllNews(String src_url){
        List<NewsInform> newsInformList = null;
        try
        {
            newsInformList = newsInformDaoOpe.queryForAll();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        if(!newsInformList.isEmpty())
            return newsInformList;
        return null;
    }
}
