package com.inMotion.entities.common.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by jialuc on 6/30/16.
 */
public class AppVersion {

    public static String appVersion(Context context){
        return getPackageInfo(context).versionName + getPackageInfo(context).versionCode;
    }


    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
