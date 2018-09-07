package com.zzq.netlib.mvp;

import android.support.annotation.NonNull;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public interface IView {

    @NonNull
    IPresenter onCreatePresenter();

    void onShowLoading();
    void onHideLoading();

    <T>void onShowMessage(T t);

    <T>void onLoadSuccess(T T);
    void onLoadFail(Exception e);

}
