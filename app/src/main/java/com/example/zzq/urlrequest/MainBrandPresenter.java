package com.example.zzq.urlrequest;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.example.zzq.urlrequest.bean.MainBrand;
import com.zzq.netlib.mvp.BasePresenter;
import com.zzq.netlib.mvp.IView;
import com.zzq.netlib.utils.Logger;

import io.reactivex.functions.Consumer;

/**
 * @auther tangedegushi
 * @creat 2018/9/10
 * @Decribe
 */
public class MainBrandPresenter extends BasePresenter<MainBrandModel,IView> {
    public MainBrandPresenter(@NonNull MainBrandModel mModel, @NonNull IView mView) {
        super(mModel, mView);
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        commonObservable(mModel.loadCommonDataCache()).subscribe(mainBrand -> Logger.zzqLog().d("main brand = "+mainBrand));
    }
}
