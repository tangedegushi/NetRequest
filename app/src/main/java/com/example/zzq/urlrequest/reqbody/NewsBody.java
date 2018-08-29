package com.example.zzq.urlrequest.reqbody;

/**
 * @auther tangedegushi
 * @creat 2018/8/15
 * @Decribe
 */
public class NewsBody {

    private String key = "f685c95d63bf3b648b293fc7bc8e84d5";
    private int type;

    public NewsBody(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
