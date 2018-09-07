package com.zzq.netlib.mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zzq.netlib.mvp.IPresenter;
import com.zzq.netlib.mvp.IView;
import com.zzq.netlib.utils.Logger;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public abstract class MvpBaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    public void onShowLoading() {
        Logger.zzqLog().d("this is on show loading");
    }

    @Override
    public void onHideLoading() {
        Logger.zzqLog().d("this is on hide loading");
    }

    @NonNull
    @Override
    public abstract P onCreatePresenter();
}
