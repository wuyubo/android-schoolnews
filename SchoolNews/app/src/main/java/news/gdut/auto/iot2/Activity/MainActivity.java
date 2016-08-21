package news.gdut.auto.iot2.Activity;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import java.lang.reflect.Method;

import news.gdut.auto.iot2.schoolnews.GlobalData;
import news.gdut.auto.iot2.schoolnews.R;
import news.gdut.auto.iot2.schoolnews.fragment.FragmentColletion;
import news.gdut.auto.iot2.schoolnews.fragment.FragmentInfo;
import news.gdut.auto.iot2.schoolnews.fragment.FragmentNews;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments = new Fragment[4];
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragments[0] = new FragmentNews();
        fragments[1] = new FragmentColletion();
        fragments[2] = new FragmentInfo();
          getFragmentManager().beginTransaction().add(R.id.main_fragment,fragments[1])
                .add(R.id.main_fragment,fragments[2]).add(R.id.main_fragment,fragments[0]).commit();
        newsClick(null);
    }

    public void newsClick(View view){
        getFragmentManager().beginTransaction().hide(fragments[1])
                .hide(fragments[2]).show(fragments[0]).commit();

        this.getSupportActionBar().setTitle(GlobalData.APP_NAME);
    }

    public void collectionClick(View view){
        if(GlobalData.currentUser==null){
            register();
        }
        getFragmentManager().beginTransaction().hide(fragments[0])
                .hide(fragments[2]).show(fragments[1]).commit();

        this.getSupportActionBar().setTitle("我的收藏夹");
    }

    public void myInfoClick(View view){
        if(GlobalData.currentUser==null){
            register();
        }

        getFragmentManager().beginTransaction().hide(fragments[0])
                .hide(fragments[1]).show(fragments[2]).commit();
        this.getSupportActionBar().setTitle("个人信息");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
//        menu.add(Menu.NONE, Menu.FIRST+1, 0, "set") .setIcon(R.drawable.icon_setting);
        mMenu = menu;
		mMenu.findItem(R.id.menu_collection).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "这是广东工业大学定制新闻app,欢迎体验");
                startActivity(Intent.createChooser(intent, getTitle()));
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
    private void register() {
        Intent mainIntent = new Intent(MainActivity.this, registerActivity.class);
        MainActivity.this.startActivity(mainIntent);
        MainActivity.this.finish();
    }

}
