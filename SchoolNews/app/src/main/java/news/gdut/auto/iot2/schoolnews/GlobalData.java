package news.gdut.auto.iot2.schoolnews;

import android.widget.ProgressBar;
import android.widget.TextView;

import news.gdut.auto.iot2.ormlite.bean.NewsInform;
import news.gdut.auto.iot2.ormlite.bean.School;
import news.gdut.auto.iot2.ormlite.bean.User;

/**
 * Created by wuyubo on 2016/6/8.
 */
public class GlobalData {
    public static School currentSchool;
    public static String APP_NAME = "广工新闻";
    public static User currentUser;
    public static int listViewShowRowsCount = 5;
    public static int newsContextShow = 60;
}
