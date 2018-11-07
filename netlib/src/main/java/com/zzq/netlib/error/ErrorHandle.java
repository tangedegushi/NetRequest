package com.zzq.netlib.error;

import com.example.zzq.netlib.BuildConfig;
import com.zzq.netlib.utils.Logger;
import com.zzq.netlib.utils.UtilApp;

/**
 * @auther tangedegushi
 * @creat 2018/9/10
 * @Decribe
 */
public interface ErrorHandle {
    void processError(Throwable e);

    ErrorHandle DEFAULT_HANDLE = new ErrorHandle() {
        @Override
        public void processError(Throwable e) {
            if (e instanceof ServerException) {
                ServerException server = (ServerException) e;
                if (BuildConfig.isDebug) {
                    UtilApp.showToast("the server error code = "+server.getErrorCode(),true);
                }
                Logger.zzqLog().e("the server is error,the error code = " + server.getErrorCode() + ",this error message is = " + server.message);
            } else if (e instanceof ResponseException) {
                e.printStackTrace();
                ResponseException exception = (ResponseException) e;
                int errorCode = exception.getErrorCode();
                switch (errorCode) {
                    case ResponseException.HTTP_ERROR:
                        UtilApp.showToast(exception.message,true);
                        break;
                    case ResponseException.CONNECT_ERROR:
                    case ResponseException.TIMEOUT_ERROR:
                        UtilApp.showToast(exception.message);
                        break;
                    case ResponseException.UNKNOW_HOST_ERROR:
                    case ResponseException.PARSE_ERROR:
                    case ResponseException.SSL_ERROR:
                    case ResponseException.UNKNOWN:
                    case ResponseException.ON_MAIN_THREAD:
                    case ResponseException.PERMISSION_DENIED:
                        if (BuildConfig.isDebug) {
                            UtilApp.showToast(exception.message,true);
                            Logger.zzqLog().e("the error code = " + exception.getErrorCode() + ",the error message is = " + exception.message);
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    };
}
