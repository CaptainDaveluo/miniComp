package com.debla.minicomp.Activity.ServiceActivities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.debla.minicomp.minicomp.R;

/**
 * Created by Dave-PC on 2017/4/16.
 */

public class NovelActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookshelf);
    }
}
