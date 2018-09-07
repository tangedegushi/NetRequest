package com.example.zzq.urlrequest;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import com.zzq.netlib.mvp.BasePresenter;
import com.zzq.netlib.mvp.IView;
import com.zzq.netlib.utils.Logger;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public class NewsPresenter extends BasePresenter<NewsModel,IView> {

    public NewsPresenter(@NonNull NewsModel mModel, @NonNull IView mView) {
        super(mModel, mView);
    }

    @Override
    public void onCreate(LifecycleOwner owner) {
        mModel.loadConmonData()
                .compose(transformer())
                .as(bindLifecycle())
                .subscribe(s -> Logger.zzqLog().d("this is the net data"));
    }
}
