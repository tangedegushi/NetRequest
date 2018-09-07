package com.zzq.netlib.mvp;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import com.uber.autodispose.AutoDisposeConverter;
import com.zzq.netlib.utils.Logger;
import com.zzq.netlib.utils.UtilRx;
import io.reactivex.Observable;
import io.reactivex.ObservableConverter;
import io.reactivex.ObservableTransformer;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public abstract class BasePresenter<IM extends IModel,IV extends IView> implements IPresenter {

    protected IM mModel;
    protected IV mView;

    public BasePresenter(@NonNull IM mModel, @NonNull IV mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    /**
     * @param <T> 目的：RxJava内存泄露问题
     *            使用：{@link Observable#as(ObservableConverter)}时传入
     * @return
     */
    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return UtilRx.bindLifecycle((LifecycleOwner)mView);
    }

    /**
     * @param <T> 主要用于线程切换
     *           使用：{@link Observable#compose(ObservableTransformer)}时传入
     * @return
     */
    public <T>ObservableTransformer<T, T> transformer(){
        return UtilRx.applySchedulers(mView);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner){
        Logger.zzqLog().d("onDestroy === "+this.getClass().getCanonicalName());
        ((LifecycleOwner)mView).getLifecycle().removeObserver(this);
        mModel = null;
        mView =null;
    }
}
