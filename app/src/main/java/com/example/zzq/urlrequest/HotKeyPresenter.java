package com.example.zzq.urlrequest;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.example.zzq.urlrequest.bean.HotKey;
import com.example.zzq.urlrequest.service.HotKeyModel;
import com.zzq.netlib.error.BaseObserver;
import com.zzq.netlib.mvp.BasePresenter;
import com.zzq.netlib.mvp.IView;
import com.zzq.netlib.mvp.response.BaseResponse;

import java.util.List;

/**
 * @auther tangedegushi
 * @creat 2018/9/10
 * @Decribe
 */
public class HotKeyPresenter extends BasePresenter<HotKeyModel, IView> {
    public HotKeyPresenter(@NonNull HotKeyModel mModel, @NonNull IView mView) {
        super(mModel, mView);
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        commonObservable(mModel.loadCommonDataRxCache())
                .subscribe(new BaseObserver<BaseResponse<List<HotKey>>, List<HotKey>>(errorHandle) {
                    @Override
                    public void onNextBaseResult(List<HotKey> listBaseResponse) {

                    }
                });
    }
}
