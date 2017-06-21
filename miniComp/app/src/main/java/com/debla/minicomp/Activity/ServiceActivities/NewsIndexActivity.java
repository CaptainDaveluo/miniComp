package com.debla.minicomp.Activity.ServiceActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.debla.minicomp.APIs.GetNewsList;
import com.debla.minicomp.Structs.NewsListBean;
import com.debla.minicomp.minicomp.R;

import java.util.ArrayList;

/**
 * Created by Dave-PC on 2017/4/6.
 */

public class NewsIndexActivity extends Activity{
    private static ListView lv_newslist;
    private static ArrayList<NewsListBean.NewsDataBean> news;
    private static LazyAdapter adapter;
    private static Activity a;
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==3){
                news = (ArrayList<NewsListBean.NewsDataBean>)msg.obj;
                if(news==null)
                    Log.e("debug","news is null");
                adapter = new LazyAdapter(a,news);
                lv_newslist.setAdapter(adapter);

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);
        lv_newslist = (ListView) findViewById(R.id.news_list_lv);
        GetNewsList newslist = new GetNewsList();
        a=this;
        newslist.init();
        //news=newslist.getData();
        //adapter = new LazyAdapter(this,news);
        //lv_newslist.setAdapter(adapter);
        lv_newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LazyAdapter adapter = (LazyAdapter)parent.getAdapter();
                String url=adapter.news.get(position).getUrl();
                Intent intent = new Intent(NewsIndexActivity.this,NewsActivity.class);
                intent.putExtra("urlPath",url);
                startActivity(intent);
            }
        });
    }
}
