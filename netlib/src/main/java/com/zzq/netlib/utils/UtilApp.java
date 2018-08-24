package com.zzq.netlib.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.StringRes;
import android.util.TypedValue;
import android.view.View;

import com.zzq.netlib.base.App;
import com.zzq.netlib.base.BaseApplication;
import com.zzq.netlib.di.ActivityManager;
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
            return ((App) context.getApplicationContext()).getAppComponent();
        } else {
            throw new RuntimeException("your Application does not implements App");
        }
    }

    public static AppComponent obtainAppComponent() {
        BaseApplication application = BaseApplication.application;
        if (application instanceof App) {
            return application.getAppComponent();
        } else {
            throw new RuntimeException("your Application does not implements App");
        }
    }

    //------------------------弹窗相关-----------------------------

    private static ActivityManager getActivityManager(){
        return obtainAppComponent().activityManager();
    }

    public static void showToast(@StringRes int resId){
        getActivityManager().showToat(resId);
    }

    public static void showToast(String message){
        getActivityManager().showToast(message,false);
    }

    public static void showToast(String message,boolean isLong){
        getActivityManager().showToast(message,isLong);
    }

    public static void showSnackbar(String message){
        getActivityManager().showSnackBar(message,false);
    }

    public static void showSnackbar(String message,boolean isLong){
        getActivityManager().showSnackBar(message,isLong);
    }

    public static int dp2px(Context context,int value){
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     * @param activity
     * @return bp
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        Bitmap bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, bmp.getWidth(), bmp.getHeight() - statusBarHeight);
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);

        return bp;
    }
}
