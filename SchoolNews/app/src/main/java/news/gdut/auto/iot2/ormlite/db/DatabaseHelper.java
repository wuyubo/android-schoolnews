package news.gdut.auto.iot2.ormlite.db;

import java.io.File;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


import news.gdut.auto.iot2.ormlite.bean.Collection;
import news.gdut.auto.iot2.ormlite.bean.College;
import news.gdut.auto.iot2.ormlite.bean.Department;
import news.gdut.auto.iot2.ormlite.bean.Major;
import news.gdut.auto.iot2.ormlite.bean.NewsInform;
import news.gdut.auto.iot2.ormlite.bean.School;
import news.gdut.auto.iot2.ormlite.bean.User;
import news.gdut.auto.iot2.ormlite.utils.L;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DB_NAME = "my_db.db";
	private static DatabaseHelper instance = null;
	private final static int DATABASE_VERSION = 3;
	//数据库默认路径SDCard
	private static String DATABASE_PATH = Environment.getExternalStorageDirectory() + "/my_db.db";
	private Context mContext;
	//数据库配置文件默认路径SDCard
	private static String DATABASE_PATH_JOURN = Environment.getExternalStorageDirectory()
			+ "/my_db.db-journ";
	Dao<User, Integer> userDao;
	Dao<Collection, Integer> collectionDao;
	Dao<College, Integer> collegeDao;
	Dao<Department, Integer> departmentDao;
	Dao<Major, Integer> majorDao;
	Dao<NewsInform, Integer> newsInformDao;
	Dao<School, Integer> schoolDao;

	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DATABASE_VERSION);
		mContext = context;
		initDtaBasePath();
		try {
			File f = new File(DATABASE_PATH);
			if (!f.exists()) {
				SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH, null);
				onCreate(db);
				db.close();
			}
		} catch (Exception e) {
		}
	}
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try{
			TableUtils.createTableIfNotExists(connectionSource, User.class);
			TableUtils.createTableIfNotExists(connectionSource, School.class);
			TableUtils.createTableIfNotExists(connectionSource, College.class);
			TableUtils.createTableIfNotExists(connectionSource, Major.class);
			TableUtils.createTableIfNotExists(connectionSource, NewsInform.class);
			TableUtils.createTableIfNotExists(connectionSource, Collection.class);
			TableUtils.createTableIfNotExists(connectionSource, Department.class);
		}catch (java.sql.SQLException e){
			throw  new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

	}

	//如果没有SDCard 默认存储在项目文件目录下
	private void initDtaBasePath() {
//		if (!Utils.ExistSDCard()) {
		DATABASE_PATH = mContext.getFilesDir().getAbsolutePath() + "/my_db.db";
		DATABASE_PATH_JOURN = mContext.getFilesDir().getAbsolutePath() + "/my_db.db-journal";
//		}
	}
	@Override
	public synchronized SQLiteDatabase getWritableDatabase() {
		return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
	}
	public synchronized SQLiteDatabase getReadableDatabase() {
		return SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
	}
	/**
	 * 单例获取该Helper
	 *
	 * @param context
	 * @return
	 */
	public static  DatabaseHelper getHelper(Context context)
	{
		if (instance == null)
			instance = new DatabaseHelper(context);
		return instance;
	}

	/*
	*get Dao
	*
	*/
	public Dao<Collection, Integer> getCollectionDao() throws SQLException {
		if (collectionDao == null) {
			collectionDao = getDao(Collection.class);
		}
		return collectionDao;
	}
	public Dao<User, Integer> getUserDao() throws SQLException {
		if (userDao == null) {
			userDao = getDao(User.class);
		}
		return userDao;
	}
	public Dao<College, Integer> getCollegeDao() throws SQLException {
		if (collegeDao == null) {
			collegeDao = getDao(College.class);
		}
		return collegeDao;
	}
	public Dao<Department, Integer> getDepartmentDao() throws SQLException {
		if (departmentDao == null) {
			departmentDao = getDao(Department.class);
		}
		return departmentDao;
	}
	public Dao<Major, Integer> getMajorDao() throws SQLException {
		if (majorDao == null) {
			majorDao = getDao(Major.class);
		}
		return majorDao;
	}
	public Dao<NewsInform, Integer> getNewsInformDao() throws SQLException {
		if (newsInformDao == null) {
			newsInformDao = getDao(NewsInform.class);
		}
		return newsInformDao;
	}
	public Dao<School, Integer> getSchoolDao() throws SQLException {
		if (schoolDao == null) {
			schoolDao = getDao(School.class);
		}
		return schoolDao;
	}

}
