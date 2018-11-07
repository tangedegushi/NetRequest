package com.zzq.netlib.error;

/**
 * @auther tangedegushi
 * @creat 2018/9/10
 * @Decribe
 */
public class ResponseException extends RuntimeException {

    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int CONNECT_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;

    /**
     * 证书出错
     */
    public static final int SSL_ERROR = 1005;

    /**
     * 连接超时
     */
    public static final int TIMEOUT_ERROR = 1006;

    /**
     * 未连接网络
     */
    public static final int UNKNOW_HOST_ERROR = 1007;

    /**
     * 在主线程中请求网络
     */
    public static final int ON_MAIN_THREAD = 1008;

    /**
     * 未添加网络权限
     */
    public static final int PERMISSION_DENIED = 1009;

    private int errorCode;
    public String message;

    public ResponseException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
