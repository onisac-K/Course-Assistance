package com.coursespick.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.coursespick.MainActivity;
import com.coursespick.R;
import com.coursespick.fragment.CourseTable.ColorUtils;
import com.coursespick.fragment.CourseTable.CornerTextView;
import com.coursespick.fragment.autocourse.AutoCourseDao;
import com.coursespick.fragment.autocourse.CourseDao;
import com.coursespick.fragment.autocourse.CourseModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by eva on 2017/3/4.
 */

public class firstSubFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    static public int mode ;  //1为手动 ， 2为自动
    public static firstSubFragment newInstance(int i) {
        firstSubFragment firstFragment = new firstSubFragment();
        Bundle bundle = new Bundle();
        mode = i;
        firstFragment.setArguments(bundle);
        return firstFragment;
    }
    CourseModel[] course;
    private View rootView;
    private TextView tvClearTable;
    LinearLayout weekNames;
    LinearLayout sections;
    SwipeRefreshLayout mFreshLayout;
    List<LinearLayout> mWeekViews;
    private Activity mActivity;
    private int itemHeight;
    private int maxSection = 13;
    MainActivity mainActivity;
    //private DownloadClass downloadClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_content, container, false);
        rootView = view;
        Bundle bundle = getArguments();
        itemHeight = getResources().getDimensionPixelSize(R.dimen.sectionHeight);
        mActivity = getActivity();
        mainActivity = (MainActivity)getActivity();
        bindViews();
        initWeekNameView();
        initSectionView();
        DisplayCourse(mode);
        return view;
    }

    /**
     * 绑定一周五天的View和布局，用来显示课程表
     */
    private void bindViews()
    {
        tvClearTable = (TextView)rootView.findViewById(R.id.first_fragment_clear_table);
        tvClearTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //清空MainActivity中课表
                if(mode == 2) {
                    MainActivity.allcourse = new CourseModel[]{};
                }else if(mode == 1) {
                    MainActivity.selectcourse = new CourseModel[]{};
                }
                clearChildView();
            }
        });
        weekNames = (LinearLayout)rootView.findViewById(R.id.weekNames);
        sections = (LinearLayout)rootView.findViewById(R.id.sections);
        mFreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.mFreshLayout);
        mFreshLayout.setColorSchemeResources(R.color.color1, R.color.color1);
        mFreshLayout.setOnRefreshListener(this);
        mWeekViews = new ArrayList<>();
        mWeekViews.add((LinearLayout)rootView.findViewById(R.id.weekPanel_1));
        mWeekViews.add((LinearLayout)rootView.findViewById(R.id.weekPanel_2));
        mWeekViews.add((LinearLayout)rootView.findViewById(R.id.weekPanel_3));
        mWeekViews.add((LinearLayout)rootView.findViewById(R.id.weekPanel_4));
        mWeekViews.add((LinearLayout)rootView.findViewById(R.id.weekPanel_5));
        //mWeekViews.add((LinearLayout)rootView.findViewById(R.id.weekPanel_6));
        //mWeekViews.add((LinearLayout)rootView.findViewById(R.id.weekPanel_7));
    }
    /**
     * DisplayCourse()把所选课程画出来，调用initWeekPanel
     * flag == 1 显示手动排课， flag == 2 显示自动排课
     */
    public void DisplayCourse(int flag){
        clearChildView();
        if(flag == 1){
            if(MainActivity.selectcourse == null)
                return;
            Log.d("Display:",MainActivity.selectcourse.length+"");
            for(CourseModel m:MainActivity.selectcourse){
                Log.d("Display:",m.getCourseName());
                Log.d("Display:","Necessary:"+m.getNecessary()+"");
            }
            if(MainActivity.selectcourse != null) {
                Boolean c = CourseDao.courseConflict(MainActivity.selectcourse);
                Log.d("Display: c:",c+"");
                if(c==false){
                    Toast.makeText((MainActivity)getActivity(),"您选的课程与已有课程冲突",Toast.LENGTH_SHORT);
                    return;
                }
            }
//            Log.i("1",String.valueOf(1234));
            course = mainActivity.getSelectcourse();
            if(course == null) {
                Log.i("FirstFragment中","course == null");
                return ;
            }
            Log.i("1",String.valueOf(course.length));
            CourseDao.setCourseData(course);
            for (int i = 0; i < mWeekViews.size(); i++){
                initWeekPanel(mWeekViews.get(i), CourseDao.getCourseData()[i]);
            }
        }
        else if(flag == 2){
            course = mainActivity.getAllcourse();
            if(course == null){
                Log.i("FirstFragment中","course == null");
                return ;
            }
            AutoCourseDao.SetCourseData(course);
            for (int i = 0; i < mWeekViews.size(); i++){
                initWeekPanel(mWeekViews.get(i), AutoCourseDao.getCourseData()[i]);
            }
        }
//        if(course == null) {
//            Log.i("FirstFragment中","course == null");
//            return ;
//        }
        Log.i("firstfragment中第一门课的名字",course[0].getCourseName());


    }

    /**
     * 顶部周一到周日的布局
     **/
    private void initWeekNameView() {
        for (int i = 0; i < mWeekViews.size() + 1; i++) {
            TextView tvWeekName = new TextView(mActivity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity = Gravity.CENTER | Gravity.CENTER_HORIZONTAL;
            if (i != 0) {
                lp.weight = 1.4f;
                tvWeekName.setText("周" + intToZH(i));
                if (i == getWeekDay()) {
                    tvWeekName.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    tvWeekName.setTextColor(Color.parseColor("#4A4A4A"));
                }
            } else {
                lp.weight = 0.8f;
                tvWeekName.setText(getMonth() + "月");
            }
            tvWeekName.setGravity(Gravity.CENTER_HORIZONTAL);
            tvWeekName.setLayoutParams(lp);
            weekNames.addView(tvWeekName);
        }
    }

    /**
     * 左边节次布局，每天13节课
     */
    private void initSectionView() {
        for (int i = 1; i <= maxSection; i++) {
            TextView tvSection = new TextView(mActivity);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, getResources().getDimensionPixelSize(R.dimen.sectionHeight));
            lp.gravity = Gravity.CENTER;
            tvSection.setGravity(Gravity.CENTER);
            tvSection.setText(String.valueOf(i));
            tvSection.setLayoutParams(lp);
            sections.addView(tvSection);
        }
    }

    /**
     * 当前星期
     */
    public int getWeekDay() {
        int w = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1;
        if (w <= 0) {
            w = 5;
        }
        return w;
    }

    /**
     * 当前月份
     */
    public int getMonth() {
        int w = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return w;
    }

    /**
     * 每次刷新前清除每个LinearLayout上的课程view
     */
    public void clearChildView() {
        for (int i = 0; i < mWeekViews.size(); i++) {
            if (mWeekViews.get(i) != null)
                if (mWeekViews.get(i).getChildCount() > 0)
                    mWeekViews.get(i).removeAllViews();
        }
    }


    public void initWeekPanel(LinearLayout ll, List<CourseModel> data) {

        if (ll == null || data == null || data.size() < 1)
            return;
        CourseModel firstCourse = data.get(0);
        for (int i = 0; i < data.size(); i++) {
            final CourseModel courseModel = data.get(i);

            if (courseModel.getSection() == 0 || courseModel.getSectionSpan() == 0)
                return;
            FrameLayout frameLayout = new FrameLayout(mActivity);

            CornerTextView tv = new CornerTextView(mActivity,
                    ColorUtils.getCourseBgColor(courseModel.getCourseFlag()),
                    dip2px(mActivity, 10));
            LinearLayout.LayoutParams frameLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    itemHeight * courseModel.getSectionSpan());
            LinearLayout.LayoutParams tvLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            if (i == 0) {
                frameLp.setMargins(0, (courseModel.getSection() - 1) * itemHeight, 0, 0);
            } else {
                frameLp.setMargins(0, (courseModel.getSection() - (firstCourse.getSection() + firstCourse.getSectionSpan())) * itemHeight, 0, 0);
            }
            tv.setLayoutParams(tvLp);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(14);
            tv.setTextColor(Color.parseColor("#FFFFFF"));
            tv.setText(courseModel.getCourseName() + "\n @" + courseModel.getClassRoom());

            frameLayout.setLayoutParams(frameLp);
            frameLayout.addView(tv);
            frameLayout.setPadding(2, 2, 2, 2);
            ll.addView(frameLayout);
            firstCourse = courseModel;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showToast(courseModel.getCourseName());
                }
            });
        }
    }

    /**
     * Toast
     */
    private void showToast(String msg) {
        if (TextUtils.isEmpty(msg))
            return;
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 数字转换中文
     */
    public static String intToZH(int i) {
        String[] zh = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] unit = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十"};

        String str = "";
        StringBuffer sb = new StringBuffer(String.valueOf(i));
        sb = sb.reverse();
        int r = 0;
        int l = 0;
        for (int j = 0; j < sb.length(); j++) {
            r = Integer.valueOf(sb.substring(j, j + 1));
            if (j != 0)
                l = Integer.valueOf(sb.substring(j - 1, j));
            if (j == 0) {
                if (r != 0 || sb.length() == 1)
                    str = zh[r];
                continue;
            }
            if (j == 1 || j == 2 || j == 3 || j == 5 || j == 6 || j == 7 || j == 9) {
                if (r != 0)
                    str = zh[r] + unit[j] + str;
                else if (l != 0)
                    str = zh[r] + str;
                continue;
            }
            if (j == 4 || j == 8) {
                str = unit[j] + str;
                if ((l != 0 && r == 0) || r != 0)
                    str = zh[r] + str;
                continue;
            }
        }
        if (str.equals("七"))
            str = "日";
        return str;
    }
    /*子线程下拉刷新*/
    @Override
    public void onRefresh() {
        Log.i("课程表FirstFragment","刷新一次");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearChildView();
                //initWeekCourseView();
                DisplayCourse(mode);
                mFreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}