package news.gdut.auto.iot2.ormlite.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import news.gdut.auto.iot2.ormlite.bean.College;
import news.gdut.auto.iot2.ormlite.bean.School;

/**
 * Created by Administrator on 2016/5/15.
 */
public class CollegeDao {

    private Context context;
    private Dao<College, Integer> collegeDaoOpe;
    private DatabaseHelper helper;
    private static CollegeDao instence;

    public static CollegeDao getInstence(Context context){
        if(instence == null){
            instence = new CollegeDao(context);
        }
        return instence;
    }

    public CollegeDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            collegeDaoOpe = helper.getCollegeDao();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 增加一个学院
     *
     * @param college
     * @throws SQLException
     */
    public void add(College college)
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
            collegeDaoOpe.create(college);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public College getById(int id)
    {
        try
        {
            return collegeDaoOpe.queryForId(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public List<College> getAll()
    {
        List<College> colleges = null;
        try
        {
            colleges =  collegeDaoOpe.queryBuilder().query();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return colleges;
    }

}
