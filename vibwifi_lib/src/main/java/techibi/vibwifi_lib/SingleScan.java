package techibi.vibwifi_lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by System8 on 7/30/2016.
 */
public class SingleScan {

    ArrayList<HashMap<String, String>> WifiList;
    Intent intent;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiScanReceiver;
    Context context;

    private static final String TAG = "BroadcastService";
    public static final String BROADCAST_ACTION = "com.websmithing.broadcasttest.displayevent";

    public SingleScan(Context context ) {

        this.context=context;
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {

            wifiManager.setWifiEnabled(true);
        }

        wifiScanReceiver = new WifiScanReceiver();
        wifiManager.startScan();

        WifiList = new ArrayList<HashMap<String, String>>();
        context.registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        intent = new Intent(BROADCAST_ACTION);





    }

    public class WifiScanReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context c, Intent intent) {
            WifiList.clear();
            HashMap<String, String> map = new HashMap<String, String>();

            for (ScanResult scanResult : wifiManager.getScanResults()) {



                        map.put("BSSID", scanResult.BSSID);
                        map.put("SSID", scanResult.SSID);
                        map.put("level", Integer.toString(scanResult.level));
                        map.put("connection", "Connected");
                        map.put("capabilities", scanResult.capabilities);


                    }





                WifiList.add(map);
                DisplayLoggingInfo(WifiList);
            }



    }
    private void DisplayLoggingInfo(ArrayList<HashMap<String, String>> scanresult) {
        intent.putExtra("wifilist", scanresult);

        context.sendBroadcast(intent);
    }
}