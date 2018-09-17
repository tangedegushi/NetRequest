package com.zzq.netlib.mvp;

import com.zzq.netlib.http.net.INetManager;
import com.zzq.netlib.utils.UtilApp;

import io.reactivex.Observable;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public abstract class BaseModel<D> implements IModel<D> {
    protected INetManager netManager;

    public BaseModel() {
        netManager = UtilApp.obtainAppComponent().netManager();
    }

    public Observable<D> loadCommonDataRxCache() {
        return null;
    }

    public <T> T getService(Class<T> c) {
        return netManager.getRetrofitService(c);
    }

    public <T> T getCacheService(Class<T> c) {
        return netManager.getCacheService(c);
    }

    @Override
    public void onDestroy() {
        netManager = null;
    }
}
