package news.gdut.auto.iot2.ormlite.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import news.gdut.auto.iot2.ormlite.bean.Department;
import news.gdut.auto.iot2.ormlite.bean.School;
import news.gdut.auto.iot2.ormlite.bean.User;

/**
 * Created by Administrator on 2016/5/15.
 */
public class DepartmentDao {
    private Context context;
    private Dao<Department, Integer> departmentDaoOpe;
    private DatabaseHelper helper;
    private static DepartmentDao instence;

    public static DepartmentDao getInstence(Context context){
        if(instence == null){
            instence = new DepartmentDao(context);
        }
        return instence;
    }

    public DepartmentDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            departmentDaoOpe = helper.getDepartmentDao();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 增加一个用户
     *
     * @param department
     * @throws SQLException
     */
    public void add(Department department)
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
            departmentDaoOpe.create(department);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public Department getById(int id)
    {
        try
        {
            return departmentDaoOpe.queryForId(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public Department getByName(String name)
    {
        List<Department> departmentList = null;
        try
        {
            departmentList = departmentDaoOpe.queryBuilder().where().eq("name", name).query();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        if(!departmentList.isEmpty()){
            return departmentList.get(0);
        }
        return null;
    }
    public List<Department> getBySchool(School school){
        List<Department> departmentList = null;

        try
        {
            departmentList = departmentDaoOpe.queryBuilder().where().eq("school_id", school.getId()).query();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        if(!departmentList.isEmpty()){
            return departmentList;
        }
        return null;
    }
}
