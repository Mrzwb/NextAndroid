package com.zwb.next.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;
import android.widget.Toast;

/**
 * 第三方地图工具
 *
 * @author zhouwb
 */
public final class MapUtils {

    /**
     * 唤起高德路径规划
     * @param context
     */
    public static void startAMapPath(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        Uri uri = Uri.parse("amapuri://route/plan/?sourceApplication=&dname=天安门&dev=0&t=0");
        intent.setData(uri);

        if (intent.resolveActivity( context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            toast(context, "请先安装高德地图");
        }
    }

    /**
     * 唤起百度路径规划
     */
    public static void startBMapPath(Context context) {
        Intent intent = new Intent();
        Uri uri = Uri.parse("baidumap://map/navi?query=故宫&src="+ getPackageName(context));
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            toast(context, "请先安装百度地图");
        }
    }

    private static String getPackageName(Context context) {
        return context.getPackageName();
    }

    private static void toast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
