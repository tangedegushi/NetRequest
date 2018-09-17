package com.zzq.netlib.mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.zzq.netlib.R;
import com.zzq.netlib.mvp.IPresenter;
import com.zzq.netlib.mvp.IView;
import com.zzq.netlib.utils.Logger;
import com.zzq.netlib.view.dialog.EasyDialog;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public abstract class MvpBaseActivity<P extends IPresenter> extends AppCompatActivity implements IView {

    protected P mPresenter;
    private EasyDialog easyDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = onCreatePresenter();
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    public void onShowLoading() {
        Logger.zzqLog().d("show loading time = "+System.currentTimeMillis());
        if (!isShowLoading()) return;
        easyDialog = new EasyDialog.Builder()
                .setLayoutId(R.layout.netlib_dialog_loading)
                .setWidth(100)
                .setHeight(100)
                .build();
        easyDialog.show(getSupportFragmentManager(),"loading");
    }

    @Override
    public void onHideLoading() {
        Logger.zzqLog().d("hide loading time = "+System.currentTimeMillis());
        if (!isShowLoading()) return;
        easyDialog.dismiss();
        //当为全局变量时一定要置null，否则会内存泄露
        easyDialog = null;
    }

    public boolean isShowLoading(){
        return true;
    }

    @NonNull
    @Override
    public abstract P onCreatePresenter();
}
