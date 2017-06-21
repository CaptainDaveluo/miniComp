package com.debla.minicomp.Activity.ToolActivities;

import android.app.Activity;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.debla.minicomp.minicomp.R;

/**
 * Created by Dave-PC on 2017/3/29.
 */

public class FlashLightActivity extends Activity{
    private LinearLayout view;
    private ToggleButton tbtn_switch;
    private Camera m_Camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flashlight);
        tbtn_switch = (ToggleButton) findViewById(R.id.flashlight_switch);
        view = (LinearLayout) findViewById(R.id.flashlight_bg);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        init();
        tbtn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lightswitch(isChecked);
            }
        });
    }
    public void init(){
        PackageManager pm = this.getPackageManager();
        FeatureInfo[] features = pm.getSystemAvailableFeatures();
        view.setBackgroundResource(R.drawable.light_on);
        tbtn_switch.setBackgroundResource(R.drawable.light_on_button);
        for (FeatureInfo f : features) {
            if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) // 判断设备是否支持闪光灯
            {
                if (null == m_Camera) {
                    m_Camera = Camera.open();
                }
                Camera.Parameters parameters = m_Camera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                m_Camera.setParameters(parameters);
                m_Camera.startPreview();
                return;
            }
        }
        Toast.makeText(getApplicationContext(),"您的手机不支持闪光灯",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lightswitch(false);
    }


    public void lightswitch(boolean isChecked){
        if (isChecked) {
            if (null == m_Camera) {
                m_Camera = Camera.open();
            }
            view.setBackgroundResource(R.drawable.light_on);
            tbtn_switch.setBackgroundResource(R.drawable.light_on_button);
            Camera.Parameters parameters = m_Camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            m_Camera.setParameters(parameters);
            m_Camera.startPreview();
        } else {
            view.setBackgroundResource(R.drawable.light_off);
            tbtn_switch.setBackgroundResource(R.drawable.light_off_button);
            if (m_Camera != null) {
                m_Camera.stopPreview();
                m_Camera.release();
                m_Camera = null;
            }
        }
    }

}
