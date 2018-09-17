package com.zzq.netlib.error;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.zzq.netlib.utils.Logger;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.exceptions.CompositeException;
import retrofit2.HttpException;

/**
 * @auther tangedegushi
 * @creat 2018/9/11
 * @Decribe
 */
public class ExceptionHandleUtil {

    private ExceptionHandleUtil() {
    }

    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponseException handleException(Throwable e) {
        ResponseException ex;
        if (e instanceof CompositeException) {
            List<Throwable> exceptions = ((CompositeException) e).getExceptions();
            e = exceptions.get(0);
        }
        Logger.zzqLog().e("the throwable = " + e);
        if (e instanceof HttpException || e instanceof retrofit2.adapter.rxjava2.HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseException(e, ResponseException.HTTP_ERROR);
            ex.message = "the http error code is = " + httpException.code();
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseException(e, ResponseException.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseException(e, ResponseException.CONNECT_ERROR);
            ex.message = "连接失败";
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseException(e, ResponseException.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else if (e instanceof ConnectTimeoutException || e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseException(e, ResponseException.TIMEOUT_ERROR);
            ex.message = "连接超时";
            return ex;
        } else if (e instanceof UnknownHostException) {
            ex = new ResponseException(e, ResponseException.UNKNOW_HOST_ERROR);
            ex.message = "UnknownHostException";
            return ex;
        } else {
            ex = new ResponseException(e, ResponseException.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }
}
