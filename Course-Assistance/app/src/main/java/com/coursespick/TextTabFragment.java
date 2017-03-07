package com.coursespick;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coursespick.fragment.FirstFragment;
import com.coursespick.fragment.FourthFragment;
import com.coursespick.fragment.SecondFragment;
import com.coursespick.fragment.ThirdFragment;

public class TextTabFragment extends Fragment implements View.OnClickListener {
    private TextView mTHome, mTLocation, mTLike, mTMe;

    static public FirstFragment firstFragment;
    static public SecondFragment secondFragment;
    static public ThirdFragment thirdFragment;
    static public FourthFragment fourthFragment;

    public static TextTabFragment newInstance() {
        TextTabFragment viewPagerFragment = new TextTabFragment();
        return viewPagerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_tab, container, false);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        firstFragment = FirstFragment.newInstance();
        secondFragment = SecondFragment.newInstance();
        thirdFragment = ThirdFragment.newInstance();
        fourthFragment = FourthFragment.newInstance();
        transaction.add(R.id.sub_content,firstFragment,"FirstFragment");
        transaction.add(R.id.sub_content,secondFragment,"SecondFragment");
        transaction.add(R.id.sub_content,thirdFragment,"ThirdFragment");
        transaction.add(R.id.sub_content,fourthFragment,"FourthFragment");
        transaction.commit();

        mTHome = (TextView) view.findViewById(R.id.tv_home);
        mTLocation = (TextView) view.findViewById(R.id.tv_location);
        mTLike = (TextView) view.findViewById(R.id.tv_like);
        mTMe = (TextView) view.findViewById(R.id.tv_person);
        mTHome.setOnClickListener(this);
        mTLocation.setOnClickListener(this);
        mTLike.setOnClickListener(this);
        mTMe.setOnClickListener(this);

        setDefaultFragment();
        return view;
    }

    /**
     * set the default Fragment
     */
    private void setDefaultFragment() {
        switchFrgment(0);
        //set the defalut tab state
        setTabState(mTHome, R.drawable.ic_home_amber_600_24dp, getColor(R.color.color1));
    }

    @Override
    public void onClick(View view) {
        resetTabState();//reset the tab state
        switch (view.getId()) {
            case R.id.tv_home:
                setTabState(mTHome, R.drawable.ic_home_amber_600_24dp, getColor(R.color.color1));
                switchFrgment(0);
                break;
            case R.id.tv_location:
                setTabState(mTLocation, R.drawable.ic_near_me_amber_600_24dp, getColor(R.color.color2));
                switchFrgment(1);
                break;
            case R.id.tv_like:
                setTabState(mTLike, R.drawable.ic_shopping_basket_amber_600_24dp, getColor(R.color.color1));
                switchFrgment(2);
                break;
            case R.id.tv_person:
                setTabState(mTMe, R.drawable.ic_account_circle_amber_600_24dp, getColor(R.color.color2));
                switchFrgment(3);
                break;

        }
    }

    /**
     * switch the fragment accordting to id
     * @param i id
     */
    private void switchFrgment(int i) {
        if(i>4)return;
         // 只有4个页面
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        if(firstFragment!=null){
            transaction.hide(firstFragment);
        }
        if(secondFragment!=null){
            transaction.hide(secondFragment);
        }
        if(thirdFragment !=null){
            transaction.hide(thirdFragment);
        }
        if(fourthFragment!=null){
            transaction.hide(fourthFragment);
        }

        switch (i) {
            case 0:
                    transaction.show(firstFragment);
                break;
            case 1:
                    transaction.show(secondFragment);
                break;
            case 2:
                    transaction.show(thirdFragment);
                break;
            case 3:
                    transaction.show(fourthFragment);
                break;
        }
        transaction.commit();
    }

    /**
     * set the tab state of bottom navigation bar
     *
     * @param textView the text to be shown
     * @param image    the image
     * @param color    the text color
     */
    private void setTabState(TextView textView, int image, int color) {
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, image, 0, 0);//Call requires API level 17
        textView.setTextColor(color);
    }


    /**
     * revert the image color and text color to black
     */
    private void resetTabState() {
        setTabState(mTHome, R.drawable.ic_home_grey_500_24dp, getColor(R.color.md_blue_grey_500));
        setTabState(mTLocation, R.drawable.ic_near_me_grey_500_24dp, getColor(R.color.md_blue_grey_500));
        setTabState(mTLike, R.drawable.ic_shopping_basket_grey_500_24dp, getColor(R.color.md_blue_grey_500));
        setTabState(mTMe, R.drawable.ic_account_circle_grey_500_24dp, getColor(R.color.md_blue_grey_500));

    }

    /**
     * @param i the color id
     * @return color
     */
    private int getColor(int i) {
        return ContextCompat.getColor(getActivity(), i);
    }
}