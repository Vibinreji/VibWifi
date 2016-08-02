package techibi.vibwifi_lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by System8 on 8/1/2016.
 */
public class Connect {

    private WifiManager wifiManager;
    Context context;

    public Connect(Context context ,String ssid,String password) {

        this.context=context;
        wifiManager = (WifiManager)context.getSystemService(context.WIFI_SERVICE);
        WifiConfiguration wifiConfig = new WifiConfiguration();
        wifiConfig.SSID = String.format("\"%s\"", ssid);
        wifiConfig.preSharedKey = String.format("\"%s\"", password);

        wifiManager.setWifiEnabled(true);
        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
        ConnectivityManager connManager = (ConnectivityManager)context. getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifi.isConnected())
        {


            Toast.makeText(context, "Wifi Connected" ,Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(context, "Wifi Not Connected" , Toast.LENGTH_LONG).show();
        }






    }








}
