package com.example.thinkpad.icompetition.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * creat by a'su's
 * 获取网络状态
 * Created by ningyuwen on 2017/4/11.
 */

public class NetWorkHelper {
    public NetWorkHelper() {
    }

    public static boolean isActiveNetWorkWifi(Context paramContext) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService(paramContext.CONNECTIVITY_SERVICE);
        if(localConnectivityManager != null) {
            NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
            if(localNetworkInfo == null || localNetworkInfo.getType() != 1) {
                return false;
            }
        }

        return true;
    }

    public static boolean isActiveNetworkMobile(Context paramContext) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService(paramContext.CONNECTIVITY_SERVICE);
        if(localConnectivityManager != null) {
            NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
            if(localNetworkInfo == null || localNetworkInfo.getType() != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNetworkAvailable(Context paramContext) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService(paramContext.CONNECTIVITY_SERVICE);
        if(localConnectivityManager != null) {
            NetworkInfo[] arrayOfNetworkInfo = localConnectivityManager.getAllNetworkInfo();
            if(arrayOfNetworkInfo != null) {
                int j = arrayOfNetworkInfo.length;

                for(int k = 0; k < j; ++k) {
                    if(arrayOfNetworkInfo[k].getState() == NetworkInfo.State.CONNECTED
                            && !arrayOfNetworkInfo[k].getTypeName().equals("VPN")) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
