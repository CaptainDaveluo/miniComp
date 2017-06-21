package com.debla.minicomp.Structs;

import java.util.ArrayList;

/**
 * Created by Dave-PC on 2017/2/19.
 */

public class WeatherDataEntity {
    public String currentCity;
    public String pm25;
    public ArrayList<Index> index;
    public ArrayList<Weather_data> weather_data;
    public class Index{
        public String title;
        public String zs;
        public String tipt;
        public String des;
    }
    public class Weather_data{
        public String date;
        public String dayPictureUrl;
        public String nightPictureUrl;
        public String weather;
        public String wind;
        public String temperature;
    }
}
