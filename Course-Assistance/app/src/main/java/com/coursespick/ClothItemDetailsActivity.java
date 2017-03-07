package com.coursespick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Created by eva on 2017/2/17.
 */

public class ClothItemDetailsActivity extends AppCompatActivity {
    private ImageView rootImgView;
    private TextView textTitleView;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    //此方法在onResume之前执行
    @Override
    protected void onNewIntent(Intent intent) {
        //每次重新到前台就主动更新intent并保存，之后就能获取到最新的intent
        setIntent(intent);
        super.onNewIntent(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTransparentStatusBar();
        setContentView(R.layout.activity_cloth_details);

        rootImgView = (ImageView)findViewById(R.id.cloth_details_topimg);
        textTitleView = (TextView)findViewById(R.id.cloth_details_textTitle);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.cloth_details_collapsingToolbarLayout);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String imageUrl = bundle.getString("imageUrl");
        String textTitle  = bundle.getString("textTitle");
        collapsingToolbarLayout.setTitle(textTitle);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        textTitleView.setText(textTitle);
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(rootImgView);

    }
    @SuppressLint("InlinedApi")
    private void initTransparentStatusBar()
    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }


    }
}
