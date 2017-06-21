package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.widget.TextView;

import com.debla.minicomp.APIs.GetAlmanc;
import com.debla.minicomp.minicomp.R;
import com.iflytek.speech.SynthesizerPlayer;

/**
 * Created by Dave-PC on 2017/2/16.
 * 老黄历的Activity
 * 需要引入APIs包中的类来获取数据
 */

public class AlmancActivity extends Activity {

    private static TextView tv_ji;
    private static TextView tv_yi;
    private static TextView tv_yangli;
    private static TextView tv_yinli;
    private static SynthesizerPlayer player;
    private TextView tv_week;
    private static final String APPID = "appid=519328ab";
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bd = msg.getData();
            String ji = bd.getString("ji");
            String yi = bd.getString("yi");
            String yangli = bd.getString("yangli");
            yangli = yangli.substring(5,yangli.length());
            String yinli = bd.getString("yinli");
            tv_ji.setText(ji);
            tv_yi.setText(yi);
            tv_yangli.setText(yangli);
            tv_yinli.setText(yinli);
            player.playText("今天是"+yinli+"忌:"+ji+"，宜:"+yi,"ent=vivi21,bft=5",null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.almanac);
        tv_ji = (TextView) findViewById(R.id.almanac_ji);
        tv_yi = (TextView) findViewById(R.id.almanac_yi);
        tv_yangli = (TextView) findViewById(R.id.almanac_yangli);
        tv_yinli = (TextView) findViewById(R.id.almanac_yinli);
        tv_week = (TextView) findViewById(R.id.almanac_week);
        Time t = new Time();
        t.setToNow();
        int weekNum = t.weekDay;
        String [] week ={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        String weekDay = week[weekNum];
        tv_week.setText(weekDay);
        player = SynthesizerPlayer.createSynthesizerPlayer(AlmancActivity.this, APPID);
        player.setVoiceName("vivixiaoyan");
        GetAlmanc almanc = new GetAlmanc(t);
        almanc.init();
    }
}
