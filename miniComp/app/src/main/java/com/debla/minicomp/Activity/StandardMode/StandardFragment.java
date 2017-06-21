package com.debla.minicomp.Activity.StandardMode;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.debla.minicomp.Activity.Contacts.ContactsActivity;
import com.debla.minicomp.Activity.Public.AlmancActivity;
import com.debla.minicomp.Activity.Public.AppListActivity;
import com.debla.minicomp.Activity.Public.ControlActivity;
import com.debla.minicomp.Activity.Public.HelpActivity;
import com.debla.minicomp.Activity.Public.SOSActivity;
import com.debla.minicomp.Activity.Public.ServiceActivity;
import com.debla.minicomp.Activity.Public.SettingActivity;
import com.debla.minicomp.Activity.Public.SmsActivity;
import com.debla.minicomp.Activity.Public.ToolsActivity;
import com.debla.minicomp.Activity.Public.VibratorUtil;
import com.debla.minicomp.Activity.Public.WeatherActivity;
import com.debla.minicomp.Activity.SimpleMode.SimDailActivity;
import com.debla.minicomp.Structs.ItemView;
import com.debla.minicomp.minicomp.R;
import com.iflytek.speech.SynthesizerPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.debla.minicomp.minicomp.R.id.myGridView;


/**
 * Created by Dave-PC on 2017/2/22.
 */

public class StandardFragment extends Fragment {
    private ImageView[] imageViews;
    private ImageView imageView;
    private LayoutInflater mInflater;
    private LinearLayout groupViewLl;
    private MyViewPager viewPager;

    private DragGridView mGridView_1 = null;            //第一页的GridView,第二页不需要用到GridView
    private DragGridView mGridView_2 = null;            //第二页的GridView
    private DragGridView mGridView_3 = null;            //第三页的GridView
    private DragGridView mGridView_4 = null;            //第三页的GridView
    private List<ItemView> mViewList_one;                   //第一页要绑定数据的List
    private List<ItemView> mViewList_two;                   //第二页要绑定数据的List
    private List<ItemView> mViewList_three;                   //第二页要绑定数据的List
    private List<ItemView> mViewList_four;                  //第四页要绑定数据的List


    private SynthesizerPlayer player;
    private static final String APPID = "appid=519328ab";

    private List<View> viewList = new ArrayList<View>();


    private int[] picRes1 = new int[]{                   //第一页要加载的图标列表，都是测试的到时候再加一个数组
            R.drawable.ceshi,R.drawable.ceshi,
            R.drawable.ceshi,R.drawable.ceshi,
            R.drawable.sms,R.drawable.ceshi,
            R.drawable.ceshi,R.drawable.ceshi,
    };
    private int[] picRes2 = new int[]{                   //第一页要加载的图标列表，都是测试的到时候再加一个数组
            R.drawable.sos,R.drawable.control,
            R.drawable.familar,R.drawable.add,
            R.drawable.familar,R.drawable.add,
            R.drawable.add,R.drawable.contacts,
    };
    private int[] picRes3 = new int[]{                   //第一页要加载的图标列表，都是测试的到时候再加一个数组
            R.drawable.mall,R.drawable.weather_qing,
            R.drawable.general,R.drawable.camera,
            R.drawable.phone,R.drawable.sms,
    };
    private int[] picRes4 = new int[]{                   //第一页要加载的图标列表，都是测试的到时候再加一个数组
            R.drawable.album,R.drawable.internet,
            R.drawable.calendar,R.drawable.tool,
            R.drawable.service,R.drawable.applications,
            R.drawable.help,R.drawable.setting,
    };
    private int[] viewColor_one = new int[]{                //第一页的配色列表
            Color.parseColor("#5B2D90"),Color.parseColor("#45B0FF"),
            Color.parseColor("#45B0FF"),Color.parseColor("#BBD80A"),
            Color.parseColor("#BBD80A"),Color.parseColor("#FFB901"),
            Color.parseColor("#45B0FF"),Color.parseColor("#CD1515")
    };
    private int[] viewColor_two = new int[]{                //第二页的配色列表
            Color.parseColor("#CD1515"),Color.parseColor("#333333"),
            Color.parseColor("#FFB901"),Color.parseColor("#45B0FF"),
            Color.parseColor("#45B0FF"),Color.parseColor("#BBD80A"),
            Color.parseColor("#5B2D90"),Color.parseColor("#FFB901")
    };
    private int[] viewColor_three = new int[]{                //第三页的配色列表
            Color.parseColor("#CD1515"),Color.parseColor("#00204F"),
            Color.parseColor("#BBD80A"),Color.parseColor("#45B0FF"),
            Color.parseColor("#45B0FF"),Color.parseColor("#FFB901")
    };
    private int[] viewColor_four = new int[]{                //第四页的配色列表
            Color.parseColor("#FFB901"),Color.parseColor("#45B0FF"),
            Color.parseColor("#45B0FF"),Color.parseColor("#BBD80A"),
            Color.parseColor("#CD1515"),Color.parseColor("#FFB901"),
            Color.parseColor("#5B2D90"),Color.parseColor("#05668D")
    };
    private String[] itemTitle_one = new String[]{
            "暂定","暂定","暂定","暂定",
            "暂定","暂定","暂定","暂定"
    };
    private String[] itemTitle_two = new String[]{
            "紧急求助","控制中心","家人","添加",
            "朋友","添加","添加","联系人"
    };
    private String[] itemTitle_three = new String[]{
            "微商城","天气","常用软件","相机","电话","短信"
    };
    private String[] itemTitle_four = new String[]{
            "相册","上网","老黄历","工具",
            "服务","所有应用","使用帮助","设置"
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View switchView = inflater.inflate(R.layout.fragment_other,container,false);
        mInflater = inflater;
        groupViewLl = (LinearLayout) switchView.findViewById(R.id.viewGroup2);
        viewPager = (MyViewPager) switchView.findViewById(R.id.viewPager2);
        player = SynthesizerPlayer.createSynthesizerPlayer(getActivity(), APPID);
        player.setVoiceName("vivixiaoyan");
        return switchView;
    }

