package com.zzq.netlib.mvp;

import com.zzq.netlib.http.net.INetManager;
import com.zzq.netlib.utils.UtilApp;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public abstract class BaseModel<D> implements IModel<D> {
    protected INetManager netManager;
    public BaseModel(){
        netManager = UtilApp.obtainAppComponent().netManager();
    }
}
