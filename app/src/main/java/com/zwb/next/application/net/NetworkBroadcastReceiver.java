package com.zwb.next.application.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "网络消息");

    }
}
