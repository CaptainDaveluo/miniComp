package com.debla.minicomp.Activity.Public;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.debla.minicomp.Structs.AppBean;
import com.debla.minicomp.minicomp.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave-PC on 2017/2/20.
 */

public class AppListActivity extends Activity {
    private ListView lv_app;
    private List<AppBean> list;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list);
        context = getApplicationContext();
        lv_app = (ListView) findViewById(R.id.lv_app);
        list=getAllApk();
        lv_app.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v;
                if(convertView==null){
                    v= LayoutInflater.from(getApplicationContext()).inflate(R.layout.app_item,null);
                }else{
                    v=(View)convertView;
                }
                ImageView img = (ImageView) v.findViewById(R.id.app_item_img);
                img.setImageDrawable(list.get(position).getAppIcon());
                TextView tv_name = (TextView) v.findViewById(R.id.app_item_name);
                tv_name.setText(list.get(position).getAppName());
                TextView tv_size = (TextView) v.findViewById(R.id.app_item_size);
                float size=(float)list.get(position).getAppSize()/1024/1024;
                DecimalFormat df = new DecimalFormat("0.00");
                String str_size=df.format(size);
                tv_size.setText(str_size+"M");
                return v;
            }
        });
    }



    public static List<AppBean> getAllApk() {
        List<AppBean>appBeanList=new ArrayList<>();
        AppBean bean=null;
        Log.e("debug",context.toString());
        PackageManager packageManager= context.getPackageManager();
        List<PackageInfo> list=packageManager.getInstalledPackages(0);
        for (PackageInfo p:list) {
            bean=new AppBean();
            bean.setAppIcon(p.applicationInfo.loadIcon(packageManager));
            bean.setAppName(packageManager.getApplicationLabel(p.applicationInfo).toString());
            bean.setAppPackageName(p.applicationInfo.packageName);
            bean.setApkPath(p.applicationInfo.sourceDir);
            File file=new File(p.applicationInfo.sourceDir);
            bean.setAppSize((int) file.length());
            int flags=p.applicationInfo.flags;
            //判断是否是属于系统的apk
            if ((flags& ApplicationInfo.FLAG_SYSTEM)!=0){
                bean.setSystem(true);
            }else {
                bean.setSd(true);
                appBeanList.add(bean);
            }


        }
        return appBeanList;
    }
}

