package com.debla.minicomp.APIs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;

import com.debla.minicomp.Activity.Public.AlmancActivity;
import com.debla.minicomp.Structs.AlmancResult;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dave-PC on 2017/2/16.
 * 获取老黄历数据和解析的类
 */

public class GetAlmanc implements APIgetter {
    private OkHttpClient client;
    private Gson gson;
    private Request.Builder builder;
    private Request request;
    private Call call;
    private AlmancResult result;
    private String ji;          //忌
    private String yi;          //宜
    private String yangli;      //阳历
    private String yinli;       //阴历
    private Time date;          //传入的时间
    private String key="647acfa738f20186f6479e67ef89cc11";         //API key
    private String urlPath="http://v.juhe.cn/laohuangli/d?date=";     //API地址
    private String jsonResult;
    private Handler handler;

    public GetAlmanc(Time date) {
        this.date = date;
    }

    public String getJi() {
        return ji;
    }

    public String getYi() {
        return yi;
    }

    @Override
    public void init() {            //初始化Almanc类
        client = new OkHttpClient();
        String time=date.format("%Y-%m-%d");
        urlPath=urlPath+time+"&key="+key;
        String jsonResult=post(urlPath);
    }

    @Override
    public String post(String apiurl) {
        builder = new Request.Builder().url(apiurl);
        request = builder.build();
        call= client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("debug","onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    jsonResult= response.body().string();
                    Analize(jsonResult);
                    handler = AlmancActivity.handler;
                    Message msg = new Message();
                    Bundle bd = new Bundle();
                    bd.putString("ji",getJi());
                    bd.putString("yi",getYi());
                    bd.putString("yangli",getYangli());
                    bd.putString("yinli",getYinli());
                    msg.setData(bd);
                    handler.sendMessage(msg);
                }
            }
        });
        return null;
    }

    @Override
    public void Analize(String JSON) {
        Log.e("debug","analize"+JSON);
        gson = new Gson();
        result = gson.fromJson(JSON,AlmancResult.class);
        ji=result.getResult().getJi();
        yi=result.getResult().getYi();
        yangli = result.getResult().getYangli();
        yinli = result.getResult().getYinli();
    }

    public String getYinli() {
        return yinli;
    }

    public void setYinli(String yinli) {
        this.yinli = yinli;
    }

    public String getYangli() {
        return yangli;
    }

    public void setYangli(String yangli) {
        this.yangli = yangli;
    }
}
