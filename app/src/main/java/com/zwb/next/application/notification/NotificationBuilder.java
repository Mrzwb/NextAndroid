package com.zwb.next.application.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.zwb.next.R;

import androidx.annotation.NonNull;

public abstract class NotificationBuilder {

    protected final Context mContext;

    protected int chanelID;

    public NotificationBuilder(@NonNull Context context, int chanelId) {
        this.mContext = context;
        this.chanelID = chanelId;
    }

    public abstract Notification build(String title, String content);

    /**
     * 创建渠道并设置重要性
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = mContext.getString(R.string.channel_name);
            String description = mContext.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(chanelID), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public int getChanelId() {
        return this.chanelID;
    }

    public void setChanelId(int chanelId) {
        this.chanelID = chanelId;
    }
}
