package com.coursespick.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coursespick.R;
import com.coursespick.adapter.thirdFgRealCourseAdapter;
import com.coursespick.fragment.realCourse.realCourseData;


/**
 * Created by eva on 2017/2/21.
 */

public class ThirdFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static ThirdFragment newInstance() {
        ThirdFragment thirdFragment = new ThirdFragment();
        Bundle bundle = new Bundle();
        thirdFragment.setArguments(bundle);
        return thirdFragment;
    }
    private TextView btnFloatAuto;
    private View rootview;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mainList;
    private thirdFgRealCourseAdapter adpater;
//    private List<realCourseData> dataTemp;
//    private thirdReal
    public void thirdAdapterAddOne(realCourseData real){
        adpater.addOne(real);
        CheckAndSetEmpty();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.third_fragment_main, container, false);
        rootview = view;
//        List<realCourseData> temp = new ArrayList<realCourseData>(Arrays.asList(MainActivity.realCourseDatas));
//        List<realCourseData> temp = new ArrayList<>();
//        realCourseData[] test  = MainActivity.realCourseDatas;
//        if(test != null){
//            for (int i = 0; i < test.length; i++) {
//                temp.add(test[i]);
//                Log.d("thirdFg", "onCreateView: " + test[i].getcName());
//            }
//        }
//        dataTemp = temp;
        InitBase();
        InitWidgetsFunction();
        return view;
    }
    private TextView tvNoDataShow;
    private void InitBase(){
        tvNoDataShow = (TextView)rootview.findViewById(R.id.third_fragment_no_data_show);
        mainList = (RecyclerView)rootview.findViewById(R.id.third_fragment_realcourse_list);
        btnFloatAuto = (TextView)rootview.findViewById(R.id.third_fragment_float_Btn);
        refreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.third_fragment_refresh);
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        refreshLayout.setOnRefreshListener(this);
    }
    private void InitWidgetsFunction(){
//        一行的布局
        mainList.setLayoutManager(new LinearLayoutManager(mainList.getContext(), LinearLayoutManager.VERTICAL, false));
//        其他布局
//        mainList.setLayoutManager(new GridLayoutManager(mainList.getContext(), 6, GridLayoutManager.VERTICAL, false));
//        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager.setAutoMeasureEnabled(true);
        mainList.setItemAnimator(new DefaultItemAnimator());
//        Bundle bundle = getArguments();
//        realCourseData real = (realCourseData)bundle.getSerializable("realCourseData");
//        dataTemp.add(real);
        mainList.setAdapter(adpater = new thirdFgRealCourseAdapter(getActivity()));
        CheckAndSetEmpty();
        btnFloatAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 一键排课按下
                //显示课程在第一个页面
                if(adpater == null){
                    Log.d("adpater"," is null");
                    return;
                }
                if(adpater.callBack == null){
                    Log.d("adpater.callBack"," is null");
                    return;
                }
                if(adpater.getDatashow().size() == 0){
                    Log.d("没有需要排课的数据", "onClick: size = 0");
                    return;
                }
//

                adpater.callBack
                        .FirstAutoArrange(adpater.getDatashow());
            }
        });
    }
    public void CheckAndSetEmpty(){
        if(adpater.getItemCount()==0)
            tvNoDataShow.setVisibility(View.VISIBLE);
        else{
            tvNoDataShow.setVisibility(View.GONE);
        }
    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                CheckAndSetEmpty();
                adpater.notifyDataSetChanged();
            }
        }, 1000);
    }
}