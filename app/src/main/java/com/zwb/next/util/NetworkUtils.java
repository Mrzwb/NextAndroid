package com.zwb.next.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import static android.net.NetworkCapabilities.TRANSPORT_CELLULAR;
import static android.net.NetworkCapabilities.TRANSPORT_WIFI;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

public class NetworkUtils {

    /**
     * 检查网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(@NonNull Context context) {
        NetworkCapabilities capabilities = getCapabilities(context);
        if (null == capabilities) {
            return false;
        }
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }

    /**
     * 获取网络信息
     * @return
     */
    @RequiresApi(LOLLIPOP)
    public static NetworkCapabilities getCapabilities(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connMgr.getActiveNetwork();
            capabilities = connMgr.getNetworkCapabilities(network);
        }
        return capabilities;
    }

    /**
     *  获取网络链接信息
     * @param context
     * @param transportType @
     * @return
     */
    @RequiresApi(LOLLIPOP)
    private LinkProperties getLinkProperties(Context context,int transportType) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network[] networks = connMgr.getAllNetworks();
        for (Network n : networks) {
            LinkProperties lp = connMgr.getLinkProperties(n);
            NetworkCapabilities np = connMgr.getNetworkCapabilities(n);
            String iname =  lp.getInterfaceName();
            if (iname != null && np != null) {
                //Log.d(TAG, ">>> " + iname + ": " + np.hasTransport(cap));
                if (np.hasTransport(transportType))
                    return lp;
            }
        }
        return null;
    }

    /**
     * 是否为wifi网络
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnection(Context context) {
        NetworkCapabilities capabilities = getCapabilities(context);
        if (null == capabilities) {
            return false;
        }
        return capabilities.hasTransport(TRANSPORT_WIFI);
    }

    /**
     *  是否为移动网络
     * @return 返回boolean
     */
    public static boolean isCellularConnection(Context context) {
        NetworkCapabilities capabilities = getCapabilities(context);
        if (null == capabilities) {
            return false;
        }
        return capabilities.hasTransport(TRANSPORT_CELLULAR);
    }

    /**
     * 移动网络下 获取本地IP
     * @return
     */
    public static String getLocalIpV4Address_MobileNetwork() {
        try {
            String ipv4;
            ArrayList<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nilist) {
                ArrayList<InetAddress> ialist = Collections.list(ni.getInetAddresses());
                for (InetAddress address : ialist) {
                    if (!address.isLoopbackAddress() && !address.isLinkLocalAddress()) {
                        ipv4 = address.getHostAddress();
                        return ipv4;
                    }
                }
            }

        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Wifi网络下 获取本地IP
     *
     * @param context
     * @return
     */
    public static String getLocalIpV4Address_WifiNetwork(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    //获取Wifi ip 地址
    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }


}
