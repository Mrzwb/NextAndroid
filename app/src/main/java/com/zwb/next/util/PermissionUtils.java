package com.zwb.next.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    private static final int MY_PERMISSIONS_REQUEST = 10222;

    private static final String[] DEFAULT_PERMISSIONS = new String[] {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
    };

    @TargetApi(23)
    public static void requestPermissions(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    Toast.makeText(activity, "缺少权限", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, MY_PERMISSIONS_REQUEST);
                }
            } else {
                // Permission has already been granted
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Log.d(this.getClass().getName(), "获取手机权限失败");
                }
                return;
            }
        }
    }
}


