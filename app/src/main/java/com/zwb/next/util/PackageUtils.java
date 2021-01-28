package com.zwb.next.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Process;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * @author zhouwb
 */
public final class PackageUtils {

    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }

    /**
     * 安装应用包
     * @param apkPath
     */
    public static  void installAPK(Context context, String apkPath) {
        File toInstall = new File(apkPath);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName(), toInstall);
            intent.setData(apkUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(toInstall), "application/vnd.android.package-archive");
        }

        context.startActivity(intent);
        Process.killProcess(Process.myPid());
    }

}
