package com.zzq.netlib.mvp.response;

import com.google.gson.annotations.SerializedName;

/**
 * @auther tangedegushi
 * @creat 2018/9/10
 * @Decribe 根据实际传递的参数这里需要修改，这只是一个模板而已
 */
public class BaseResponse<T> {

    /**
     * success : true
     * errCode : 0
     * msg : success
     * data:{"":""}
     */

    @SerializedName("errorCode")
    private int errorCode;
    @SerializedName("errorMsg")
    private String errorMsg;
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
