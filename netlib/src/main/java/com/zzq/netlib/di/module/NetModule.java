package com.zzq.netlib.di.module;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;

import com.example.zzq.netlib.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zzq.netlib.di.scope.AppScope;
import com.zzq.netlib.http.log.HttpLoggingInterceptor;
import com.zzq.netlib.http.net.INetManager;
import com.zzq.netlib.http.net.NetManager;
import com.zzq.netlib.utils.UtilFile;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @auther tangedegushi
 * @creat 2018/8/14
 * @Decribe 提供一些三方实例创建retrofit，包含gson，rxjava，okhttp以及rxcache
 */
@Module
public class NetModule {
    private static final int TIME_OUT = 10;

    @AppScope
    @Provides
    public static GsonBuilder provideGsonBuilder() {
        return new GsonBuilder();
    }

    @AppScope
    @Provides
    public static Gson provideGson(Application application, GsonBuilder builder, @Nullable ConfigurationGson config) {
        if (config != null) {
            config.configGson(application, builder);
        }
        return builder.create();
    }

    @AppScope
    @Provides
    public static HttpLoggingInterceptor provideLoggingInterceptor(@Nullable ConfigurationLoggingInterceptor config) {
        HttpLoggingInterceptor interceptor;
        if (BuildConfig.isDebug) {
            interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        if (config != null) {
            config.configLoggingInterceptor(interceptor);
        }
        return interceptor;
    }

    @AppScope
    @Provides
    public static OkHttpClient.Builder provideOkhttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @AppScope
    @Provides
    public static OkHttpClient provideOkhttp(Application application, OkHttpClient.Builder builder, @Nullable ConfigurationOkhttp config,
                                      HttpLoggingInterceptor logInterceptor, @Nullable List<Interceptor> interceptors) {
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor);

        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (config != null) {
            config.configOkhttp(application, builder);
        }
        return builder.build();
    }

    @AppScope
    @Provides
    public static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @AppScope
    @Provides
    public static Retrofit provideRetrofit(Application application, Retrofit.Builder builder, @Nullable ConfigurationRetrofit config, OkHttpClient client,
                                    HttpUrl url, Gson gson) {
        builder.baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        if (config != null) {
            config.configRetrofit(application, builder);
        }

        return builder.build();
    }

    /**
     * @param application
     * @param config
     * @param cacheFileDir RxCache缓存目录，默认是应用缓存目录下的RxCache目录
     * @return
     */
    @AppScope
    @Provides
    public static RxCache proviceRxcacheBuilder(Application application, @Nullable ConfigurationRxcache config, File cacheFileDir) {
        RxCache.Builder builder = new RxCache.Builder();
        if (config != null) {
            return config.configRxcache(application, builder);
        } else {
            return builder.persistence(cacheFileDir, new GsonSpeaker());
        }
    }

    @AppScope
    @Provides
    public static File provideFile(Application application, @Nullable ConfigurationCacheFileDir config) {
        File file = null;
        if (config != null) {
            file = new File(config.configCacheFile(application), "RxCache");
        }
        if (file == null) {
            file = new File(UtilFile.getCacheFile(application), "RxCache");
        }
        return UtilFile.makeDirs(file);
    }

    interface ConfigurationGson {
        void configGson(Context context, GsonBuilder builder);
    }

    interface ConfigurationOkhttp {
        void configOkhttp(Context context, OkHttpClient.Builder builder);
    }

    interface ConfigurationRetrofit {
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    interface ConfigurationRxcache {
        RxCache configRxcache(Context context, RxCache.Builder builder);
    }

    interface ConfigurationLoggingInterceptor {
        void configLoggingInterceptor(HttpLoggingInterceptor interceptor);
    }

    interface ConfigurationCacheFileDir {
        File configCacheFile(Application application);
    }
}