    private void initList(){
        mViewList_one = new ArrayList<ItemView>();
        mViewList_two = new ArrayList<ItemView>();
        mViewList_three = new ArrayList<ItemView>();
        mViewList_four = new ArrayList<ItemView>();
        for(int i=0;i<picRes1.length;i++){               //每一页应用数量都是一样的，所以放在一起写了
            ItemView iv_1 = new ItemView();
            ItemView iv_2 = new ItemView();
            ItemView iv_4 = new ItemView();
            iv_1.setId(i+1);iv_2.setId(i+1);iv_4.setId(i+1);
            iv_1.setColor(viewColor_one[i]);
            iv_2.setColor(viewColor_two[i]);
            iv_4.setColor(viewColor_four[i]);
            iv_1.setItem_img(picRes1[i]);
            iv_2.setItem_img(picRes2[i]);            //到时候这里别忘了改
            iv_4.setItem_img(picRes4[i]);
            iv_1.setItem_title(itemTitle_one[i]);
            iv_2.setItem_title(itemTitle_two[i]);
            iv_4.setItem_title(itemTitle_four[i]);
            mViewList_one.add(iv_1);
            mViewList_two.add(iv_2);
            mViewList_four.add(iv_4);
        }
        for(int i=0;i<picRes3.length;i++){
            ItemView iv_3 = new ItemView();
            iv_3.setId(i+1);
            iv_3.setColor(viewColor_three[i]);
            iv_3.setItem_img(picRes3[i]);
            iv_3.setItem_title(itemTitle_three[i]);
            mViewList_three.add(iv_3);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View oneView = mInflater.inflate(R.layout.standard_v1, null);
        viewList.add(oneView);
        final View twoView = mInflater.inflate(R.layout.standard_v2, null);
        viewList.add(twoView);
        final View threeView = mInflater.inflate(R.layout.standard_v3, null);
        viewList.add(threeView);
        final View fourView = mInflater.inflate(R.layout.standard_v4, null);
        viewList.add(fourView);


        initList();
        mGridView_1 = (DragGridView)oneView.findViewById(myGridView);
        mGridView_2 = (DragGridView)twoView.findViewById(myGridView);
        mGridView_3 = (DragGridView)threeView.findViewById(myGridView);
        mGridView_4 = (DragGridView)fourView.findViewById(myGridView);
        mGridView_1.setAdapter(new GridViewAdapter(getActivity(),mViewList_one));
        mGridView_2.setAdapter(new GridViewAdapter(getActivity(),mViewList_two));
        mGridView_3.setAdapter(new GridViewAdapter(getActivity(),mViewList_three));
        mGridView_4.setAdapter(new GridViewAdapter(getActivity(),mViewList_four));



        mGridView_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridViewAdapter adapter = (GridViewAdapter) mGridView_1.getAdapter();
                List<ItemView> list = adapter.getList();
                int vid = list.get(position).getId();
                switch (vid){           //第一页暂定
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                }
            }
        });

        mGridView_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridViewAdapter adapter = (GridViewAdapter) mGridView_1.getAdapter();
                List<ItemView> list = adapter.getList();
                int vid = list.get(position).getId();
                switch (vid){
                    case 1:
                        Log.e("debug","紧急求助");
                        VibratorUtil.Vibrate(getActivity(), 100);
                        player.playText("紧急求助","ent=vivi21,bft=5",null);
                        Intent intent = new Intent(getActivity(), SOSActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        Log.e("debug","控制中心");
                        player.playText("控制中心","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), ControlActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Log.e("debug","家人");
                        player.playText("家人","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 4:
                        Log.e("debug","添加");
                        player.playText("添加","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 5:
                        Log.e("debug","朋友");
                        player.playText("朋友","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 6:
                        Log.e("debug","添加");
                        player.playText("添加","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 7:
                        Log.e("debug","添加");
                        player.playText("添加","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 8:
                        Log.e("debug","联系人");
                        player.playText("联系人","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), ContactsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        mGridView_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridViewAdapter adapter = (GridViewAdapter) mGridView_1.getAdapter();
                List<ItemView> list = adapter.getList();
                Intent intent;
                int vid = list.get(position).getId();
                switch (vid){
                    case 1:
                        Log.e("debug","微商城");
                        player.playText("微商城","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 2:
                        Log.e("debug","天气");
                        player.playText("天气","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), WeatherActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Log.e("debug","微信");
                        player.playText("微信","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 4:
                        Log.e("debug","相机");
                        player.playText("相机","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent=new Intent();
                        intent.setAction("android.media.action.IMAGE_CAPTURE");
                        intent.addCategory("android.intent.category.DEFAULT");
                        //保存照片到指定的路径
                        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        Uri uri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivity(intent);
                        break;
                    case 5:
                        Log.e("debug","电话");
                        player.playText("电话","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), SimDailActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        Log.e("debug","短信");
                        player.playText("短信","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), SmsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });


        mGridView_4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridViewAdapter adapter = (GridViewAdapter) mGridView_1.getAdapter();
                List<ItemView> list = adapter.getList();
                Intent intent;
                int vid = list.get(position).getId();
                switch (vid){
                    case 1:
                        Log.e("debug","相册");
                        player.playText("相册","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 2:
                        Log.e("debug","上网");
                        player.playText("上网","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("http://m.baidu.com");
                        intent.setData(content_url);
                        startActivity(intent);
                        break;
                    case 3:
                        Log.e("debug","老黄历");
                        player.playText("老黄历","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), AlmancActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        Log.e("debug","工具");
                        player.playText("工具","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), ToolsActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        Log.e("debug","服务");
                        player.playText("服务","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), ServiceActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        Log.e("debug","所有应用");
                        player.playText("所有应用","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), AppListActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        Log.e("debug","使用帮助");
                        player.playText("使用帮助","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), HelpActivity.class);
                        startActivity(intent);
                        break;
                    case 8:
                        Log.e("debug","设置");
                        player.playText("设置","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), SettingActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        imageViews = new ImageView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            imageView = new ImageView(this.getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            imageView.setPadding(20, 0, 20, 0);
            imageViews[i] = imageView;

            if (i == 2) {
                // 默认选中第一张图
                imageViews[i]
                        .setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);
            }

            groupViewLl.addView(imageViews[i]);
        }
        viewPager.setAdapter(new MyPagerAdapter(viewList));
        viewPager.setCurrentItem(2);
        viewPager.setOnPageChangeListener(new SwitchPageChangeListener());
    }


    class SwitchPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.drawable.page_indicator_focused);

                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.page_indicator);
                }
            }
        }
    }


}
