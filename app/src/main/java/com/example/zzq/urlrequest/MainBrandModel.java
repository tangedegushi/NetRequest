package com.example.zzq.urlrequest;

import com.example.zzq.urlrequest.bean.MainBrand;
import com.example.zzq.urlrequest.service.ApiCacheService;
import com.example.zzq.urlrequest.service.ApiService;
import com.zzq.netlib.mvp.BaseModel;
import com.zzq.netlib.mvp.response.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;

/**
 * @auther tangedegushi
 * @creat 2018/9/10
 * @Decribe
 */
public class MainBrandModel extends BaseModel<BaseResponse<MainBrand>> {
    @Override
    public Observable<BaseResponse<MainBrand>> loadCommonData() {
        return netManager.getRetrofitService(ApiService.class).getMainBrand(1);
    }

    public Observable<BaseResponse<MainBrand>> loadCommonDataCache () {
        return netManager.getCacheService(ApiCacheService.class)
                .getMainBrand(loadCommonData(),new DynamicKey(1),new EvictDynamicKey(false))
                .map(Reply::getData);
    }
}
