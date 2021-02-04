package com.zwb.next.util;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import androidx.annotation.RequiresApi;

import static android.os.storage.StorageManager.ACTION_MANAGE_STORAGE;

public class StorageUtils {

    private final Context mContext;

    private StorageUtils(Context context) {
        mContext = context;
    }

    public static StorageUtils getInstance(Context context) {
        return new StorageUtils(context);
    }

    public String getFile()  {
        File dir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals("mounted");
        if (sdCardExist) {
            dir = Environment.getExternalStorageDirectory();
        } else {
            dir = mContext.getCacheDir();
        }
        return dir.toString();
    }

    /**
     * 查询空闲空间，请求用户移除文件
     */
    public void queryFreeSpace() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O ) {

        } else {
            this.queryFreeSpace_o();
        }
    }

    // App needs 10 MB within internal storage.
    private static final long NUM_BYTES_NEEDED_FOR_MY_APP = 1024 * 1024 * 10L;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void queryFreeSpace_o() {
        StorageManager storageManager = mContext.getSystemService(StorageManager.class);
        File dir = mContext.getFilesDir();
        try {
            UUID appSpecificInternalDirUuid = storageManager.getUuidForPath(dir);
            long availableBytes =
                    storageManager.getAllocatableBytes(appSpecificInternalDirUuid);
            if (availableBytes <= NUM_BYTES_NEEDED_FOR_MY_APP) {
                storageManager.allocateBytes(
                        appSpecificInternalDirUuid, NUM_BYTES_NEEDED_FOR_MY_APP);
            } else {
                // To request that the user remove all app cache files instead, set
                // "action" to ACTION_CLEAR_APP_CACHE.
                Intent storageIntent = new Intent();
                storageIntent.setAction(ACTION_MANAGE_STORAGE);
                mContext.startActivity(storageIntent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED;
    }

    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED ||
                Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED_READ_ONLY;
    }

    private boolean isExternalStorageRemovable() {
        return Environment.isExternalStorageRemovable();
    }
}
