package com.coursespick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coursespick.MainActivity;
import com.coursespick.R;
import com.coursespick.adapter.FirstTabLayoutAdapter;

/**
 * FirstFragment用来显示所有在SecondFragment和ThirdFragment里选择的课程
 *
 */

public class FirstFragment extends Fragment  {
    private View rootview;
    private ViewPager viewPager;
    private FirstTabLayoutAdapter paperAdapter;
    private TabLayout tabLayout;
    public static FirstFragment newInstance() {
        FirstFragment firstFragment = new FirstFragment();
        Bundle bundle = new Bundle();

        firstFragment.setArguments(bundle);
        return firstFragment;
    }


    MainActivity mainActivity;
    //private DownloadClass downloadClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment_main, container, false);
        rootview = view;
        Bundle bundle = getArguments();
        mainActivity = (MainActivity)getActivity();
        InitBase();
        return view;
    }


    public void DrawAutoClass(){
        paperAdapter.AutoDisplayClass();
    }
    public void DrawOneClass(){
        paperAdapter.DrawSingleClass();
    }
    private void InitBase(){

        tabLayout = (TabLayout)rootview.findViewById(R.id.first_fragment_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("自动选课"));
        tabLayout.addTab(tabLayout.newTab().setText("手动选课"));
        viewPager = (ViewPager)rootview.findViewById(R.id.mepage_viewpaper);
//        List<Fragment> fragments = new ArrayList<>();
//        fragments.add(new HomeFragment());
//        fragments.add(new HomeFragment());
        paperAdapter = new FirstTabLayoutAdapter(getChildFragmentManager(),getActivity());
        viewPager.setAdapter(paperAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

}