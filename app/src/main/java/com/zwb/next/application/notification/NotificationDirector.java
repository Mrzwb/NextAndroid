package com.zwb.next.application.notification;

import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

/**
 * @author zhouwb 2021-01-15
 */
public class NotificationDirector {

    private NotificationBuilder mNotificationBuilder;

    private NotificationManagerCompat mNotificationManager;

    private final Context mContext;

    public NotificationDirector (@NonNull Context context) {
        this.mContext = context;
        this.mNotificationManager = NotificationManagerCompat.from(mContext);
        this.mNotificationBuilder = new DefaultNotificationBuilder(mContext);
    }

    public NotificationDirector (@NonNull Context context, @NonNull NotificationBuilder builder) {
        this.mContext = context;
        this.mNotificationBuilder = builder;
        this.mNotificationManager = NotificationManagerCompat.from(context);
    }

    public Notification newNotification(String title, String content) {
        return mNotificationBuilder.build(title,content);
    }

    /**
     * 发出通知
     * @param notification
     */
    public void notify(Notification notification) {
        mNotificationManager.notify(getChanelId(), notification);
    }

    /**
     * 发出通知
     * @param tag
     * @param notification
     */
    public void notify(@Nullable String tag, @NonNull Notification notification) {
        mNotificationManager.notify(tag, getChanelId(), notification);
    }

    /**
     * 取消通知
     */
    public void cancel() {
        mNotificationManager.cancel(getChanelId());
    }

    /**
     * 取消通知
     * @param tag
     */
    public void cancel(@Nullable String tag) {
        mNotificationManager.cancel(tag, getChanelId());
    }

    /**
     * 取消所有通知
     */
    public void cancelAll() {
        mNotificationManager.cancelAll();
    }

    public NotificationBuilder getNotificationBuilder() {
        return mNotificationBuilder;
    }

    public NotificationDirector setNotificationBuilder(NotificationBuilder mNotificationBuilder) {
        this.mNotificationBuilder = mNotificationBuilder;
        return this;
    }

    private int getChanelId() {
        return this.mNotificationBuilder.getChanelId();
    }
}
