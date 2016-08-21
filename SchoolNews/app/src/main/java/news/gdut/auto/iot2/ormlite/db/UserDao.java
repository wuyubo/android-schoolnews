package news.gdut.auto.iot2.ormlite.db;

import java.net.URLConnection;
import java.sql.SQLException;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import news.gdut.auto.iot2.ormlite.bean.User;

public class UserDao
{
	private Context context;
	private Dao<User, Integer> userDaoOpe;
	private DatabaseHelper helper;
	private static UserDao instence;

	public static UserDao getInstence(Context context){
		if(instence == null){
			instence = new UserDao(context);
		}
		return instence;
	}

	public UserDao(Context context)
	{
		this.context = context;
		try
		{
			helper = DatabaseHelper.getHelper(context);
			userDaoOpe = helper.getUserDao();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}



	/**
	 * 增加一个用户
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void add(User user) 
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
			userDaoOpe.create(user);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

	public User getById(int id)
	{
		try
		{
			return userDaoOpe.queryForId(id);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
