package com.zwb.next.application.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;

public abstract class AbstractApp {

    /**
     * 进程式:退出应用
     */
    protected static void killApp() {
        Process.killProcess(Process.myPid());
    }

    /**
     * 验证可接收的Intent应用
     *
     * @param context
     * @param intent
     * @return
     */
    protected static boolean isIntentSafe(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }

}
