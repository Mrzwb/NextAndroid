package com.zwb.next.application.notification;

import android.app.Notification;
import android.content.Context;

import com.zwb.next.R;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class DefaultNotificationBuilder extends NotificationBuilder {

    public DefaultNotificationBuilder(@NonNull Context context) {
        super(context, R.string.channel_id);
        this.createNotificationChannel();
    }

    @Override
    public Notification build(String title, String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, String.valueOf(this.chanelID))
                .setSmallIcon(R.mipmap.avatar)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        return builder.build();
    }

}
