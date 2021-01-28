package com.zwb.next.util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * @author zhouwb 2020-05-18
 */
public final class SharePreferenceUtils {

    public static void saveData(Context context, String key, String val) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, val).apply();
    }

    public static String getData(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }

    public static String getData(Context context, String key,String defaultVal) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultVal);
    }

    public static void saveBool(Context context, String key, boolean val) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, val).commit();
    }

    public static boolean getBool(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
    }
}
