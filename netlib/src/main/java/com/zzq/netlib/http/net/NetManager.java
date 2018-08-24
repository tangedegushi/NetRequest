package com.zzq.netlib.http.net;

import android.app.Application;
import android.content.Context;
import android.util.LruCache;

import com.zzq.netlib.di.scope.AppScope;

import javax.inject.Inject;

import dagger.Lazy;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * @auther tangedegushi
 * @creat 2018/8/16
 * @Decribe 主要用来网络请求及请求的数据缓存
 */
@AppScope
public class NetManager implements INetManager {
    private static final int cacheServiceCount = 100;

    @Inject
    Lazy<Retrofit> mRetrofit;
    @Inject
    Lazy<RxCache> mRxCache;
    @Inject
    Application application;

    private LruCache<String, Object> lruRetrofitCacheService;
    private LruCache<String, Object> lruRxCacheService;

    @Inject
    public NetManager() {
        lruRetrofitCacheService = new LruCache<>(cacheServiceCount);
        lruRxCacheService = new LruCache<>(cacheServiceCount);
    }

    @Override
    public <T> T getRetrofitService(Class<T> service) {
        T retrofitService = (T) lruRetrofitCacheService.get(service.getCanonicalName());
        if (retrofitService == null){
            retrofitService = mRetrofit.get().create(service);
            lruRetrofitCacheService.put(service.getCanonicalName(),retrofitService);
        }
        return retrofitService;
    }

    @Override
    public <T> T getCacheService(Class<T> cacheService) {
        T rxCacheService = (T) lruRxCacheService.get(cacheService.getCanonicalName());
        if (rxCacheService == null) {
            rxCacheService = mRxCache.get().using(cacheService);
            lruRxCacheService.put(cacheService.getCanonicalName(),rxCacheService);
        }
        return rxCacheService;
    }

    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll().subscribe();
    }

    @Override
    public Context getContext() {
        return application;
    }
}
