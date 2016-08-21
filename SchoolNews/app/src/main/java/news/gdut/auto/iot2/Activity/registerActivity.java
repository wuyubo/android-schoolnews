package news.gdut.auto.iot2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

import java.util.ArrayList;
import java.util.List;

import news.gdut.auto.iot2.ormlite.bean.College;
import news.gdut.auto.iot2.ormlite.db.DataBaseOpt;
import news.gdut.auto.iot2.schoolnews.GlobalData;
import news.gdut.auto.iot2.schoolnews.R;

public class registerActivity extends KJActivity {


    @BindView(id = R.id.bt_ok, click = true)
    private Button bt_ok;
    @BindView(id = R.id.bt_skip, click = true)
    private Button bt_skip;
    @BindView(id = R.id.edt_name)
    private EditText name;
    @BindView(id = R.id.sp_college)
    private Spinner college;
    @BindView(id = R.id.sp_type)
    private Spinner job;

    private ArrayAdapter<String> collegeAdapter;
    private List<String> collegeStrs;
    private ArrayAdapter<String> typeAdapter;
    private List<String> typeStrs;
    private DataBaseOpt dbOpt = null;
    private String job_type;
    private int college_index;
    private List<College> collegeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initWidget() {
        super.initWidget();
        initSpinner();
        college_index = -1;
        job_type = "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.bt_ok:
               if(newPerson()){
                   startMain();
               }
                break;
            case R.id.bt_skip:
                startMain();
                break;
        }
    }

    private void initSpinner() {
        collegeStrs = new ArrayList<String>();
        typeStrs = new ArrayList<String>();
        collegeStrs.add("选择学院");
        typeStrs.add("选择类型");
        typeStrs.add("学生");
        typeStrs.add("教师");
        collegeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_normal, collegeStrs);
        typeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_normal, typeStrs);
        collegeAdapter.setDropDownViewResource(R.layout.spinner_item_normal);
        typeAdapter.setDropDownViewResource(R.layout.spinner_item_normal);
        college.setAdapter(collegeAdapter);
        job.setAdapter(typeAdapter);
        college.setOnItemSelectedListener(new SpinnerItemSelectListener(1));
        job.setOnItemSelectedListener(new SpinnerItemSelectListener(2));
        sourceCollege();
    }

    private void sourceCollege() {
        if(dbOpt == null){
            dbOpt = new DataBaseOpt(this);
        }
        collegeList = dbOpt.getCollegeAll();
        if(collegeList == null) return;
        for (College college : collegeList) {
            if (college.getName().isEmpty()) continue;
            collegeStrs.add(college.getName());
        }
        collegeAdapter.setNotifyOnChange(true);
    }

    private boolean newPerson() {
        if(name.getText().toString().isEmpty() ||job_type == "" || college_index == -1){
            toastShow("请填写完整个人信息");
            return false;
        }
        dbOpt.addUser(name.getText().toString(), null, job_type, null,
                collegeList.get(college_index-1), null);
        GlobalData.currentUser = dbOpt.getUserById(1);
        if(GlobalData.currentUser == null){
            toastShow("添加错误，请重试");
            return false;
        }
        return true;
    }

    private void startMain() {
        Intent mainIntent = new Intent(registerActivity.this, MainActivity.class);
        registerActivity.this.startActivity(mainIntent);
        registerActivity.this.finish();
    }

    //Spinner actions
    private class SpinnerItemSelectListener implements AdapterView.OnItemSelectedListener {

        private int spinnerType;

        public SpinnerItemSelectListener(int Spinner) {
            spinnerType = Spinner;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String str = parent.getItemAtPosition(position).toString();
            switch (spinnerType) {
                case 1:
                    if(str == "选择学院"){
                        college_index = -1;
                        return;
                    }
                    college_index = position;
                    break;
                case 2:
                    if(str == "选择类型"){
                        job_type = "";
                        return;
                    }
                    job_type = str;
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
    private void toastShow(String msg){
        Toast toast = Toast.makeText(registerActivity.this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }
}
