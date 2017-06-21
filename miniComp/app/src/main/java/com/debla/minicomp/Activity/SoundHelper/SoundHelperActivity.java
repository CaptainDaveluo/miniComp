package com.debla.minicomp.Activity.SoundHelper;

import android.app.Activity;
import android.os.Bundle;

import com.debla.minicomp.minicomp.R;
import com.iflytek.speech.SynthesizerPlayer;

/**
 * Created by Dave-PC on 2017/2/27.
 */

public class SoundHelperActivity extends Activity{
    private static SynthesizerPlayer player;
    private static final String APPID = "appid=519328ab";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soundhelper);
        player = SynthesizerPlayer.createSynthesizerPlayer(SoundHelperActivity.this, APPID);
        player.setVoiceName("vivixiaoyan");
        player.playText("主人，按住中间的按钮可以和我说话哦","ent=vivi21,bft=5",null);
    }
}
