package com.debla.minicomp.APIs;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.debla.minicomp.Activity.ServiceActivities.NewsIndexActivity;
import com.debla.minicomp.Structs.NewsListBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dave-PC on 2017/4/6.
 */

public class GetNewsList implements APIgetter {
    private OkHttpClient client;
    private Request.Builder builder;
    private Request request;
    private Call call;
    private Gson gson;
    private int status=0;
    private final String key="d17d97ab43b2520c565bba2b53ad95d9";
    private String urlPath="http://v.juhe.cn/toutiao/index";
    private String type[]={"top","shehui","guonei","guoji","yule","tiyu","junshi","keji","caijing","shishang"};
    private int i_type=0;
    private String json_result;

    public ArrayList<NewsListBean.NewsDataBean> getData() {
        return data;
    }

    public void setData(ArrayList<NewsListBean.NewsDataBean> data) {
        this.data = data;
    }

    private ArrayList<NewsListBean.NewsDataBean> data;



    public NewsListBean getNews_list() {
        return news_list;
    }

    public void setNews_list(NewsListBean news_list) {
        this.news_list = news_list;
    }

    private NewsListBean news_list;
    //新闻头条类型
    @Override
    public void init() {
        client = new OkHttpClient();
        urlPath=urlPath+"?type="+type[getI_type()]+"&key="+key;
        post(urlPath);
    }

    public int getI_type(){
        return i_type;
    }

    public void setI_type(int index){
        i_type=index;
    }

    @Override
    public String post(String apiurl) {
        builder = new Request.Builder().url(apiurl);
        request = builder.build();
        call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                status=-1;
                Log.e("debug","onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    json_result=response.body().string();
                    Analize(json_result);
                }
            }
        });
        return null;
    }

    @Override
    public void Analize(String JSON) {
        gson = new Gson();
        news_list = gson.fromJson(JSON,NewsListBean.class);
        data=news_list.getResult().getData();
        Message msg = new Message();
        msg.obj=data;
        msg.what=3;
        Handler handler = NewsIndexActivity.handler;
        handler.sendMessage(msg);
    }
}
