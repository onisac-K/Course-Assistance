package com.coursespick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coursespick.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by eva on 2017/2/21.
 */

public class FourthFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View rootview;
    private SwipeRefreshLayout layout_fresh;
    private RecyclerView recyclerView;
    private ViewPager viewPager;
    private ImageView sefi;
    private TabLayout tabLayout;
    public static FourthFragment newInstance() {
        FourthFragment fourthFragment = new FourthFragment();
        Bundle bundle = new Bundle();
        fourthFragment.setArguments(bundle);

        return fourthFragment;
    }
    public void InitBase(){
        sefi = (ImageView)rootview.findViewById(R.id.mepage_sefiBG);
        Glide.with(this)
                .load("http://a3.topitme.com/7/a8/6f/11220277163fa6fa87o.jpg")
                .bitmapTransform(new BlurTransformation(getActivity(), 25))
                .crossFade(1000)
                .into(sefi);
        sefi = (ImageView)rootview.findViewById(R.id.mepage_sefi);
        Glide.with(this)
                .load("http://a4.topitme.com/o/201011/28/12909463348177.jpg")
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(sefi);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fourth_fragment_main, container, false);
        Bundle bundle = getArguments();
        rootview = view;
        InitBase();
        return rootview;
    }

    @Override
    public void onRefresh() {

    }
}