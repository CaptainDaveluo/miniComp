package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.debla.minicomp.minicomp.R;

/**
 * Created by Dave-PC on 2017/2/22.
 */

public class HelpActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sim_three_help);
    }

    public void btnHelpBack(View v){
        this.finish();
    }
}
