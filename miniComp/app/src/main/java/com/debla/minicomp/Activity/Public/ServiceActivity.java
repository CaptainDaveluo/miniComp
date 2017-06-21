package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.debla.minicomp.Activity.ServiceActivities.FootCountActivity;
import com.debla.minicomp.Activity.ServiceActivities.NewsIndexActivity;
import com.debla.minicomp.Activity.ServiceActivities.NovelActivity;
import com.debla.minicomp.Activity.ServiceActivities.RadioActivity;
import com.debla.minicomp.Activity.ServiceActivities.VedioIndex;
import com.debla.minicomp.minicomp.R;

/**
 * Created by King on 2017/2/25.
 */

public class ServiceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);
    }

    public void btnReadNews(View v){
        Intent it = new Intent(ServiceActivity.this, NewsIndexActivity.class);
        startActivity(it);
    }

    public void btnFootCount(View v){
        Intent it = new Intent(ServiceActivity.this, FootCountActivity.class);
        startActivity(it);
    }

    public void btnRadio(View v){
        Intent it = new Intent(ServiceActivity.this, RadioActivity.class);
        startActivity(it);
    }

    public void btnWatchVedio(View v){
        Intent it = new Intent(ServiceActivity.this, VedioIndex.class);
        startActivity(it);
    }

    public void btnNovel(View v){
        Intent it = new Intent(ServiceActivity.this, NovelActivity.class);
        startActivity(it);
    }
}
