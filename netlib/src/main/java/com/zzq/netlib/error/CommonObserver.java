package com.zzq.netlib.error;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @auther tangedegushi
 * @creat 2018/9/11
 * @Decribe 对请求数据时发生异常情况进行了处理
 */
public abstract class CommonObserver<T> implements Observer<T> {

    private ErrorHandle errorHandle;

    public CommonObserver(ErrorHandle errorHandle) {
        this.errorHandle = errorHandle;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        errorHandle.processError(ExceptionHandleUtil.handleException(e));
    }

    @Override
    public void onComplete() {

    }
}
