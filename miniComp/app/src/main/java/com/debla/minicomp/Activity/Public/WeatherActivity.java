package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.TextView;

import com.debla.minicomp.APIs.GetWeather;
import com.debla.minicomp.Structs.WeatherDataEntity;
import com.debla.minicomp.minicomp.R;
import com.iflytek.speech.SynthesizerPlayer;

import java.util.ArrayList;

/**
 * Created by Dave-PC on 2017/2/19.
 */

public class WeatherActivity extends Activity{
    private ArrayList<WeatherDataEntity> result;
    private ArrayList<WeatherDataEntity.Weather_data> data;
    private static SynthesizerPlayer player;
    private static final String APPID = "appid=519328ab";

    private TextView tv_weather;
    private TextView tv_date;
    private TextView tv_wind;
    private TextView tv_city;
    private TextView tv_tom_temp;
    private TextView tv_temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        player = SynthesizerPlayer.createSynthesizerPlayer(WeatherActivity.this, APPID);
        player.setVoiceName("vivixiaoyan");
        tv_weather = (TextView) findViewById(R.id.weather_weather);
        tv_city = (TextView) findViewById(R.id.weather_city);
        tv_date = (TextView) findViewById(R.id.weather_date);
        tv_wind = (TextView) findViewById(R.id.wind);
        tv_temp = (TextView) findViewById(R.id.weather_temp);
        tv_tom_temp = (TextView) findViewById(R.id.weather_tom_temp);
        if(GetWeather.result!=null&&GetWeather.data!=null){
            result=GetWeather.result;
            data=GetWeather.data;
            tv_city.setText(result.get(0).currentCity);
            tv_weather.setText(data.get(0).weather);
            tv_wind.setText(data.get(0).wind);
            tv_temp.setText(data.get(0).temperature);
            tv_tom_temp.setText(data.get(1).temperature);
            player.playText("今天的天气为"+data.get(0).weather+"，温度为"+data.get(0).temperature+","+data.get(0).wind,"ent=vivi21,bft=5",null);
        }
        Time t=new Time(); // or Time t=new Time("GMT+8");
        t.setToNow(); //
        int month = t.month+1;
        int date = t.monthDay;
        tv_date.setText(month+"月"+date+"日");

    }
}
