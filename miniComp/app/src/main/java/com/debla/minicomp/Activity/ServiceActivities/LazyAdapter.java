package com.debla.minicomp.Activity.ServiceActivities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.debla.minicomp.Structs.ImageLoader;
import com.debla.minicomp.Structs.NewsListBean;
import com.debla.minicomp.minicomp.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Dave-PC on 2017/4/6.
 */

public class LazyAdapter extends BaseAdapter{
    public Activity a;
    public ImageLoader imageLoader;
    public ArrayList<NewsListBean.NewsDataBean> news;
    private static LayoutInflater inflater=null;

    public LazyAdapter(Activity activity,ArrayList<NewsListBean.NewsDataBean> data){
        a=activity;
        news=data;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(a.getApplicationContext());
    }
    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if(convertView==null){
            v= inflater.inflate(R.layout.news_list_item,null);
        }else{
            v=(View)convertView;
        }
        ImageView img = (ImageView) v.findViewById(R.id.news_item_img);
        TextView tv_title = (TextView) v.findViewById(R.id.news_ietm_title);
        TextView tv_time = (TextView) v.findViewById(R.id.news_item_time);

        tv_title.setText(news.get(position).getTitle());
        tv_time.setText(news.get(position).getDate());
        imageLoader.DisplayImage(news.get(position).getThumbnail_pic_s(),img);
        return v;
    }
}
