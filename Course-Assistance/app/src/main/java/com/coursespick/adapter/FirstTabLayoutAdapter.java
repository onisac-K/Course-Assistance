package com.coursespick.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.coursespick.fragment.firstSubFragment;
import com.coursespick.fragment.realCourse.realCourseData;

/**
 * Created by eva on 2017/3/4.
 */

public class FirstTabLayoutAdapter extends FragmentPagerAdapter {
    public final int COUNT = 2;
    private String[] titles = new String[]{"自动选课","手动选课"};
    private Context context;
    static private firstSubFragment AutoFragment;
    static private firstSubFragment NotAutoFragment;
    public FirstTabLayoutAdapter(FragmentManager fm, Context context) {
        super(fm);
        AutoFragment = firstSubFragment.newInstance(2);
        NotAutoFragment = firstSubFragment.newInstance(1);
        this.context = context;
    }

    public void AutoDisplayClass(){
        AutoFragment.DisplayCourse(2);
    }
    public void DrawSingleClass(){
        NotAutoFragment.DisplayCourse(1);
    }
    public void NotAutoAddOneClass(realCourseData real){

    }

    @Override
    public Fragment getItem(int position) {
            if(position == 0)
                return AutoFragment;
            else {
                return NotAutoFragment;
            }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
