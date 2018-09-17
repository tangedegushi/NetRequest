package com.example.zzq.urlrequest.service;

import com.example.zzq.urlrequest.bean.HotKey;
import com.zzq.netlib.mvp.BaseModel;
import com.zzq.netlib.mvp.response.BaseResponse;
import com.zzq.netlib.utils.Logger;
import java.util.List;
import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;

/**
 * @auther tangedegushi
 * @creat 2018/9/10
 * @Decribe
 */
public class HotKeyModel extends BaseModel<BaseResponse<List<HotKey>>> {
    @Override
    public Observable<BaseResponse<List<HotKey>>> loadCommonData() {
        return getService(ApiService.class).getHotKey();
    }

    @Override
    public Observable<BaseResponse<List<HotKey>>> loadCommonDataRxCache() {
        return getCacheService(ApiCacheService.class)
                .getHotkey(loadCommonData(),new DynamicKey("hotkey"),new EvictDynamicKey(false))
                .map(baseResponseReply -> {
                    Logger.zzqLog().d(baseResponseReply.getSource().name()+"   "+baseResponseReply.getData().getErrorCode());
                    return baseResponseReply.getData();
                });
    }
}
