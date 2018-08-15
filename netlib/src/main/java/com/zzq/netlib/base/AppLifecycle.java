package com.zzq.netlib.base;

import android.app.Application;
import android.content.Context;

/**
 * @auther tangedegushi
 * @creat 2018/8/14
 * @Decribe
 */
public interface AppLifecycle {

    void attachBaseContext(Context context);

    void onCreate(Application application);

    void onTerminate();

}
