package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.debla.minicomp.Activity.GuideActivity.MainActivity;
import com.debla.minicomp.Activity.SimpleMode.SimpleFragment;
import com.debla.minicomp.minicomp.R;

/**
 * Created by Dave-PC on 2017/2/22.
 */

public class SetDesktop extends Activity {
    private TextView switch_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_three_setting_desktop);
        switch_mode = (TextView) findViewById(R.id.switch_mode);
        if(MainActivity.fragment instanceof SimpleFragment){
            switch_mode.setText("切换到标准模式");
        }else{
            switch_mode.setText("切换到简单模式");
        }
        switch_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.switchFragment();
                Log.e("debug","click");
                Toast.makeText(getApplicationContext(),"切换成功",Toast.LENGTH_SHORT).show();
                if("切换到标准模式".equals(switch_mode.getText().toString())){
                    switch_mode.setText("切换到简单模式");
                }else{
                    switch_mode.setText("切换到标准模式");
                }
            }
        });
    }

}
