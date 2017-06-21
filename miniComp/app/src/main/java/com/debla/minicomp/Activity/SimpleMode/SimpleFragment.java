package com.debla.minicomp.Activity.SimpleMode;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.format.Time;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.debla.minicomp.APIs.GetWeather;
import com.debla.minicomp.Activity.Contacts.ContactsActivity;
import com.debla.minicomp.Activity.GuideActivity.MainActivity;
import com.debla.minicomp.Activity.Public.AppListActivity;
import com.debla.minicomp.Activity.Public.ControlActivity;
import com.debla.minicomp.Activity.Public.HelpActivity;
import com.debla.minicomp.Activity.Public.LunarCalendar;
import com.debla.minicomp.Activity.Public.SOSActivity;
import com.debla.minicomp.Activity.Public.ServiceActivity;
import com.debla.minicomp.Activity.Public.SettingActivity;
import com.debla.minicomp.Activity.Public.SmsActivity;
import com.debla.minicomp.Activity.Public.ToolsActivity;
import com.debla.minicomp.Activity.Public.VibratorUtil;
import com.debla.minicomp.Activity.SoundHelper.SoundHelperActivity;
import com.debla.minicomp.Structs.ItemView;
import com.debla.minicomp.minicomp.R;
import com.iflytek.speech.SynthesizerPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.debla.minicomp.minicomp.R.id.myGridView;
import static com.debla.minicomp.minicomp.R.id.sim_app;
import static com.debla.minicomp.minicomp.R.id.sim_dial;

/**
 * Created by Dave-PC on 2017/2/12.
 */

public class SimpleFragment extends Fragment implements OnGestureListener {
    private LinearLayout groupViewLl;
    private MyViewPager viewPager;

    private ImageView[] imageViews;
    private ImageView imageView;

    private LinearLayout btn_dial;
    private LinearLayout btn_app;

    private TextView tv_date_time;
    private TextView tv_date_ying;
    private TextView tv_date_yang;
    private TextView tv_date_week;
    private static TextView tv_city;
    private static TextView tv_weather;
    private static String str_temp;
    private static Bundle weather_info;
    private static ImageView weather_image;

    private static String city;
    private static String weather;
    private static Bitmap bitmap;


    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;

    private SynthesizerPlayer player;
    private static final String APPID = "appid=519328ab";

    private List<View> viewList = new ArrayList<View>();
    private LayoutInflater mInflater;
    private DragGridView mGridView_1 = null;            //第一页的GridView,第二页不需要用到GridView
    private DragGridView mGridView_3 = null;            //第三页的GridView
    private List<ItemView> mViewList_one;                   //第一页要绑定数据的List
    private List<ItemView> mViewList_two;                   //第二页要绑定数据的List
    private int[] picRes = new int[]{                   //第一页要加载的图标列表，都是测试的到时候再加一个数组
            R.drawable.sos,R.drawable.control,
            R.drawable.familar,R.drawable.add,
            R.drawable.sms,R.drawable.contacts
    };
    private int[] picRes2 = new int[]{                   //第一页要加载的图标列表，都是测试的到时候再加一个数组
            R.drawable.camera,R.drawable.internet,
            R.drawable.tool,R.drawable.service,
            R.drawable.help,R.drawable.setting
    };
    private int[] viewColor_one = new int[]{                //第一页的配色列表
            Color.parseColor("#CD1515"),Color.parseColor("#333333"),
            Color.parseColor("#FFB901"),Color.parseColor("#BBD80A"),
            Color.parseColor("#BBD80A"),Color.parseColor("#45B0FF")
    };
    private int[] viewColor_two = new int[]{                //第三页的配色列表
            Color.parseColor("#FFB901"),Color.parseColor("#45B0FF"),
            Color.parseColor("#BBD80A"),Color.parseColor("#CD1515"),
            Color.parseColor("#5B2D90"),Color.parseColor("#05668D")
    };
    private String[] itemTitle_one = new String[]{
            "紧急求助","控制中心","家人","添加","短信","联系人"
    };
    private String[] itemTitle_two = new String[]{
            "相机","上网","工具","服务","使用帮助","设置"
    };

