package news.gdut.auto.iot2.ormlite.db;

import android.content.Context;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.List;

import news.gdut.auto.iot2.ormlite.bean.Collection;
import news.gdut.auto.iot2.ormlite.bean.College;
import news.gdut.auto.iot2.ormlite.bean.Department;
import news.gdut.auto.iot2.ormlite.bean.Major;
import news.gdut.auto.iot2.ormlite.bean.NewsInform;
import news.gdut.auto.iot2.ormlite.bean.School;
import news.gdut.auto.iot2.ormlite.bean.User;

/**
 * Created by Administrator on 2016/5/15.
 */
public class DataBaseOpt {

    private static Context mcontext;
    private static UserDao userDao;
    private static SchoolDao schoolDao;
    private static CollegeDao collegeDao;
    private static MajorDao majorDao;
    private static NewsInformDao newsInformDao;
    private static CollectionDao collectionDao;
    private static DepartmentDao departmentDao;

    public DataBaseOpt(Context context){
        mcontext = context;
    }
    public static void setContext(Context context){
        mcontext = context;
    }

    //////add Beans
    public static Boolean addUser(String name, String sex, String professional,
                           School school, College college, Major major){
        User user = new User();
        userDao = UserDao.getInstence(mcontext);
        user.setName(name);
        user.setsex(sex);
        user.setprofessional(professional);
        user.setschool(school);
        user.setcollege(college);
        user.setmajor(major);
        userDao.add(user);
       return true;
    }

    public static Boolean addSchool(String name, String url){
        School school = new School();
        schoolDao = SchoolDao.getInstence(mcontext);
        school.setName(name);
        school.seturl(url);
        schoolDao.add(school);
        return true;
    }

    public static Boolean addMajor(String name, String keys){
        Major major = new Major();
        majorDao = MajorDao.getInstence(mcontext);
        major.setName(name);
        major.setkeys(keys);
        majorDao.add(major);
        return true;
    }

    public static Boolean addNewsInform(String title, String type, String src_url,
                                 String img_url, String content, Boolean is_valid, String time,
                                 School school, Department department){
        NewsInform newsInform = new NewsInform();
        newsInformDao = NewsInformDao.getInstence(mcontext);
        newsInform.settitle(title);
        newsInform.settype(type);
        newsInform.setsrc_url(src_url);
        newsInform.setschool(school);
        newsInform.setimg_url(img_url);
        newsInform.setcontent(content);
        newsInform.setis_valid(is_valid);
        newsInform.settime(time);
        newsInform.setschool(school);
        newsInform.setdepartment(department);
        newsInformDao.add(newsInform);
        return true;
    }

    public static Boolean addCollection(String name, User user, NewsInform newsInform, String time){
        Collection collection = new Collection();
        collectionDao = CollectionDao.getInstence(mcontext);
        collection.setId(1);
        collection.setUser(user);
        collection.setNewsInform(newsInform);
        collection.settime(time);
        collectionDao.add(collection);
        return true;
    }

    public static Boolean addDepartment(String name, School school){
        Department department = new Department();
        departmentDao = departmentDao.getInstence(mcontext);
        department.setName(name);
        department.setSchool(school);
        departmentDao.add(department);
        return true;
    }

    public static Boolean addCollege(String name, String url, School school){
        College college = new College();
        collegeDao = CollegeDao.getInstence(mcontext);
        college.setName(name);
        college.seturl(url);
        college.setSchool(school);
        collegeDao.add(college);
        return true;
    }

 /////// select option By id
    public static School getSchoolById(int id){
        schoolDao = SchoolDao.getInstence(mcontext);
        return schoolDao.getById(id);
    }

    public static Department getDepartmentById(int id){
        departmentDao = DepartmentDao.getInstence(mcontext);
        return departmentDao.getById(id);
    }

    public static  Major getMajorById(int id){
        majorDao = MajorDao.getInstence(mcontext);
        return majorDao.getById(id);
    }

    public static College getCollegeById(int id){
        collegeDao = CollegeDao.getInstence(mcontext);
        return collegeDao.getById(id);
    }

    public static NewsInform getNewsInformById(int id){
        newsInformDao = NewsInformDao.getInstence(mcontext);
        return newsInformDao.getById(id);
    }
    public static User getUserById(int id){
        userDao = UserDao.getInstence(mcontext);
        return userDao.getById(id);
    }
    //school select options
    public static School getSchoolBySchoolName(String name){
        schoolDao = SchoolDao.getInstence(mcontext);
        return schoolDao.getByName(name);
    }
    public static List<School> getSchoolAll(){
        schoolDao = SchoolDao.getInstence(mcontext);
        return schoolDao.getAll();
    }
    //NewInform select options
    public static Boolean isNewExistByUrl(String url){
        newsInformDao = NewsInformDao.getInstence(mcontext);
        if(newsInformDao.getBySrc_url(url) != null){
            return true;
        }
        return false;
    }
    public static NewsInform getNewsInformByUrl(String url){
        newsInformDao = NewsInformDao.getInstence(mcontext);
       return newsInformDao.getBySrc_url(url);
    }
    //Departmet select options
    public static Department getDepartmentByNameOrCreate(String name, School school){
        departmentDao = DepartmentDao.getInstence(mcontext);
        Department department = departmentDao.getByName(name);
        if(department == null){
            addDepartment(name, school);
            return getDepartmentByNameOrCreate(name, school);
        }
        return department;
    }

    public static List<Department> getDepartmentAllBySchool(School school){
        departmentDao = DepartmentDao.getInstence(mcontext);
        return departmentDao.getBySchool(school);
    }
    //College select options
    public static List<College> getCollegeAll(){
        collegeDao = CollegeDao.getInstence(mcontext);
        return collegeDao.getAll();
    }
    //colletion select options
    public static List<Collection> getColletionByUser(User user){
        collectionDao = CollectionDao.getInstence(mcontext);
        return collectionDao.getByUser(user);
    }
    public static List<Collection> getColletionByNewsInfom(NewsInform newsInform){
        collectionDao = CollectionDao.getInstence(mcontext);
        return collectionDao.getByNewsInform(newsInform);
    }
    public static boolean deleteColletionByUN(User user, NewsInform newsInform){
        collectionDao = CollectionDao.getInstence(mcontext);
        return collectionDao.deleteByUN(user, newsInform);
    }
   //////////////////////
    public static List<NewsInform> getNewsInformList(NewsInformOptions opt){

        newsInformDao = NewsInformDao.getInstence(mcontext);
        return newsInformDao.getNewsInformList(opt);
    }
    public static NewsInform getNewsInformBySrcUrl(String url){
        newsInformDao = NewsInformDao.getInstence(mcontext);

        return newsInformDao.getBySrc_url(url);
    }

    public static java.sql.Date transfromDate(String dateFormat){
        SimpleDateFormat bartDateFormat =   new SimpleDateFormat("yyyy-MM-dd");
        String dateStringToParse = dateFormat;
        java.sql.Date sqlDate = null;
        try{
            java.util.Date date = bartDateFormat.parse(dateStringToParse);
            sqlDate = new java.sql.Date(date.getTime());
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return sqlDate;
    }

}
