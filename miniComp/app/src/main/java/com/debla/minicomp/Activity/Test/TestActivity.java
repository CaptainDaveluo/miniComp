package com.debla.minicomp.Activity.Test;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.debla.minicomp.Structs.ItemView;
import com.debla.minicomp.Activity.SimpleMode.DragGridView;
import com.debla.minicomp.minicomp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave-PC on 2017/2/13.
 */

public class TestActivity extends Activity{
    private DragGridView myGridView = null;
    private List<ItemView> mViewList;
    private int[] picRes = new int[]{
            R.drawable.ceshi,R.drawable.ceshi,
            R.drawable.ceshi,R.drawable.ceshi,
            R.drawable.ceshi,R.drawable.ceshi
    };
    private int[] viewColor = new int[]{
            Color.parseColor("#CD1515"),Color.parseColor("#333333"),
            Color.parseColor("#FFB901"),Color.parseColor("#BBD80A"),
            Color.parseColor("#BBD80A"),Color.parseColor("#45B0FF")
    };
    private String[] itemTitle = new String[]{
            "紧急求助","控制中心","家人","添加","短信","联系人"
    };

    private void initList(){
        mViewList = new ArrayList<ItemView>();
        for(int i=0;i<picRes.length;i++){
            ItemView iv = new ItemView();
            iv.setColor(viewColor[i]);
            iv.setItem_img(picRes[i]);
            iv.setItem_title(itemTitle[i]);
            mViewList.add(iv);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.simple_test);
    }
}
