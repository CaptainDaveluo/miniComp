package com.debla.minicomp.Activity.ServiceActivities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.debla.minicomp.minicomp.R;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dave-PC on 2017/4/13.
 */

public class VedioIndex extends Activity{
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private int imageIds[];
    private String[] titles;
    private ArrayList<ImageView> images;
    private ArrayList<View> dots;
    private TextView title;


    private int oldPosition = 0;//记录上一次点的位置
    private int currentItem; //当前页面
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vedioplayer);

        imageIds = new int[]{
                R.drawable.vedio1,
                R.drawable.vedio2,
                R.drawable.vedio3,
                R.drawable.vedio4,
                R.drawable.vedio5
        };
        //图片标题
        titles = new String[]{
                "人民的名义： 官二代赵公子上线",
                "放开我北鼻： 捣蛋鬼千玺逗哈琳",
                "狐狸的夏天： 谭松韵变保洁小妹",
                "血战钢锯岭： 二战版勇敢的心",
                "漂洋过海： 王丽坤被逼抛弃朱亚文"
        };

        images = new ArrayList<ImageView>();
        for(int i =0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));

        title = (TextView) findViewById(R.id.vedio_title);
        title.setText(titles[0]);

        mViewPager = (ViewPager) findViewById(R.id.vedio_imagevp);
        adapter = new ViewPagerAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                title.setText(titles[position]);
                dots.get(oldPosition).setBackgroundResource(R.drawable.page_indicator);
                dots.get(position).setBackgroundResource(R.drawable.page_indicator_focused);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //每隔2秒钟切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5, 5, TimeUnit.SECONDS);
    }
    private class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return images.size();
        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
            //super.destroyItem(container, position, object);
            //view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }
    }
    private class ViewPagerTask implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            currentItem = (currentItem +1) % imageIds.length;
            //更新界面
            //handler.sendEmptyMessage(0);
            handler.obtainMessage().sendToTarget();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //设置当前页面
            mViewPager.setCurrentItem(currentItem);
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
    }
}
