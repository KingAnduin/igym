package com.example.thinkpad.icompetition;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import greendao.GreenDaoHelper;
import greendao.gen.DaoMaster;
import greendao.gen.DaoSession;

/**
 * Created by a'su's on 2018/11/13.
 */
public class IcompetitionApplication extends MultiDexApplication {
    private String token;//token,登陆时服务器返回该值,有效期内不需要重复获取,存储于sharedPreferences
    private DaoSession daoSession; //greenDao数据库DaoSession
    private boolean initDone;               //初始化完成标志

    private static IcompetitionApplication application;

    public void setInitDone(boolean initDone) {
        this.initDone = initDone;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        setRegisterActivityLifecycleCallbacks();
        initInChildThread();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static IcompetitionApplication getApplication() {
        return application;
    }

    /**
     * 在子线程进行初始化操作.
     * 在初始化执行完后将初始化标志置为true,并且调用SpreadAdvertisementActivity的方法尝试跳转
     */

    private void initInChildThread() {
        initDone = false;           //复位初始化完成标志
        new Thread(new Runnable() {
            @Override
            public void run() {
                //这里写上初始化的方法
                loadDataFromSharePreferences();
                initDone = true;                        //置位初始化完成标志

//                APICloud.initialize(IswustApplication.this);
                //在极端情况下这里会抛出NullPointerException,也就是在firstActivity销毁后执行以下代码
//                try {
//                    if (firstActivity != null) {
//                        firstActivity.get().applicationInitDone();    //调用第一个Activity的方法
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                } finally {
//                    firstActivity = null;
//                }
            }
        }).start();
    }

    /**
     * 从SharedPreferences加载数据到变量中
     * ApplicationBase用于存储一些在整个应用中都需要使用的轻量级数据
     */
    private void loadDataFromSharePreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("ApplicationBase", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
    }

    /**
     * 获取token,如果为null,或者为空串,再次从SharedPreferences读取值
     *
     * @return token
     */

    public String getToken() {
        if (token == null || "".equals(token)) {
            SharedPreferences sharedPreferences = getSharedPreferences("ApplicationBase", MODE_PRIVATE);
            token = sharedPreferences.getString("token", "");
        }
        return token;
    }

    /**
     * 设置值时,不仅设置给变量,同时存储到SharedPreferences中,以便于以后读取
     *
     * @param token 设置的token值
     */
    public void setToken(String token) {
        this.token = token;
        SharedPreferences sharedPreferences = getSharedPreferences("ApplicationBase", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    /**
     * 获取greenDao的daoSession
     *
     * @return daoSession
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            GreenDaoHelper helper = new GreenDaoHelper(this);
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    /**
     * 获取application初始化是否完成.
     *
     * @return return
     */
    public boolean isInitDone() {
        return initDone;
    }

    /**
     * 在application中监听所有aictivity的生命周期
     */
    public void setRegisterActivityLifecycleCallbacks() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
