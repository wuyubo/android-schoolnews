package news.gdut.auto.iot2.schoolnews.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import news.gdut.auto.iot2.schoolnews.GlobalData;
import news.gdut.auto.iot2.schoolnews.R;


public class FragmentInfo extends android.app.Fragment {

    private Activity activity;
    private TextView name;
    private TextView college;
    private TextView type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_fragment_info, container, false);
        this.activity = getActivity();

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        name = (TextView)activity.findViewById(R.id.tv_name);
        college = (TextView)activity.findViewById(R.id.tv_college);
        type = (TextView) activity.findViewById(R.id.tv_type);
        if(GlobalData.currentUser != null){
            name.setText(GlobalData.currentUser.getName());
            college.setText(GlobalData.currentUser.getcollege().getName()+"学院");
            type.setText(GlobalData.currentUser.getprofessional());
        }
    }


}
