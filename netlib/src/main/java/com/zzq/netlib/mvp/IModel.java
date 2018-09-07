package com.zzq.netlib.mvp;

import io.reactivex.Observable;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public interface IModel<D> {

    Observable<D> loadConmonData();

}
