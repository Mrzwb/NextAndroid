package com.zwb.next.application.base;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.Settings;
import android.widget.Toast;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.core.content.FileProvider;

/**
 * 应用下载更新
 *
 * @author zhouwb 2020-07-16
 */
public class AppUpdate {

    private Context mContext;

    private String title;

    private long downloadId;

    protected ProgressDialog progressDialog;

    private static ScheduledExecutorService mScheduledExecutorService;

    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001 && AppUpdate.this.progressDialog != null) {
                AppUpdate.this.progressDialog.setProgress(msg.arg1);
                AppUpdate.this.progressDialog.setMax(msg.arg2);
            }
        }
    };

    public AppUpdate(Context context) {
        this.mContext = context;
        mScheduledExecutorService = Executors.newScheduledThreadPool(2);
    }

    public void update(String updateUrl, String tempApkName) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        this.mContext.registerReceiver(new DownloadManagerReceiver(), filter);

        if (Build.VERSION.SDK_INT <= 9) {
            Uri uri = Uri.parse(updateUrl);
            Intent downloadIntent = new Intent("android.intent.action.VIEW", uri);
            downloadIntent.addCategory(Intent.CATEGORY_BROWSABLE);
            this.mContext.startActivity(downloadIntent);
        } else {
            DownloadManager downloadManager = (DownloadManager) this.mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(updateUrl);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setAllowedOverMetered(true);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            int state = this.mContext.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (state != 2 && state != 3 && state != 4) {
                if (Environment.getExternalStorageState().equals("mounted") && null != this.mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)) {
                    request.setDestinationInExternalFilesDir(this.mContext, Environment.DIRECTORY_DOWNLOADS, tempApkName + ".apk");
                    if (this.title != null) {
                        request.setTitle(this.title);
                    }
                    this.downloadId = downloadManager.enqueue(request);
                    this.showDownLoadProgress();
                }
            } else {
                PackageManager packageManager = this.mContext.getPackageManager();
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:com.android.providers.downloads"));
                    if (intent.resolveActivity(packageManager) != null) {
                        this.mContext.startActivity(intent);
                    }
                } catch (ActivityNotFoundException var8) {
                    var8.printStackTrace();
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    if (intent.resolveActivity(packageManager) != null) {
                        this.mContext.startActivity(intent);
                    }
                }

                AppBase.killApp();
            }
        }
    }

    public void query() {
        DownloadManager downloadManager = (DownloadManager) this.mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(new long[]{this.downloadId});
        Cursor cursor = downloadManager.query(query);

        try {
            if (cursor !=null && cursor.moveToFirst()) {
                int mDownLoad_so_far = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                int mDownLoad_all = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                int mDownLoad_status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

                Message msg = Message.obtain();
                if (mDownLoad_so_far > 0) {
                    msg.what = 1001;
                    msg.arg1 = mDownLoad_so_far;
                    msg.arg2 = mDownLoad_all;
                    this.mHandle.sendMessage(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private String getDownloadPath(long id) {
        DownloadManager downloadManager = (DownloadManager) this.mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(new long[]{id});
        Cursor cursor = downloadManager.query(query);

        String path;
        int localUriIndex;
        for(path = null; cursor.moveToNext(); path = cursor.getString(localUriIndex)) {
            localUriIndex = cursor.getColumnIndex("local_uri");
        }

        if (cursor !=null ) {
            cursor.close();
        }

        return path;
    }

    private void installAPK(String apkPath) {
        File toInstall = new File(apkPath);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(this.mContext, this.mContext.getPackageName(), toInstall);
            intent.setData(apkUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(toInstall), "application/vnd.android.package-archive");
        }

        this.mContext.startActivity(intent);
        Process.killProcess(Process.myPid());
    }

    class DownloadManagerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L);
                if (AppUpdate.this.downloadId == id) {
                    String downloadPath = AppUpdate.this.getDownloadPath(id);
                    if (downloadPath == null) {
                        Toast.makeText(context, "下载异常!!!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String apkPath = downloadPath.replace("file://", "");
                    AppUpdate.this.installAPK(apkPath);
                    context.unregisterReceiver(this);
                    mScheduledExecutorService.shutdown();
                }

                Toast.makeText(context, "更新完成...", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void showDownLoadProgress() {
        this.progressDialog = new ProgressDialog(this.mContext);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
        this.execDownLoadQuery();
    }

    private void execDownLoadQuery() {
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable(){
            @Override
            public void run() {
                query();
            }
        }, 0, 300L, TimeUnit.MILLISECONDS);
    }

    public void setTitle(String title) { this.title = title; }

}
