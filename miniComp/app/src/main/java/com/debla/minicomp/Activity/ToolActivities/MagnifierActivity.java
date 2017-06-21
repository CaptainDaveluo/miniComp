package com.debla.minicomp.Activity.ToolActivities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.debla.minicomp.minicomp.R;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.hardware.Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;

/**
 * Created by Dave-PC on 2017/3/29.
 */

public class MagnifierActivity extends Activity implements SurfaceHolder.Callback{
    private Camera m_Camera;
    private SurfaceView m_surfaceview;
    private OrientationEventListener mOrEventListener; // 设备方向监听器
    private Boolean mCurrentOrientation; // 当前设备方向 横屏false,竖屏true
    private Camera.AutoFocusCallback mAutoFocusCallback;    //自动聚焦回调
    private Timer mTimer;
    private TimerTask mTimerTask;
    private ScaleGestureDetector mScaleGestureDetector = null;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magnifier);
        m_surfaceview = (SurfaceView) findViewById(R.id.magnifier_sfv);

        mAutoFocusCallback = new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if(success){
                    m_Camera.setOneShotPreviewCallback(null);
                    Log.e("debug","自动聚焦成功");
                }
            }
        };
        mTimer = new Timer();
        mTimerTask = new CameraTimerTask();
        SurfaceHolder holder = m_surfaceview.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        startOrientationChangeListener();
        ViewGroup.LayoutParams lp = m_surfaceview.getLayoutParams();
        lp.width= ViewGroup.LayoutParams.MATCH_PARENT;
        m_surfaceview.setLayoutParams(lp);
        m_surfaceview.setZOrderOnTop(false);
        //m_surfaceview.setRotation(90);
        holder.addCallback(this);
        mScaleGestureDetector = new ScaleGestureDetector(this,new ScaleGestureListener());
        m_surfaceview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mScaleGestureDetector.onTouchEvent(event);
            }
        });
    }


    private void initCamera(){
        m_Camera.autoFocus(mAutoFocusCallback);
        m_Camera.setDisplayOrientation(90);
        if(m_Camera.getParameters().isZoomSupported()&&m_Camera.getParameters().isSmoothZoomSupported()){
            int MaxZoom=m_Camera.getParameters().getMaxZoom();
            if(MaxZoom==0)
                return;
            int zoomValue=1;
            while(zoomValue<MaxZoom){
                zoomValue++;
                Parameters parm = m_Camera.getParameters();
                parm.setZoom(zoomValue);
                m_Camera.setParameters(parm);
            }
        }
        Log.e("debug","addCallback");
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(m_Camera!=null){
            try{
                m_Camera.setPreviewDisplay(holder);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    @SuppressWarnings("deprecation")
    private Size getBestSupportedSize(List<Size> sizes) {
        // 取能适用的最大的SIZE
        Size largestSize = sizes.get(0);
        int largestArea = sizes.get(0).height * sizes.get(0).width;
        for (Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                largestArea = area;
                largestSize = s;
            }
        }
        return largestSize;
    }


    @SuppressWarnings("deprecation")
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Parameters parameters = m_Camera.getParameters();// 获取mCamera的参数对象
        Size largestSize = getBestSupportedSize(parameters
                .getSupportedPreviewSizes());
        parameters.setPreviewSize(largestSize.width, largestSize.height);// 设置预览图片尺寸
        largestSize = getBestSupportedSize(parameters
                .getSupportedPictureSizes());// 设置捕捉图片尺寸
        parameters.setPictureSize(largestSize.width, largestSize.height);
        parameters.setFocusMode(FOCUS_MODE_CONTINUOUS_PICTURE);
        parameters.set("rotation",90);
        m_Camera.setParameters(parameters);


        try {
            m_Camera.startPreview();
        } catch (Exception e) {
            if (m_Camera != null) {
                m_Camera.release();
                m_Camera = null;
            }
        }
    }



    private final void startOrientationChangeListener() {
        mOrEventListener = new OrientationEventListener(this) {
            @Override
            public void onOrientationChanged(int rotation) {
                if (((rotation >= 0) && (rotation <= 45)) || (rotation >= 315)
                        || ((rotation >= 135) && (rotation <= 225))) {// portrait
                    mCurrentOrientation = true;
                } else if (((rotation > 45) && (rotation < 135))
                        || ((rotation > 225) && (rotation < 315))) {// landscape
                    mCurrentOrientation = false;
                }
            }
        };
        mOrEventListener.enable();
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        // 开启相机
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            m_Camera = Camera.open(0);
            initCamera();   //初始化相机
            // i=0 表示后置相机
        } else
            m_Camera = Camera.open();
    }


    @Override
    public void onPause() {
        super.onPause();
        // 释放相机
        if (m_Camera != null) {
            m_Camera.release();
            m_Camera = null;
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(m_Camera!=null){
            m_Camera.stopPreview();
        }
    }

    class CameraTimerTask extends TimerTask {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            if(m_Camera != null)
            {
                m_Camera.autoFocus(mAutoFocusCallback);
            }

        }

    }

    class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener{

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scale = detector.getScaleFactor();
            Parameters parm = m_Camera.getParameters();
            if(scale<1.0){
                if(m_Camera.getParameters().isZoomSupported()&&m_Camera.getParameters().isSmoothZoomSupported()){
                    int MaxZoom=m_Camera.getParameters().getMaxZoom();
                    int zoomValue=m_Camera.getParameters().getZoom();
                    if(zoomValue>0)
                        zoomValue--;
                    parm.setZoom(zoomValue);
                    m_Camera.setParameters(parm);
                }
            }else if(scale>1.0){
                if(m_Camera.getParameters().isZoomSupported()&&m_Camera.getParameters().isSmoothZoomSupported()){
                    int MaxZoom=m_Camera.getParameters().getMaxZoom();
                    int zoomValue=m_Camera.getParameters().getZoom();
                    if(zoomValue<MaxZoom)
                        zoomValue++;
                    parm.setZoom(zoomValue);
                    m_Camera.setParameters(parm);
                }
            }
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }
}
