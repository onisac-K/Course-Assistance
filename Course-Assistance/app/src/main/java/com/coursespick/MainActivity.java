package com.coursespick;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.coursespick.UI.SceneRecommand.SecneRecommendActivity;
import com.coursespick.adapter.secFgClassFilterAdapter;
import com.coursespick.adapter.thirdFgRealCourseAdapter;
import com.coursespick.fragment.autocourse.CourseModel;
import com.coursespick.fragment.realCourse.realCourseData;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,secFgClassFilterAdapter.secTothirdCallBack
        , thirdFgRealCourseAdapter.ThirdToFirstCallBack{

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private TextTabFragment mTextTabFragment;
    private SearchFragment searchFragment;
    static public CourseModel [] selectcourse;//手动添加的课用于显示课程表
    static public CourseModel [] allcourse;//所有用户选的课程用于一键排课显示页面1
    static public realCourseData[] realCourseDatas;//所有用户选的课程用于一键排课显示页面3
    static public List<realCourseData> realCourseDataList;//List(secondfragment单击add后存入，thirdfragment显示该list内容）


    static public void transferealCourseData(List<realCourseData> realCourseDataList1){
        List<realCourseData> temp = new ArrayList<>();
        for(int i =0;i<realCourseDatas.length;i++){
            temp.add(realCourseDatas[i]);
        }

        int length = realCourseDataList1.size();
//        realCourseData[] real = new realCourseData[100];
        for(int i = 0; i< length;i++){
            temp.add(realCourseDataList1.get(0));
        }
        realCourseData[] real = new realCourseData[temp.size()];
        for(int i = 0; i< length;i++){
            real[i] = temp.get(i);
        }
        setRealCourseDatas(real);
    }

    public CourseModel[] getSelectcourse(){
        return selectcourse;
    }
    public void setSelectcourse(CourseModel []course){
        this.selectcourse= course;
    }

    public CourseModel[] getAllcourse(){
        return allcourse;
    }
    public void setAllcourse(CourseModel []course){
        this.allcourse= course;
    }

    public realCourseData[] getRealCourseDatas(){
        return realCourseDatas;
    }
    static public void setRealCourseDatas(realCourseData []course){
        realCourseDatas= course;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);

        if(realCourseDataList == null){
            realCourseDataList = new ArrayList<>();
            Log.i("MainActivity","realCourselist == null");
        }
        else{
            Log.i("MainActivity","realcourselist 的长度" + String.valueOf(realCourseDataList.size()));
        }

        setSupportActionBar(mToolbar);
//        mToolbar.setOnMenuItemClickListener(this);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0,0);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mNavigationView.setNavigationItemSelectedListener(this);
        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                Snackbar.make(mDrawerLayout, keyword+"，缺少搜索结果页面", Snackbar.LENGTH_SHORT).show();
            }
        });
        initSearchView();
//        setNavigationViewChecked(0);
        setCurrentFragment();

    }

    private void setCurrentFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mTextTabFragment == null) {
            mTextTabFragment = TextTabFragment.newInstance();
        }
        transaction.replace(R.id.frame_content, mTextTabFragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                Snackbar.make(mDrawerLayout, "没有", Snackbar.LENGTH_SHORT).show();

//                intent.setClass(MainActivity.this,UploadClothItemActivity.class);
//                startActivity(intent);
                break;

            case R.id.menu_about:
                Snackbar.make(mDrawerLayout, "方案", Snackbar.LENGTH_SHORT).show();
                intent.setClass(MainActivity.this,SecneRecommendActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_private_message:
                Snackbar.make(mDrawerLayout, "登录", Snackbar.LENGTH_SHORT).show();

                intent.setClass(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;

        }
        mDrawerLayout.closeDrawers();
        transaction.commit();
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public void initSearchView()
    {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                searchFragment.show(getSupportFragmentManager(), SearchFragment.TAG);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setAllcoursebylist(){
        List<realCourseData> templist = realCourseDataList;
        CourseModel [] resultcourse = new CourseModel[templist.size()<<5];

        int total = 0;
        for (int i = 0 ; i < templist.size() ; i++){
            CourseModel [] tempcourse = CourseModel.realCourseData2CourseModel(templist.get(i));
            for (int j = 0 ; j < tempcourse.length; j++){
                resultcourse[total++] = tempcourse[j];
            }
        }
        setAllcourse(resultcourse);
    }
    public List<realCourseData> getRealCourseDataList(){return realCourseDataList;}
    static public void setRealCourseDataList(List<realCourseData> list){realCourseDataList = list;}


    @Override
    public void thirdAddOne(realCourseData real) {
        Log.i("MainActivity:","callBack from sec fragment to add new one on third");
//        TextTabFragment.thirdFragment;
        realCourseDataList.add(real);
        Log.i("MainActivity","realcourselist中课程个数" + String.valueOf(realCourseDataList.size()));
        TextTabFragment.thirdFragment.thirdAdapterAddOne(real);
    }

    @Override
    public void FirstAutoArrange(List<realCourseData> realList) {
        //

        realCourseData[] temp = new realCourseData[realList.size()];
        setRealCourseDatas(realList.toArray(temp));
        Log.d("FirstAutoArrang:",String.valueOf(MainActivity.realCourseDatas.length));
        for(realCourseData r:realCourseDatas){
            Log.d("FirstAutoArrang:",r.getcName());
//            r.setNecessary(1);
            Log.d("FirstAutoArrang:",r.getNecessary()+"");
        }
        //setAllcoursebylist();
        CourseModel tep[] = CourseModel.realCourseData2CourseModel(realCourseDatas);
        setAllcourse(tep);
//        TextTabFragment.firstFragment.DisplayCourse(2);
        TextTabFragment.firstFragment.DrawAutoClass();
    }

    @Override
    public void FirstAddOneClass(realCourseData real) {
        if(selectcourse == null){
            selectcourse = new CourseModel[]{};
        }
        CourseModel[] temp = CourseModel.realCourseData2CourseModel(real);
        int cnt = temp.length;
        cnt += selectcourse.length;
        CourseModel[] tep = new  CourseModel[cnt];
        for(int i = 0;i<selectcourse.length;i++){
            tep[i] = selectcourse[i];
        }
        for(int i=0;i<temp.length;++i){
            tep[i+selectcourse.length] = temp[i];
        }
        setSelectcourse(tep);
        TextTabFragment.firstFragment.DrawOneClass();
    }
}
//        ArrayList<String> list=new ArrayList<String>();
//        Strin[] realTemp = new realCourseData[realList.size()];
//        realList.toArray(realTemp);