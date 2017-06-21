package com.debla.minicomp.Activity.GuideActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.debla.minicomp.APIs.MyLocationListener;
import com.debla.minicomp.Activity.Public.AlmancActivity;
import com.debla.minicomp.Activity.Public.WeatherActivity;
import com.debla.minicomp.Activity.SimpleMode.SimpleFragment;
import com.debla.minicomp.Activity.StandardMode.StandardFragment;
import com.debla.minicomp.minicomp.R;
import com.iflytek.speech.SynthesizerPlayer;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{
    public static int page_index=1;
    private static boolean isSimpleMode = true;
    private static Context context ;
    private static MainActivity me;
    private static SynthesizerPlayer player;
    private static final String APPID = "appid=519328ab";
    public static LocationClient mLocClient;
    public static Fragment fragment=null;
    private static FragmentTransaction ft;
    private BDLocationListener bdListener = new MyLocationListener();

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            10);

    private static final int BAIDU_READ_PHONE_STATE =100;
    private static final int BAIDU_ACCESS_COARSE_LOCATION=101;
    private static final int BAIDU_ACCESS_FINE_LOCATION=102;
    private static final int BAIDU_READ_EXTERNAL_STORAGE=103;
    private static final int BAIDU_WRITE_EXTERNAL_STORAGE=104;

    private void getContext(){
        context = getApplicationContext();
        me = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initLocation();
        mLocClient.start();
        BDLocation bdLocation = new BDLocation();
        bdListener.onReceiveLocation(bdLocation);
        ft = this.getFragmentManager().beginTransaction();
        player = SynthesizerPlayer.createSynthesizerPlayer(MainActivity.this, APPID);
        player.setVoiceName("vivixiaoyan");
    }

    @TargetApi(23)
    public void requestPermission(){
        if(Build.VERSION.SDK_INT>=23) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                requestPermissions(new String[]{
                        Manifest.permission.READ_PHONE_STATE}, BAIDU_READ_PHONE_STATE);
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION}, BAIDU_ACCESS_COARSE_LOCATION);
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION}, BAIDU_ACCESS_FINE_LOCATION);
                requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, BAIDU_READ_EXTERNAL_STORAGE);
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, BAIDU_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getContext();
        setFragment();
    }

    /**
     * Fragment界面
     */
    private  void setFragment(){
        if(isSimpleMode){
            fragment = new SimpleFragment();
        }else{
            fragment = new StandardFragment();
        }
        FragmentTransaction ft = this.getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_switch_ll, fragment);
        ft.commitAllowingStateLoss();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {

            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 1:
                BAIDU_READ_PHONE_STATE:

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）

                } else{

                    // 没有获取到权限，做特殊处理
                }
                break;

            default:
                break;

        }
    }

    /**
     * Fragment界面切换
     */
    public static void switchFragment(){
        if(isSimpleMode){
            fragment = new SimpleFragment();
        }else{
            fragment = new StandardFragment();
        }
        isSimpleMode=!isSimpleMode;
        FragmentTransaction ft = me.getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_switch_ll, fragment);
        ft.commitAllowingStateLoss();
    }

    public void onDate(View v){
        Intent intent = new Intent(MainActivity.this, AlmancActivity.class);
        startActivity(intent);
    }

    public void onWeather(View v){
        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
        startActivity(intent);
    }

    private void initLocation(){
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(bdListener);
        LocationClientOption option = new LocationClientOption();
        Log.e("debug","initial");
        // option.setLocationMode(LocationMode.Hight_Accuracy
        //);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocClient.setLocOption(option);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }
}
