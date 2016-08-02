package techibi.vibwifi_lib;

/**
 * Created by admin on 4/28/2016.
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultipleScan extends Service {
    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.websmithing.broadcasttest.displayevent";
    private final Handler handler = new Handler();
    Intent intent;
    int counter = 0;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiScanReceiver;
    private boolean isScanning = true;
    private boolean jawsAutoEnabledWifi = false;
    ArrayList<HashMap<String, String>> WifiList;
    int check=0;


    @Override
    public void onCreate() {
        super.onCreate();

        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent1, int startId) {

        wifiManager = (WifiManager)getApplicationContext(). getSystemService(Context.WIFI_SERVICE);
        wifiScanReceiver = new WifiScanReceiver();
        WifiList = new ArrayList<HashMap<String, String>>();
        registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        registerReceiver(wifiScanReceiver, new IntentFilter(MultipleScan.BROADCAST_ACTION));
        Bundle extras = intent1.getExtras();
        int intervel = extras.getInt("intervel");
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, intervel);


        if (!wifiManager.isWifiEnabled()) {
            jawsAutoEnabledWifi = true;
            Toast.makeText(getApplicationContext(), "Turn On  Wifi",
                    Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }

    }


    private Runnable sendUpdatesToUI = new Runnable() {
        public void run() {
            wifiManager.startScan();

            handler.postDelayed(this, 1000); // 10 seconds


        }
    };

    public class WifiScanReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context c, Intent intent) {
            WifiList.clear();

            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            String current = wifiInfo.getSSID();
            String ssid = wifiInfo.getSSID().replaceAll("^\"(.*)\"$", "$1");
            System.out.println(current);

            System.out.println("Connected ssid" + wifiInfo.getSSID());
            List<ScanResult> results = wifiManager.getScanResults();

            for (ScanResult scanResult : wifiManager.getScanResults()) {

                HashMap<String, String> map = new HashMap<String, String>();


                        map.put("BSSID", scanResult.BSSID);
                        map.put("SSID", scanResult.SSID);
                        map.put("level", Integer.toString(scanResult.level));
                        map.put("capabilities", scanResult.capabilities);



                WifiList.add(map);
            }

            DisplayLoggingInfo(WifiList);


        }
    }
    private void DisplayLoggingInfo(ArrayList<HashMap<String, String>> ddd) {
        intent.putExtra("wifilist", ddd);

        sendBroadcast(intent);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(sendUpdatesToUI);
        unregisterReceiver(wifiScanReceiver);
        super.onDestroy();
    }


}