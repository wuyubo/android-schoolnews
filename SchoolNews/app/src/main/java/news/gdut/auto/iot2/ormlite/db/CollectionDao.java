package news.gdut.auto.iot2.ormlite.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

import news.gdut.auto.iot2.ormlite.bean.Collection;
import news.gdut.auto.iot2.ormlite.bean.NewsInform;
import news.gdut.auto.iot2.ormlite.bean.User;
import news.gdut.auto.iot2.schoolnews.domain.News;

/**
 * Created by Administrator on 2016/5/15.
 */
public class CollectionDao {
    private Context context;
    private Dao<Collection, Integer> collectionDaoOpe;
    private DatabaseHelper helper;
    private static CollectionDao instence;

    public static CollectionDao getInstence(Context context){
        if(instence == null){
            instence = new CollectionDao(context);
        }
        return instence;
    }

    public CollectionDao(Context context)
    {
        this.context = context;
        try
        {
            helper = DatabaseHelper.getHelper(context);
            collectionDaoOpe = helper.getCollectionDao();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 增加一个用户
     *
     * @param collection
     * @throws SQLException
     */
    public void add(Collection collection)
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
            collectionDaoOpe.create(collection);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public Collection getById(int id)
    {
        try
        {
            return collectionDaoOpe.queryForId(id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public List<Collection> getByUser(User user)
    {
        try
        {
            return collectionDaoOpe.queryBuilder().where().eq("user_id", user).query();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public List<Collection> getByNewsInform(NewsInform newsInform)
    {
        List<Collection> collectionList = null;
        try
        {
            collectionList =  collectionDaoOpe.queryBuilder().where().eq("newsInform_id", newsInform).query();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return collectionList;
    }
    public boolean deleteByUN(User user, NewsInform newsInform){
        try
        {
            DeleteBuilder<Collection, Integer> deleteBuilder  = collectionDaoOpe.deleteBuilder();
            deleteBuilder.where().eq("user_id", user).and().eq("newsInform_id", newsInform);
            deleteBuilder.delete();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
