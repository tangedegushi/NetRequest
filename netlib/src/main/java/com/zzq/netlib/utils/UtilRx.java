package com.zzq.netlib.utils;

import android.arch.lifecycle.LifecycleOwner;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.zzq.netlib.mvp.IView;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @auther tangedegushi
 * @creat 2018/9/7
 * @Decribe
 */
public class UtilRx {

    private UtilRx(){
    }

    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(lifecycleOwner)
        );
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> view.onShowLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(view::onHideLoading);
    }
}
