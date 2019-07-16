package com.gh_hitech.devicecontroller.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

import java.util.LinkedList;
import java.util.List;

import cn.com.yijigu.rxnetwork.application.RetrofitUtilsApplication;

/**
 * @author yijigu
 */
public class ApplicationUtils extends Application {
    protected static Context mContext;

    private List<Activity> activities = new LinkedList<Activity>();

    private static ApplicationUtils mInstance;
    /** 主线程ID */
    private static int mMainThreadId = -1;
    /** 主线程ID */
    private static Thread mMainThread;
    /** 主线程Handler */
    private static Handler mMainThreadHandler;
    /** 主线程Looper */
    private static Looper mMainLooper;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        new RetrofitUtilsApplication().onCreate(this);
        mMainThreadId = android.os.Process.myTid();
        mMainThread = Thread.currentThread();
        mMainThreadHandler = new Handler();
        mMainLooper = getMainLooper();
        mInstance = this;
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activities.remove(activity);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //非默认值
        if (newConfig.fontScale != 1) {
            this.getResources();
        }
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        //非默认值
        if (res.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    public static Context getAppContext() {
        return mContext;
    }

    /***
     * 添加一个activty
     *
     * @param activity
     *            需要添加的activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    public void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }

        /***
         * 退出应用
         */
    public void exitApp() {
        for (Activity activity : activities) {
            activity.finish();
            activity = null;
        }
        activities.clear();
        System.exit(0);
        ActivityManager activityMgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.restartPackage(getPackageName());
    }
    /***
     * 退出应用
     */
    public Activity getOneActity(Class cls) {
        for (Activity activity : activities) {
            if(activity.getClass() == cls){
                return  activity;
            }
        }
        return null;
    }

    public static ApplicationUtils getApplication() {
        return mInstance;
    }

    /** 获取主线程ID */
    public static int getMainThreadId() {
        return mMainThreadId;
    }

    /** 获取主线程 */
    public static Thread getMainThread() {
        return mMainThread;
    }

    /** 获取主线程的handler */
    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    /** 获取主线程的looper */
    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }
}
