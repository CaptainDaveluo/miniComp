package com.debla.minicomp.Activity.ServiceActivities;

import android.app.Activity;
import android.os.Bundle;

import com.debla.minicomp.Structs.CircleProgressBar;
import com.debla.minicomp.minicomp.R;

/**
 * Created by Dave-PC on 2017/4/8.
 * 计步器界面
 */

public class FootCountActivity extends Activity{
    private CircleProgressBar stepBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foot_count);
        stepBar = (CircleProgressBar) findViewById(R.id.foot_count_step);
        stepBar.setMaxProgress(3000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<=1230;i++){
                    try {
                        Thread.sleep(1);
                        stepBar.setProgressNotInUiThread(i);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
