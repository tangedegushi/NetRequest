package com.zzq.netlib.error;

import com.zzq.netlib.mvp.response.BaseResponse;
import com.zzq.netlib.utils.Logger;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @auther tangedegushi
 * @creat 2018/9/10
 * @Decribe 主要用于内容是由 {@link BaseResponse<T>}包裹的,如果不是，
 * 则使用 {@link CommonObserver<T>},这里主要是对异常情况进行了处理
 */
public abstract class BaseObserver<T, R> implements Observer<T> {

    private ErrorHandle errorHandle;

    public BaseObserver(ErrorHandle errorHandle) {
        this.errorHandle = errorHandle;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t instanceof BaseResponse) {
            BaseResponse<R> response = (BaseResponse) t;
            int errorCode = response.getErrorCode();
            if (errorCode != 0) {
                onError(new ServerException(errorCode, response.getErrorMsg()));
            } else {
                onNextBaseResult(response.getData());
            }
        } else {
            Logger.zzqLog().e("the instance should not " + BaseResponse.class.getCanonicalName() + ",the onNextBaseResult() is not called," +
                    "you should implement your logic in onNext() or you can use " + CommonObserver.class.getCanonicalName());
        }
    }


    public abstract void onNextBaseResult(R r);

    @Override
    public void onError(Throwable e) {
        // 这里使用的是全局处理，如果不想全局处理，可以重写这个方法
        errorHandle.processError(e instanceof ServerException ? e : ExceptionHandleUtil.handleException(e));
    }

    @Override
    public void onComplete() {

    }
}
