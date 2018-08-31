package com.zzq.netlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @auther tangedegushi
 * @creat 2018/8/29
 * @Decribe  目的：提供一个全局操作sp的工具类，便于管理
 *           思路：将所有sp的名称和key值都在这里设置常量，这样便于统一管理
 *           比如这里提供了一个默认的sp文件名：common_data,如果还有其他sp文件名同样以这样的方式设置，key值也是类型
 *
 *           使用：如果加载的sp文件比较耗时（初次），那我们可以先调用 {@link #initSp(String spName)}这样可以先在子线程中去加载sp文件，
 *           如果我们是在使用的时候再去初始化的sp，那么主线程会等待子线程加载完后在进行操作，可能就会引起卡顿，如果已经加载过sp文件，
 *           那么内存中就已经存在，不会再去加载了（sp文件加载过一次，内存中就会一直存在），加载过一次后直接设置值既可以了
 */
public class UtilSP {

    private static final String SP_NAME_DEFAULT = "common_data";
    private static ArrayMap<String, SharedPreferences> spMap = new ArrayMap<>();
    private static SharedPreferences currentSP;
    private static SharedPreferences defaultSP;

    private UtilSP() {
    }

    /**
     * @param spName 初次加载sp中的内容时可以先调用这个方法，这样是为了先将sp中的内容加载进内存中
     */
    public static void initSp(@Nullable String spName) {
        if (TextUtils.isEmpty(spName)) {
            if (defaultSP == null) {
                Context context = UtilApp.obtainAppComponent().application();
                currentSP = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
                defaultSP = currentSP;
                spMap.put(SP_NAME_DEFAULT, currentSP);
            } else {
                currentSP = defaultSP;
            }
            return;
        }
        currentSP = spMap.get(currentSP);
        if (currentSP == null) {
            Context context = UtilApp.obtainAppComponent().application();
            currentSP = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
            spMap.put(SP_NAME_DEFAULT, currentSP);
        }

    }

    /**
     * 当有多个值需要设置时，建议获取Editor，然后等所有值设置完后在调用apply（），
     * 这样做是为了减少对象的创建，以及退出界面时阻塞主线程（退出界面时，如果sp中的
     * 内容还没有保存到本地，保存到本地的这个过程就会从后台切换到主线程中来执行，
     * 等到全部保存到本地后才会继续往下执行）
     */
    public static SharedPreferences.Editor getSpEditor(@Nullable String spName) {
        initSp(spName);
        return currentSP.edit();
    }

    /**
     * 存储字符串信息到sp中
     */
    public static void setString(String key, String value) {
        setString(key, value, null);
    }

    public static void setString(String key, String value, @Nullable String spName) {
        initSp(spName);
        currentSP.edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getString(key, null, null);
    }

    public static String getString(String key, String defValue, @Nullable String spName) {
        initSp(spName);
        return currentSP.getString(key, defValue);
    }

    public static void setInt(String key, int value) {
        setInt(key, value, null);
    }

    public static void setInt(String key, int value, @Nullable String spName) {
        initSp(spName);
        currentSP.edit().putInt(key, value);
    }

    public static int getInt(String key) {
        return getInt(key, -1, null);
    }

    public static int getInt(String key, int defValue, String spName) {
        initSp(spName);
        return currentSP.getInt(key, defValue);
    }

    public static void setBoolean(String key, boolean b) {
        setBoolean(key, b, null);
    }

    public static void setBoolean(String key, boolean b, @Nullable String spName) {
        initSp(spName);
        currentSP.edit().putBoolean(key, b);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false, null);
    }

    public static boolean getBoolean(String key, boolean defValue, @Nullable String spName) {
        initSp(spName);
        return currentSP.getBoolean(key, defValue);
    }

    /**
     * @param key 清除SP中的一个key
     */
    public static void removeKey(String key) {
        removeKey(key, null);
    }

    public static void removeKey(String key, @Nullable String spName) {
        initSp(spName);
        currentSP.edit().remove(key).apply();
    }

    /**
     * @param spName 清除SP中的所有内容
     */
    public static void clearSp(String spName) {
        SharedPreferences preferences = spMap.remove(spName);
        if (preferences != null) {
            preferences.edit().clear().apply();
        }
    }

    /**
     * 将对象储存到sharepreference
     *
     * @param key
     * @param device
     * @param <T>
     */
    public static <T> boolean saveDeviceData(String key, T device, @NonNull String spName) {
        initSp(spName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {   //Device为自定义类
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(device);
            // 将字节流编码成base64的字符串
            String oAuth_Base64 = new String(Base64.encode(baos
                    .toByteArray(), Base64.DEFAULT));
            currentSP.edit().putString(key, oAuth_Base64).apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将对象从shareprerence中取出来
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getDeviceData(String key, String spName) {
        initSp(spName);
        T device = null;
        String productBase64 = currentSP.getString(key, null);

        if (productBase64 == null) {
            return null;
        }
        // 读取字节
        byte[] base64 = Base64.decode(productBase64.getBytes(), Base64.DEFAULT);

        // 封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            // 再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);

            // 读取对象
            device = (T) bis.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return device;
    }
}
