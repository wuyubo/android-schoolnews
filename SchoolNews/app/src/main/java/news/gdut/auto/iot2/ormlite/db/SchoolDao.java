package news.gdut.auto.iot2.ormlite.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import news.gdut.auto.iot2.ormlite.bean.School;
import news.gdut.auto.iot2.ormlite.bean.User;

/**
 * Created by Administrator on 2016/5/15.
 */
public class SchoolDao {
    private Context context;
    private Dao<School, Integer> schoolDaoOpe;
    private DatabaseHelper helper;

    private static SchoolDao instence;

    public static SchoolDao getInstence(Context context){
        if(instence == null){
            instence = new SchoolDao(context);
        }
        return instence;
    }

    public SchoolDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            schoolDaoOpe = helper.getSchoolDao();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 增加一个用户
     *
     * @param school
     * @throws SQLException
     */
    public void add(School school)
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
            schoolDaoOpe.create(school);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public School getById(int id)
    {
        try
        {
            return schoolDaoOpe.queryForId(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public School getByName(String name)
    {
        List<School> schools = null;
        try
        {
            schools = schoolDaoOpe.queryBuilder().where().eq("name",name).query();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        if(schools.isEmpty()){
            return null;
        }
       return schools.get(0);
    }

    public List<School> getAll(){
        List<School> schools = null;
        try
        {
            schools = schoolDaoOpe.queryBuilder().query();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return schools;
    }
}
