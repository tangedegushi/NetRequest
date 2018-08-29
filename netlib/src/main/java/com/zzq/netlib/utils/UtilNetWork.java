package com.zzq.netlib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * @auther tangedegushi
 * @creat 2018/8/29
 * @Decribe
 */
public class UtilNetWork {

    private UtilNetWork() {
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager cmr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                NetworkInfo mWiFiNetworkInfo = cmr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable();
                }
            } else {
                Network[] allNetworks = cmr.getAllNetworks();
                for (Network allNetwork : allNetworks) {
                    NetworkInfo networkInfo = cmr.getNetworkInfo(allNetwork);
                    if ("WIFI".equals(networkInfo.getTypeName())) {
                        return networkInfo.isAvailable();
                    }
                }
            }
        }
        return false;
    }


    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager cmr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                NetworkInfo mMobileNetworkInfo = cmr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isAvailable();
                }
            } else {
                Network[] allNetworks = cmr.getAllNetworks();
                for (Network allNetwork : allNetworks) {
                    NetworkInfo info = cmr.getNetworkInfo(allNetwork);
                    if ("MOBILE".equals(info.getTypeName())) {
                        return info.isAvailable();
                    }
                }
            }
        }
        return false;
    }


    /**
     * 获取当前网络连接的类型信息
     *
     * @param context
     * @return
     */
    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager cmr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                NetworkInfo mNetworkInfo = cmr.getActiveNetworkInfo();
                if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                    return mNetworkInfo.getType();
                }
            } else {
                Network[] allNetworks = cmr.getAllNetworks();
                for (Network allNetwork : allNetworks) {
                    NetworkInfo info = cmr.getNetworkInfo(allNetwork);
                    if ("WIFI".equals(info.getTypeName())) {
                        return 1;
                    } else if ("MOBILE".equals(info.getTypeName())) {
                        return 0;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 描述当前网络的连接状态
     * */
    public enum NetWorkType {
        NETWORK_UNLINK(-1, "未连接网络"),
        NETWORK_UNKNOWN(0, "未知网络"),
        NETWORK_WIFI(1, "当前网络是WiFI"),
        NETWORK_2G(2, "当前是2G网络"),
        NETWORK_3G(2, "当前是3G网络"),
        NETWORK_4G(2, "当前是4G网络");

        private String netDetail;
        private int type;

        NetWorkType(int type, String netDetail) {
            this.type = type;
            this.netDetail = netDetail;
        }

        public String getNetDetail() {
            return netDetail;
        }

        public int getType() {
            return type;
        }
    }


    /**
     * @return 返回当前网络的连接状态
     */
    public static NetWorkType getNetWorkStatus() {
        Context context = UtilApp.obtainAppComponent().application();
        NetWorkType netWorkType = NetWorkType.NETWORK_UNKNOWN;

        ConnectivityManager cmr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cmr.getActiveNetworkInfo();

        if (networkInfo == null) {
            return NetWorkType.NETWORK_UNLINK;
        }

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NetWorkType.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }

        return netWorkType;
    }

    private static NetWorkType getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NetWorkType.NETWORK_2G;

            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NetWorkType.NETWORK_3G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return NetWorkType.NETWORK_4G;

            default:
                return NetWorkType.NETWORK_UNKNOWN;
        }
    }
}
