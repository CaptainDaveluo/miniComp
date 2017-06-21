package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;

import com.debla.minicomp.Activity.ToolActivities.FlashLightActivity;
import com.debla.minicomp.Activity.ToolActivities.HealthyActivity;
import com.debla.minicomp.Activity.ToolActivities.MagnifierActivity;
import com.debla.minicomp.minicomp.R;

/**
 * Created by Dave-PC on 2017/2/22.
 */

public class ToolsActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools_activity);
    }

    public void btnLight(View v){
        Intent it = new Intent(ToolsActivity.this, FlashLightActivity.class);
        startActivity(it);
    }

    public void btnMagnifier(View v){
        Intent it = new Intent(ToolsActivity.this, MagnifierActivity.class);
        startActivity(it);
    }

    public void btnCalculator(View v){
        Intent intent = new Intent();
        intent.setClassName("com.android.calculator2","com.android.calculator2.Calculator");
        startActivity(intent);
    }

    public void btnAlarm(View v){
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        startActivity(intent);
    }

    public void btnMusic(View v){
        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
        startActivity(intent);
    }

    public void btnHealthy(View v){
        Intent it = new Intent(ToolsActivity.this, HealthyActivity.class);
        startActivity(it);
    }
}
