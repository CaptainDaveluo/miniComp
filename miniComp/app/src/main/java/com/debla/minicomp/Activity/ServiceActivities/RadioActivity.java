package com.debla.minicomp.Activity.ServiceActivities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.debla.minicomp.minicomp.R;

/**
 * Created by Dave-PC on 2017/4/9.
 */

public class RadioActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio);
    }

    public void btnRadio_exit(View v){
        this.finish();
    }
}
