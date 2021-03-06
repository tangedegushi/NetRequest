package com.zzq.netlib.base.delegate;

import android.app.Application;
import android.content.Context;

import com.example.zzq.netlib.BuildConfig;
import com.zzq.netlib.base.AppLifecycle;
import com.zzq.netlib.di.component.AppComponent;
import com.zzq.netlib.di.component.DaggerAppComponent;
import com.zzq.netlib.di.module.GlobalConfigurationModule;

/**
 * @auther tangedegushi
 * @creat 2018/8/14
 * @Decribe
 */
public class AppDelegate implements AppLifecycle {

    private AppComponent appComponent;

    public AppDelegate() {
    }

    @Override
    public void attachBaseContext(Context context) {

    }

    @Override
    public void onCreate(Application application) {
        appComponent = DaggerAppComponent.builder()
                .application(application)
                .globalConfigurationModule(initGlobalConfig())
                .build();
        initActivityManager();
    }

    private void initActivityManager() {
        appComponent.activityManager();
    }

    private GlobalConfigurationModule initGlobalConfig() {
        return GlobalConfigurationModule.getDefaultInstance();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onTerminate() {

    }
}
