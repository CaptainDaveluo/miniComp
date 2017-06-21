package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.debla.minicomp.minicomp.R;

/**
 * Created by Dave-PC on 2017/2/21.
 */

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_three_setting);
    }

    public void btnDesktop(View v){
        Intent intent = new Intent(SettingActivity.this,SetDesktop.class);
        startActivity(intent);
    }

    public void btnLogin(View v){
        Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    public void btnSettingBack(View v){
        this.finish();
    }
}
