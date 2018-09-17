package com.zzq.netlib.error;

/**
 * @auther tangedegushi
 * @creat 2018/9/11
 * @Decribe
 */
public class ServerException extends RuntimeException {

    private int errorCode;
    public String message;

    public ServerException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
