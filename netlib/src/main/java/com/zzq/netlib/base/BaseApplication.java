package com.zzq.netlib.base;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.zzq.netlib.base.delegate.AppDelegate;
import com.zzq.netlib.di.component.AppComponent;
import com.zzq.netlib.utils.UtilCheck;

/**
 * @auther tangedegushi
 * @creat 2018/8/14
 * @Decribe
 */
public class BaseApplication extends Application implements App{

    private AppDelegate appDelegate;
    public static BaseApplication application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (UtilCheck.isNull(appDelegate)) {
            appDelegate = new AppDelegate();
        }
        appDelegate.attachBaseContext(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        appDelegate.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        appDelegate.onTerminate();
    }

    @NonNull
    @Override
    public AppComponent getAppComponent(){
        return appDelegate.getAppComponent();
    }
}
