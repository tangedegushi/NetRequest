package com.zzq.netlib.base.delegate;

import android.app.Application;
import android.content.Context;

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

    }

    private GlobalConfigurationModule initGlobalConfig() {
        return new GlobalConfigurationModule.Builder().baseUrl("http://cqfb.people.com.cn/wgainDev/WzwActiveService.svc/").build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onTerminate() {

    }
}
