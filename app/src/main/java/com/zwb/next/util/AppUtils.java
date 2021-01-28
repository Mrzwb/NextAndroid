package com.zwb.next.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.Settings;

/**
 * @author zhouwb
 */
public class AppUtils {

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        if (isIntentSafe(context, intent)) {
            context.startActivity(intent);
        }
    }

    /**
     * 通话
     * @param context
     * @param tel
     * @param
     */
    public static void startPhoneCall(Context context, String tel, boolean autoCall) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        if (autoCall) {
            intent = new Intent(Intent.ACTION_CALL);
        }
        intent.setData(Uri.parse("tel:"+ tel));
        if (isIntentSafe(context, intent)) {
            context.startActivity(intent);
        }
    }

    /**
     * 创建闹钟
     * @param context
     * @param message
     * @param hour
     * @param minutes
     */
    public static void startAlarmSetting(Context context, String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        if (isIntentSafe(context, intent)) {
            context.startActivity(intent);
        }
    }

    /**
     * 验证可接收的Intent应用
     * @param context
     * @param intent
     * @return
     */
    private static boolean isIntentSafe(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }

}
