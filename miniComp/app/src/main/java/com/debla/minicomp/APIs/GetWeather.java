package com.debla.minicomp.APIs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.debla.minicomp.Activity.SimpleMode.SimpleFragment;
import com.debla.minicomp.Structs.WeatherDataEntity;
import com.debla.minicomp.Structs.WeatherEntity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dave-PC on 2017/2/19.
 */

public class GetWeather implements APIgetter{
    private String city = "嘉兴";
    private String key="Em8bLaBbUcQ1CnKjcYxbSO0UH8hwugCu";
    private String mcode="55:42:F5:75:32:28:A8:5D:30:04:98:C7:4D:12:65:B1:7E:71:F9:9F;com.example.weatherdemo";
    private OkHttpClient client;
    private String urlPath="http://api.map.baidu.com/telematics/v3/weather?";
    private Gson gson;
    private Request.Builder builder;
    private Request request;
    private Call call;
    private String jsonResult;
    private Handler handler;
    public static ArrayList<WeatherDataEntity> result;
    public static ArrayList<WeatherDataEntity.Weather_data> data;

    public String getWeather() {
        return weather;
    }

    public String getWind() {
        return wind;
    }

    public String getTemperature() {
        return temperature;
    }

    private String weather;
    private String wind;
    private String temperature;

    public String getPm_25() {
        return pm_25;
    }

    private String pm_25;

    public String getCurrent_temp() {
        return current_temp;
    }

    private String current_temp;

    @Override
    public void init() {
        client = new OkHttpClient();
        urlPath=urlPath+"location="+ city+"&output=json&ak="+key+"&mcode="+mcode;
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
                    jsonResult=response.body().string();
                    Analize(jsonResult);
                    Message msg = new Message();
                    Bundle bd = new Bundle();
                    bd.putString("weather",getWeather());
                    bd.putString("wind",getWind());
                    bd.putString("temperature",getTemperature());
                    bd.putString("today_image",data.get(0).dayPictureUrl);
                    bd.putString("tomorrow_weather",data.get(1).weather);
                    bd.putString("tomorrow_temp",data.get(1).temperature);
                    bd.putString("tomorrow_image",data.get(1).temperature);
                    msg.what=3;
                    msg.obj=bd;
                    SimpleFragment.handler.sendMessage(msg);
                }
            }
        });
        return null;
    }



    @Override
    public void Analize(String JSON) {
        gson = new Gson();
        result = gson.fromJson(JSON,WeatherEntity.class).results;
        pm_25 = result.get(0).pm25;
        data = result.get(0).weather_data;
        temperature=data.get(0).temperature;
        weather=data.get(0).weather;
        wind=data.get(0).wind;
    }
}
