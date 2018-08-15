package com.zzq.netlib.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.zzq.netlib.di.module.GlobalConfigurationModule;
import com.zzq.netlib.di.module.NetModule;
import com.zzq.netlib.di.scope.AppScope;

import java.io.File;

import dagger.BindsInstance;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * @auther tangedegushi
 * @creat 2018/8/14
 * @Decribe
 */
@AppScope
@Component(modules = {NetModule.class, GlobalConfigurationModule.class})
public interface AppComponent {

    Application application();

    //获取缓存目录，便于清除缓存目录下的数据
    File cacheFile();

    Gson gson();

    Retrofit retrofit();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        Builder globalConfigurationModule(GlobalConfigurationModule configuration);

        AppComponent build();
    }

}









