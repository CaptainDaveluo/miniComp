package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.debla.minicomp.Activity.GuideActivity.MainActivity;
import com.debla.minicomp.minicomp.R;
import com.iflytek.speech.SynthesizerPlayer;

/**
 * Created by Dave-PC on 2017/2/22.
 */

public class SOSActivity extends Activity {
    private static TextView tv_location;
    public static boolean ActivityStatus=false;
    private static SynthesizerPlayer player;
    private static final String APPID = "appid=519328ab";
    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String strAddr =(String) msg.obj;
            tv_location.setText(strAddr);
            player.playText("您当前位于"+strAddr,"ent=vivi21,bft=5",null);
            MainActivity.mLocClient.stop();
        }
    };

    @Override
    protected void onResume() {
        MainActivity.mLocClient.start();
        ActivityStatus=true;
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_one_sos);
        player = SynthesizerPlayer.createSynthesizerPlayer(SOSActivity.this, APPID);
        player.setVoiceName("vivixiaoyan");
        ActivityStatus=true;
        tv_location = (TextView) findViewById(R.id.sos_location);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityStatus=false;
    }
}