    private static Map<String,Integer> weather_img = new HashMap<String,Integer>();


    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==2) {
                MainActivity.mLocClient.stop();
                city=(String)msg.obj;
                tv_city.setText(city);
                GetWeather weather = new GetWeather();
                weather.init();
            }else if(msg.what==3){
                weather_info = (Bundle)msg.obj;
                weather=weather_info.getString("weather");
                str_temp=weather_info.getString("temperature");
                tv_weather.setText(weather_info.getString("temperature"));
                try {
                    weather_image.setImageResource(weather_img.get(weather));
                }catch (Exception e){
                    weather_image.setImageResource(weather_img.get("大雨"));  //没有对应图片资源
                }
                bitmap = ((BitmapDrawable)weather_image.getDrawable()).getBitmap();
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View switchView = inflater.inflate(R.layout.fragment_switch,container,false);
        mInflater = inflater;
        groupViewLl = (LinearLayout) switchView.findViewById(R.id.viewGroup);
        viewPager = (MyViewPager) switchView.findViewById(R.id.viewPager);
        player = SynthesizerPlayer.createSynthesizerPlayer(getActivity(), APPID);
        player.setVoiceName("vivixiaoyan");

        final GestureDetector mGestureDetector = new GestureDetector(getActivity(), this);
        MainActivity.MyOnTouchListener myOnTouchListener = new MainActivity.MyOnTouchListener(){
            @Override
            public boolean onTouch(MotionEvent ev) {
                boolean result = mGestureDetector.onTouchEvent(ev);
                return result;
            }
        };
        ((MainActivity)getActivity()).registerMyOnTouchListener(myOnTouchListener);
        return switchView;
    }



    private void initList(){
        mViewList_one = new ArrayList<ItemView>();
        mViewList_two = new ArrayList<ItemView>();
        for(int i=0;i<picRes.length;i++){               //每一页应用数量都是一样的，所以放在一起写了
            ItemView iv_1 = new ItemView();
            ItemView iv_2 = new ItemView();
            iv_1.setId(i+1);iv_2.setId(i+1);
            iv_1.setColor(viewColor_one[i]);
            iv_2.setColor(viewColor_two[i]);
            iv_1.setItem_img(picRes[i]);
            iv_2.setItem_img(picRes2[i]);            //到时候这里别忘了改
            iv_1.setItem_title(itemTitle_one[i]);
            iv_2.setItem_title(itemTitle_two[i]);
            mViewList_one.add(iv_1);
            mViewList_two.add(iv_2);
        }
        //天气数据
        weather_img.put("暴雪",R.drawable.weather_baoxue);weather_img.put("暴雨",R.drawable.weather_baoyu);
        weather_img.put("暴雨转大暴雨",R.drawable.weather_baoyuzhuandabaoyu);weather_img.put("大暴雨",R.drawable.weather_dabaoyu);
        weather_img.put("大暴雨转特大暴雨",R.drawable.weather_dabaoyuzhuantedabaoyu);weather_img.put("大雪",R.drawable.weather_daxue);
        weather_img.put("大雪转暴雪",R.drawable.weather_daxuezhuanbaoxue);weather_img.put("大雨",R.drawable.weather_dayu);
        weather_img.put("大雨转暴雨",R.drawable.weather_dayuzhuanbaoyu);weather_img.put("冻雨",R.drawable.weather_dongyu);
        weather_img.put("多云",R.drawable.weather_duoyun);weather_img.put("浮尘",R.drawable.weather_fuchen);
        weather_img.put("雷阵雨",R.drawable.weather_leizhenyu);weather_img.put("雷阵雨伴有冰雹",R.drawable.weather_leizhenyubanyoubingbao);
        weather_img.put("霾",R.drawable.weather_mai);weather_img.put("强沙尘暴",R.drawable.weather_qiangshachenbao);
        weather_img.put("晴",R.drawable.weather_qing);weather_img.put("沙尘暴",R.drawable.weather_shachenbao);
        weather_img.put("特大暴雨",R.drawable.weather_tedabaoyu);weather_img.put("雾",R.drawable.weather_wu);
        weather_img.put("小雪",R.drawable.weather_xiaoxue);weather_img.put("小雪转中雪",R.drawable.weather_xiaoxuezhuanzhongxue);
        weather_img.put("小雨",R.drawable.weather_xiaoyu);weather_img.put("小雨转中雨",R.drawable.weather_xiaoyuzhuanzhongyu);
        weather_img.put("扬沙",R.drawable.weather_yangsha);weather_img.put("阴",R.drawable.weather_yin);
        weather_img.put("雨夹雪",R.drawable.weather_yujiaxue);weather_img.put("阵雪",R.drawable.weather_zhenxue);
        weather_img.put("阵雨",R.drawable.weather_zhenyu);weather_img.put("中雪",R.drawable.weather_zhongxue);
        weather_img.put("中雪转大雪",R.drawable.weather_zhongxuezhuandaxue);weather_img.put("中雨",R.drawable.weather_zhongyu);
        weather_img.put("中雨转大雨",R.drawable.weather_zhongyuzhuandayu);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View oneView = mInflater.inflate(R.layout.simple_v1, null);
        viewList.add(oneView);
        final View twoView = mInflater.inflate(R.layout.simple_v2, null);
        viewList.add(twoView);
        final View threeView = mInflater.inflate(R.layout.simple_v3, null);
        viewList.add(threeView);
        //final View fourView = mInflater.inflate(R.layout.simple_v4, null);
        //viewList.add(fourView);
        btn_dial = (LinearLayout) twoView.findViewById(sim_dial);
        btn_app = (LinearLayout) twoView.findViewById(sim_app);
        initList();



        btn_dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.playText("拨号","ent=vivi21,bft=5",null);
                VibratorUtil.Vibrate(getActivity(), 100);
                Intent intent = new Intent(getActivity(),SimDailActivity.class);
                startActivity(intent);
            }
        });

        btn_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.playText("应用","ent=vivi21,bft=5",null);
                VibratorUtil.Vibrate(getActivity(), 100);
                Intent intent = new Intent(getActivity(), AppListActivity.class);
                startActivity(intent);
            }
        });

        mGridView_1 = (DragGridView)oneView.findViewById(myGridView);
        mGridView_3 = (DragGridView)threeView.findViewById(myGridView);
        mGridView_1.setAdapter(new GridViewAdapter(getActivity(),mViewList_one));
        mGridView_3.setAdapter(new GridViewAdapter(getActivity(),mViewList_two));

        tv_date_time = (TextView)twoView.findViewById(R.id.simple_date_time);
        tv_date_yang=(TextView)twoView.findViewById(R.id.simple_date_yang);
        tv_date_ying=(TextView)twoView.findViewById(R.id.simple_date_yin);
        tv_date_week = (TextView) twoView.findViewById(R.id.simple_date_week);
        tv_city = (TextView) twoView.findViewById(R.id.simple_city);
        tv_weather = (TextView) twoView.findViewById(R.id.simple_weather);
        weather_image = (ImageView) twoView.findViewById(R.id.simple_weather_image);

        if(city!=null){
            tv_city.setText(city);
        }
        if(str_temp!=null){
            tv_weather.setText(str_temp);
        }
        if(bitmap!=null){
            weather_image.setImageBitmap(bitmap);
        }


        mGridView_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridViewAdapter adapter = (GridViewAdapter) mGridView_1.getAdapter();
                List<ItemView> list = adapter.getList();
                int vid = list.get(position).getId();
                switch (vid){
                    case 1:             //"紧急求助"
                        Log.e("debug","紧急求助");
                        player.playText("紧急求助","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        Intent intent = new Intent(getActivity(), SOSActivity.class);
                        startActivity(intent);
                        break;
                    case 2:             //"控制中心"
                        Log.e("debug","控制中心");
                        player.playText("控制中心","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), ControlActivity.class);
                        startActivity(intent);
                        break;
                    case 3:             //"家人"
                        Log.e("debug","家人");
                        player.playText("家人","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 4:             //"添加"
                        Log.e("debug","添加");
                        player.playText("添加","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        break;
                    case 5:             //"短信"
                        Log.e("debug","短信");
                        player.playText("短信","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), SmsActivity.class);
                        startActivity(intent);
                        break;
                    case 6:             //"联系人"
                        Log.e("debug","联系人");
                        player.playText("联系人","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), ContactsActivity.class);
                        startActivity(intent);
                        break;
                    default:break;
                }
            }
        });

        mGridView_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GridViewAdapter adapter = (GridViewAdapter) mGridView_3.getAdapter();
                List<ItemView> list = adapter.getList();
                int vid = list.get(position).getId();
                switch (vid){
                    case 1:             //"相机"
                        Log.e("debug","相机");
                        player.playText("相机","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        Intent intent = new Intent();
                        intent.setAction("android.media.action.IMAGE_CAPTURE");
                        intent.addCategory("android.intent.category.DEFAULT");

                        //保存照片到指定的路径
                        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        Uri uri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivity(intent);
                        break;
                    case 2:             //"上网"
                        Log.e("debug","上网");
                        player.playText("上网","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        Intent in = new Intent();
                        in.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("http://m.baidu.com");
                        in.setData(content_url);
                        startActivity(in);
                        break;
                    case 3:             //"工具"
                        Log.e("debug","工具");
                        player.playText("工具","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), ToolsActivity.class);
                        startActivity(intent);
                        break;
                    case 4:             //"服务"
                        Log.e("debug","服务");
                        player.playText("服务","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), ServiceActivity.class);
                        startActivity(intent);
                        break;
                    case 5:             //"使用帮助"
                        Log.e("debug","使用帮助");
                        player.playText("使用帮助","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent = new Intent(getActivity(), HelpActivity.class);
                        startActivity(intent);
                        break;
                    case 6:             //"设置"
                        Log.e("debug","设置");
                        player.playText("设置","ent=vivi21,bft=5",null);
                        VibratorUtil.Vibrate(getActivity(), 100);
                        intent=new Intent(getActivity(), SettingActivity.class);
                        startActivity(intent);
                        break;
                    default:break;
                }
            }
        });
        Time t=new Time(); // or Time t=new Time("GMT+8");

        t.setToNow(); // 取锟斤拷系统时锟戒。
        int year = t.year;
        int month = t.month+1;
        int date = t.monthDay;
        int hour = t.hour; // 0-23
        int minute = t.minute;
        int weekNum = t.weekDay;
        String [] week ={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        String weekDay = week[weekNum];
        //int second = t.second;
        String minute1=""+minute;
        String hour1=""+hour;
        if(minute<10)
        {
            minute1="0"+minute1;
            tv_date_time.setText(hour1+":"+minute1);
            tv_date_yang.setText(month+"月"+date+"日");
            String Lunar= LunarCalendar.getLunar(year,month,date);
            tv_date_week.setText(weekDay);
            tv_date_ying.setText(Lunar);
        }
        else
        {
            tv_date_time.setText(hour1+":"+minute1);
            tv_date_yang.setText(month+"月"+date+"日");
            String Lunar= LunarCalendar.getLunar(year,month,date);
            tv_date_week.setText(weekDay);
            tv_date_ying.setText(Lunar);
        }

        imageViews = new ImageView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            imageView = new ImageView(this.getActivity());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
            imageView.setPadding(20, 0, 20, 0);
            imageViews[i] = imageView;

            if (i == MainActivity.page_index) {
                // 默认选中第一张图
                imageViews[i]
                        .setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageViews[i].setBackgroundResource(R.drawable.page_indicator);
            }

            groupViewLl.addView(imageViews[i]);
        }
        viewPager.setAdapter(new MyPagerAdapter(viewList));
        viewPager.setCurrentItem(MainActivity.page_index);
        viewPager.setOnPageChangeListener(new SwitchPageChangeListener());
    }



    class SwitchPageChangeListener implements OnPageChangeListener {
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
            MainActivity.page_index=arg0;
        }
    }




    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        try {
            if (e1.getY() - e2.getY() < -89) {

                return true;
            } else if (e1.getY() - e2.getY() > 89) {
                Intent intent = new Intent(getActivity(),SoundHelperActivity.class);
                startActivity(intent);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
}
