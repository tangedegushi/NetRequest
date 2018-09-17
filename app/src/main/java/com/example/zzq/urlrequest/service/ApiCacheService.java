package com.example.zzq.urlrequest.service;

import com.example.zzq.urlrequest.bean.HotKey;
import com.example.zzq.urlrequest.bean.MainBrand;
import com.zzq.netlib.mvp.response.BaseResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.ProviderKey;
import io.rx_cache2.Reply;

/**
 * @auther tangedegushi
 * @creat 2018/9/10
 * @Decribe
 */
public interface ApiCacheService {

    @LifeCache(duration = 1,timeUnit = TimeUnit.MINUTES)
    @ProviderKey("main_brand")
    Observable<Reply<BaseResponse<MainBrand>>> getMainBrand(Observable observable, DynamicKey key, EvictDynamicKey evict);

    @LifeCache(duration = 1,timeUnit = TimeUnit.MINUTES)
    @ProviderKey("hot_key")
    Observable<Reply<BaseResponse<List<HotKey>>>> getHotkey(Observable observable, DynamicKey key, EvictDynamicKey evict);
}
