package com.zwb.next;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zwb.next.activities.FilesActivity;
import com.zwb.next.activities.SampleListActivity;
import com.zwb.next.application.net.NetworkBroadcastReceiver;
import com.zwb.next.application.notification.NotificationDirector;
import com.zwb.next.application.systemui.FullScreenActivity;
import com.zwb.next.util.NetworkUtils;

import androidx.annotation.Nullable;

public class MainActivity extends FullScreenActivity implements View.OnClickListener {

    private static ArrayMap<String,Class> activityMap = new ArrayMap<>();

    private BroadcastReceiver mNetworkBroadcastReceiver = new NetworkBroadcastReceiver();

    static {
        activityMap.put("atc_files",FilesActivity.class);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button  = findViewById(R.id.btn);
        button.setOnClickListener(this);

        IntentFilter intentFilter = new IntentFilter("com.zwb.next.action");
        this.registerReceiver(mNetworkBroadcastReceiver, intentFilter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkBroadcastReceiver);
    }

    private static final long NUM_BYTES_NEEDED_FOR_MY_APP = 1024 * 1024 * 10L;

    @Override
    public void onClick(View v) {
//        Intent intent = new Intent();
//        intent.setClass(this, activityMap.get("atc_files"));
//        startActivity(intent);

        boolean isOnline = NetworkUtils.isNetworkAvailable(MainActivity.this);
        boolean isWifi = NetworkUtils.isWifiConnection(MainActivity.this);
        boolean isGPRS = NetworkUtils.isCellularConnection(MainActivity.this);

        String ip = NetworkUtils.getLocalIpV4Address_MobileNetwork();
        String wip = NetworkUtils.getLocalIpV4Address_WifiNetwork(MainActivity.this);
        Log.d("网络检测", ""+isOnline);
        Log.d("wifi检测", ""+isWifi);
        Log.d("GPRS检测", ""+isGPRS);
        Log.d("ip检测", ""+ip);
        Log.d("wifi的ip检测", ""+wip);

        NotificationDirector director = new NotificationDirector(MainActivity.this);
        Notification notification = director.newNotification(null,"版本通知升级>>>>");

       // director.notify(notification);

        //director.notify(notification);

        //director.notify(notification);




        //Intent intent = new Intent(this, ScrollingActivity.class);
        //startActivity(intent);


        Intent intent = new Intent(this, SampleListActivity.class);
        startActivity(intent);
       // finish();

       // MapUtils.startAMapNav(this);

        //MapUtils.startBMapNav(this);


        sendBroadcast(new Intent("com.zwb.next.action"));

    }


}
