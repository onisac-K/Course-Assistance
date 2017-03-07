package com.coursespick.UI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.coursespick.R;
import com.coursespick.Util.MyStrUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SlideShowView extends FrameLayout {

    // 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.8.6-with-sources.jar
//	private ImageLoader imageLoader = ImageLoader.getInstance();
//	private BitmapUtils bitmapUtils;
    //轮播图图片数量
    private final static int IMAGE_COUNT = 4;
    //自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
    //自动轮播启用开关
    private final static boolean isAutoPlay = false;

    //跳转监听器
    private OnClickListener goListener;

    //自定义轮播图的资源
    private String[] imageUrls;
    //    private int[] imageSrcs;
    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    //放圆点的View的list
    private List<View> dotViewsList;

    private ViewPager viewPager;
    //当前轮播页
    private int currentItem  = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;

    private Context context;

    //Handler
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }

    };

    public SlideShowView(Context context) {
        this(context,null);
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            this.context = context;
//	        this.bitmapUtils = BitmapHelp.getBitmapUtils();
//	        this.bitmapUtils.configDefaultLoadingImage(R.drawable.none);
//	    	this.bitmapUtils.configDefaultLoadFailedImage(R.drawable.none);

            initData();
            // 一步任务获取图片
            new GetListTask().execute("");
            if(isAutoPlay){
                startPlay();
            }
        }
    }
    /**
     * 开始轮播图切换
     */
    public void startPlay(){
        if (scheduledExecutorService==null) {
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 4, 4, TimeUnit.SECONDS);
        }
    }
    /**
     * 停止轮播图切换
     */
    private void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    /**
     * 初始化相关Data
     */
    private void initData(){
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
    }
    /**
     * 初始化Views等UI
     */
    private void initUI(Context context){
        if(imageUrls == null || imageUrls.length == 0)
            return;

        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);

        LinearLayout dotLayout = (LinearLayout)findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        // 热点个数与图片特殊相等
        for (int i = 0; i < imageUrls.length; i++) {
            ImageView view =  new ImageView(context);
//            view.setTag(imageUrls[i]);
//        	if(i==0)//给一个默认图
//        		view.setBackgroundResource(R.drawable.defalt);
            view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        	view.setScaleType(ScaleType.FIT_START);
            imageViewsList.add(view);

            ImageView dotView =  new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.leftMargin = 8;
            params.rightMargin = 8;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }

        /////////首次进入的时候初始化点点
        for(int i=0;i < dotViewsList.size();i++){
            if(i == 0){
                ((View)dotViewsList.get(0)).setBackgroundResource(R.drawable.shape_oval_dot_orage);
            }else {
                ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.shape_oval_dot_dark);
            }
        }
        //////////////////////////

        if (imageViewsList.size()>0 && goListener!=null) {
            imageViewsList.get(imageViewsList.size()-1).setOnClickListener(goListener);
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);

        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }

    public void setOnGolistener(OnClickListener goListener) {
        this.goListener = goListener;
    }

    /**
     * 填充ViewPager的页面适配器
     *
     */
    private class MyPagerAdapter  extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager)container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViewsList.get(position);

            if (!MyStrUtil.isEmpty(imageUrls)) {
                //TODO load img
                Glide.with(context)
                        .load(imageUrls[position])
                        .placeholder(R.drawable.ic_more_horiz_grey_400_48dp)
                        .centerCrop()
                        .into(imageView);
//                ImageOptions imageOptions = new ImageOptions.Builder()
////                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
////                .setRadius(DensityUtil.dip2px(5))
//                        // 如果ImageView的大小不是定义为wrap_content, 不要crop.
//                        .setCrop(true)
//                        // 加载中或错误图片的ScaleType
//                        //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
//                        .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
////                .setLoadingDrawableId(R.mipmap.ic_launcher)
////                .setFailureDrawableId(R.mipmap.ic_launcher)
//                        .build();
//                x.image().bind(imageView, imageView.getTag().toString(),imageOptions,new CustomBitmapLoadCallBack(imageView));
//        			bitmapUtils.display(imageView, imageView.getTag().toString());
            }
            imageView.setBackgroundResource(R.color.white);

            container.addView(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

    }
    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     *
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    if (isAutoPlay) {
                        // 当前为最后一张，此时从右向左滑，则切换到第一张
                        if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                            viewPager.setCurrentItem(0);
                        }
                        // 当前为第一张，此时从左向右滑，则切换到最后一张
                        else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                            viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                        }
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub

            currentItem = pos;
            for(int i=0;i < dotViewsList.size();i++){
                if(i == pos){
                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.shape_oval_dot_orage);
                }else {
                    ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.shape_oval_dot_dark);
                }
            }
        }

    }

    /**
     *执行轮播图切换任务
     *
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }

    /**
     * 销毁ImageView资源，回收内存
     *
     */
    private void destoryBitmaps() {

        for (int i = 0; i < IMAGE_COUNT; i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                //解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }

    /**
     * 释放资源，在activity结束时请调用
     */
    public void destory() {
        stopPlay();
        destoryBitmaps();
    }

    public void setImageSrcs(int[] imageSrcs) {
        if (MyStrUtil.isEmpty(imageSrcs)) {
            return;
        }
        imageUrls = new String[imageSrcs.length];
        for (int i = 0; i < imageSrcs.length; i++) {
            imageUrls[i] = imageSrcs[i]+"";
        }
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    /**
     * 异步任务,获取数据
     *
     */
    class GetListTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                // 这里一般调用服务端接口获取一组轮播图片，下面是从百度找的几个图片

				imageUrls = new String[]{
						"http://a3.topitme.com/c/83/9e/118171493793d9e83cl.jpg",
						"http://a3.topitme.com/c/83/9e/118171493793d9e83cl.jpg",
						"http://a3.topitme.com/c/83/9e/118171493793d9e83cl.jpg"
				};

//				imageSrcs = new int[]{
//					R.drawable.startup1,
//					R.drawable.startup2,
//					R.drawable.startup3
//				};

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                initUI(context);
            }
        }
    }

}