package com.zzq.netlib.di.module;

import com.zzq.netlib.http.net.INetManager;
import com.zzq.netlib.http.net.NetManager;

import dagger.Binds;
import dagger.Module;

/**
 * @auther tangedegushi
 * @creat 2018/8/16
 * @Decribe
 */
@Module
public abstract class AppModule {

    @Binds
    abstract INetManager bindNetManage(NetManager manager);

}
