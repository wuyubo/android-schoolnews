package news.gdut.auto.iot2.ormlite.db;

import com.j256.ormlite.field.DatabaseField;

import news.gdut.auto.iot2.ormlite.bean.Department;
import news.gdut.auto.iot2.ormlite.bean.School;

/**
 * Created by Administrator on 2016/5/15.
 */
public class NewsInformOptions {
    public Boolean isFilter=false;
    public Boolean isOrder=true;
    public int orderindex = NewsInformOptions.TIME;
    public Boolean ordertype = true;
    public int filterindex;
    public int skip_take_index = ID;
    public int id;
    public String title;
    public String type;
    public String src_url;
    public String img_url;
    public String content;
    public Boolean is_valid;
    public String time;
    public School school;
    public Department department;
    public int skip = 0;
    public int take = 100;
    public Boolean isSeach = false;
    public String seachString = "%";

    public static final int ID = 0;
    public static final int TITLE = 1;
    public static final int TYPE = 2;
    public static final int SRC_URL = 3;
    public static final int IMG_URL = 41;
    public static final int CONTENT = 5;
    public static final int IS_VALID = 6;
    public static final int TIME = 7;
    public static final int SCHOOL = 8;
    public static final int DEPARTMENT = 9;

    public static String getColumnName(int index){
        switch (index){
            case ID:
                return "id";
            case TITLE:
                return "title";
            case TYPE:
                return "type";
            case SRC_URL:
                return "src_url";
            case IMG_URL:
                return "img_url";
            case CONTENT:
                return "content";
            case IS_VALID:
                return "is_valid";
            case TIME:
                return "time";
            case SCHOOL:
                return "school_id";
            case DEPARTMENT:
                return "department_id";
        }
        return "";
    }

    public String getColumnStringValue(int index){
        switch (index){
            case TITLE:
                return title;
            case TYPE:
                return type;
            case SRC_URL:
                return src_url;
            case IMG_URL:
                return img_url;
            case CONTENT:
                return content;
            case TIME:
                return time;
        }
        return "";
    }

    public  int getColumnIntValue(int index){
        switch (index){
            case ID:
                return id;
        }
        return 0;
    }
}
