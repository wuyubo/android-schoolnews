package news.gdut.auto.iot2.ormlite.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import news.gdut.auto.iot2.ormlite.bean.Major;
import news.gdut.auto.iot2.ormlite.bean.School;

/**
 * Created by Administrator on 2016/5/15.
 */
public class MajorDao {
    private Context context;
    private Dao<Major, Integer> majorDaoOpe;
    private DatabaseHelper helper;
    private static MajorDao instence;

    public static MajorDao getInstence(Context context){
        if(instence == null){
            instence = new MajorDao(context);
        }
        return instence;
    }

    public MajorDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            majorDaoOpe = helper.getMajorDao();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 增加一个用户
     *
     * @param major
     * @throws SQLException
     */
    public void add(Major major)
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
            majorDaoOpe.create(major);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public Major getById(int id)
    {
        try
        {
            return majorDaoOpe.queryForId(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
