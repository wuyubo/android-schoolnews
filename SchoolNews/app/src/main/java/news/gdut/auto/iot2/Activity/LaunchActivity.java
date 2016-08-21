package news.gdut.auto.iot2.Activity;


import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.os.PersistableBundle;


import org.kymjs.kjframe.KJActivity;

import news.gdut.auto.iot2.ormlite.bean.School;
import news.gdut.auto.iot2.ormlite.db.DataBaseOpt;
import news.gdut.auto.iot2.schoolnews.GlobalData;
import news.gdut.auto.iot2.schoolnews.R;
import news.gdut.auto.iot2.schoolnews.netservice.GetNewsService;

public class LaunchActivity extends KJActivity {

    private DataBaseOpt dbOpt;
    String[] schoolName = {"广东工业大学"};
    String[] schoolUrl = {"http://gdutnews.gdut.edu.cn/"};
    String[] collegeName = {"自动化", "外国语", "通信工程","材料与能源","机电","计算机"};
    String[] collegeUrl = {"http://gdutnews.gdut.edu.cn/", "m.baidu.com", "m.baidu.com", "m.baidu.com", "m.baidu.com", "m.baidu.com"};
    String[] majorName = {"物联网工程", "自动化", "日语","模具","机械","计算机信息","网络工程"};
    String[] majorKeys = {"物联网工程", "自动化", "日语","模具","机械","计算机信息","网络工程"};
    private Class<?> cls;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        welcome();
    }

    private void welcome(){
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(LaunchActivity.this, cls);
                LaunchActivity.this.startActivity(mainIntent);
                LaunchActivity.this.finish();
            }
        }, 2900); //2900 for release
    }

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_launch);
    }


    private void init_Database(){
        dbOpt = new DataBaseOpt(this);
        School school = dbOpt.getSchoolById(1);
        if(school == null){
            source_database();
        }
        GlobalData.currentSchool = dbOpt.getSchoolBySchoolName("广东工业大学");
        new GetNewsService(this).StartService(GlobalData.currentSchool);
        GlobalData.currentUser = dbOpt.getUserById(1);
        if(GlobalData.currentUser == null){
            cls = registerActivity.class;
        }else {
            cls = MainActivity.class;
        }
    }

    private void source_database(){
        for(int i=0; i<schoolName.length; i++){
            dbOpt.addSchool(schoolName[i],schoolUrl[i]);
        }
        for(int j=0; j<collegeName.length; j++){
            dbOpt.addCollege(collegeName[j], collegeUrl[j], dbOpt.getSchoolById(1));
        }
        for(int j=0; j<majorName.length; j++){
            dbOpt.addMajor(majorName[j], majorKeys[j]);
        }
    }

    @Override
    public void initData() {
        super.initData();
        init_Database();
    }
}
