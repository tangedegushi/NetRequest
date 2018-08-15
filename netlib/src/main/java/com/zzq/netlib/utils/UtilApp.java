package com.zzq.netlib.utils;

import android.content.Context;

import com.zzq.netlib.base.App;
import com.zzq.netlib.di.component.AppComponent;

import java.security.MessageDigest;

/**
 * @auther tangedegushi
 * @creat 2018/8/15
 * @Decribe
 */
public class UtilApp {

    private UtilApp() {
    }

    public static String md5Encode(String string) {
        byte[] hash = new byte[0];
        try {
            hash = MessageDigest.getInstance("MD5").digest(
                    string.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static AppComponent obtainAppComponent(Context context) {
        UtilCheck.checkNotNull(context, "%s cannot be null", Context.class.getName());
        if (context.getApplicationContext() instanceof App) {
            throw new RuntimeException("your Application does not implements App");
        }
        return ((App) context.getApplicationContext()).getAppComponent();
    }
}
