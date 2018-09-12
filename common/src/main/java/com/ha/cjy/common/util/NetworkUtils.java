package com.ha.cjy.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by cjy on 2018/7/23.
 */

public class NetworkUtils {
    public NetworkUtils() {
    }

    public static boolean checkURL(String url) {
        if(url == null) {
            return false;
        } else {
            url = url.trim();
            return !url.equals("");
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService("connectivity");
        if(cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if(networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }

        return false;
    }

    public static boolean isWifiAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo wifi = connMgr.getNetworkInfo(1);
        return wifi.isAvailable();
    }

    public static boolean isMobileNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService("connectivity");
        NetworkInfo mobile = connMgr.getNetworkInfo(0);
        return mobile.isAvailable();
    }

    public static int getNetworkState(Context ctx) {
        return isWifiEnable(ctx)?0:1;
    }

    public static boolean isWifiEnable(Context ctx) {
        ConnectivityManager tele = (ConnectivityManager)ctx.getSystemService("connectivity");
        if(tele.getActiveNetworkInfo() != null && tele.getActiveNetworkInfo().isAvailable()) {
            int type = tele.getActiveNetworkInfo().getType();
            return type == 1;
        } else {
            return false;
        }
    }

    public static String getNT(Context ctx) {
        String imsi = getIMSI(ctx);
        String nt = "0";
        if(isWifiEnable(ctx)) {
            nt = "10";
        } else if(imsi == null) {
            nt = "0";
        } else if(imsi.length() > 5) {
            String mnc = imsi.substring(3, 5);
            if(!mnc.equals("00") && !mnc.equals("02")) {
                if(mnc.equals("01")) {
                    nt = "31";
                } else if(mnc.equals("03")) {
                    nt = "32";
                }
            } else {
                nt = "53";
            }
        }

        return nt;
    }

    public static String getIMSI(Context ctx) {
        TelephonyManager tm = (TelephonyManager)ctx.getSystemService("phone");
        String result = tm.getSubscriberId();
        return result == null?"":result;
    }

    public static int checkNetworkToInt(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService("connectivity");
        if(connectMgr.getActiveNetworkInfo() != null) {
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(1);
            NetworkInfo.State wifi = wifiNetInfo.getState();
            if(wifi == NetworkInfo.State.CONNECTED) {
                return 0;
            }

            NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(0);
            NetworkInfo.State mobile = mobNetInfo.getState();
            if(mobile == NetworkInfo.State.CONNECTED) {
                return 1;
            }
        }

        return 2;
    }
}
