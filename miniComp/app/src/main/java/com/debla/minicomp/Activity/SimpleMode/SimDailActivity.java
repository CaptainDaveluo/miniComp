package com.debla.minicomp.Activity.SimpleMode;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.debla.minicomp.minicomp.R;
import com.iflytek.speech.SynthesizerPlayer;

/**
 * Created by Dave-PC on 2017/2/15.
 */

public class SimDailActivity extends Activity {
    private TextView showText;
    private static SynthesizerPlayer player;
    private static final String APPID = "appid=519328ab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_two_dial);
        showText = (TextView) findViewById(R.id.zhongjian);
        player = SynthesizerPlayer.createSynthesizerPlayer(SimDailActivity.this, APPID);
        player.setVoiceName("vivixiaoyan");
    }

    public void btnOne(View v) {
        String text = showText.getText().toString();
        text = text + "1";
        showText.setText(text);
        player.playText("妖","ent=vivi21,bft=5",null);
    }

    public void btnTwo(View v) {
        String text = showText.getText().toString();
        text = text + "2";
        showText.setText(text);
        player.playText("2","ent=vivi21,bft=5",null);
    }

    public void btnThree(View v) {
        String text = showText.getText().toString();
        text = text + "3";
        showText.setText(text);
        player.playText("3","ent=vivi21,bft=5",null);
    }

    public void btnFour(View v) {
        String text = showText.getText().toString();
        text = text + "4";
        showText.setText(text);
        player.playText("4","ent=vivi21,bft=5",null);
    }

    public void btnFive(View v) {
        String text = showText.getText().toString();
        text = text + "5";
        showText.setText(text);
        player.playText("5","ent=vivi21,bft=5",null);
    }

    public void btnSix(View v) {
        String text = showText.getText().toString();
        text = text + "6";
        showText.setText(text);
        player.playText("6","ent=vivi21,bft=5",null);
    }

    public void btnSeven(View v) {
        String text = showText.getText().toString();
        text = text + "7";
        showText.setText(text);
        player.playText("7","ent=vivi21,bft=5",null);
    }

    public void btnEight(View v) {
        String text = showText.getText().toString();
        text = text + "8";
        showText.setText(text);
        player.playText("8","ent=vivi21,bft=5",null);
    }

    public void btnNine(View v) {
        String text = showText.getText().toString();
        text = text + "9";
        showText.setText(text);
        player.playText("9","ent=vivi21,bft=5",null);
    }

    public void btnZero(View v) {
        String text = showText.getText().toString();
        text = text + "0";
        showText.setText(text);
        player.playText("0","ent=vivi21,bft=5",null);
    }

    public void btnStar(View v) {
        String text = showText.getText().toString();
        text = text + "*";
        showText.setText(text);
        player.playText("星号","ent=vivi21,bft=5",null);
    }

    public void btnSharp(View v) {
        String text = showText.getText().toString();
        text = text + "#";
        showText.setText(text);
        player.playText("井号","ent=vivi21,bft=5",null);
    }

    public void btnDel(View v){
        String text = showText.getText().toString().trim();
        if(text!=null&&!"".equals(text)){
            text=text.substring(0,text.length()-1);
            Log.e("debug",text);
        }
        showText.setText(text);
    }

    public void btnDial(View v) {
        String phone_num = showText.getText().toString().trim();
        if (phone_num != null && !phone_num.equals("")) {
            //封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone_num));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                Toast.makeText(this,"您关闭了拨号权限",Toast.LENGTH_SHORT).show();
                return;
            }
            startActivity(intent);
        }
    }
}
