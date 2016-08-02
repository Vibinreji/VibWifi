package techibi.vibwifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import techibi.vibwifi_lib.MultipleScan;
import techibi.vibwifi_lib.vibwifi;

public class MainActivity extends Activity {


    private WifiManager wifiManager;
    private BroadcastReceiver wifiScanReceiver;
    private SharedPreferences sharedPreferences;
    WifiAdapter userAdapter;
    private boolean isScanning = true;
    private boolean jawsAutoEnabledWifi = false;
    ArrayList<HashMap<String, String>> WifiList;
    HashMap<String, String> queryValues;
    private PopupWindow popup;
    vibwifi vib;
    private Intent intent;
    TextView switchinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar.(toolbar);
         vib= new vibwifi();

        wifiManager = (WifiManager)getApplicationContext(). getSystemService(Context.WIFI_SERVICE);
        //wifiScanReceiver = new WifiScanReceiver();
        WifiList = new ArrayList<HashMap<String, String>>();

        switchinfo = (TextView)  findViewById(R.id.switchnotice);
        switchinfo.setVisibility(View.VISIBLE);


        Switch onOffSwitch = (Switch) findViewById(R.id.on_off_switch);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( isChecked==true)
                {

                    vib.StartScan(MainActivity.this,1000);
                    //vib.singlescan(MainActivity.this);

                    switchinfo.setVisibility(View.GONE);
                            }
                else
                {

                    if(userAdapter!=null)
                    {
                        userAdapter.clear();
                    }
                    switchinfo.setVisibility(View.VISIBLE);

                    Toast.makeText(MainActivity.this, "disabled Wifi search",
                            Toast.LENGTH_LONG).show();               }

            }

        });

    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            updateUI(intent);
        }
    };

    private void updateUI(Intent intent) {

        WifiList = (ArrayList<HashMap<String, String>>) intent.getSerializableExtra("wifilist");
        System.out.println("serialized data.."+WifiList);

        if (!WifiList.isEmpty()) {

            userAdapter = new WifiAdapter(MainActivity.this, R.layout.wifiitem,
                    WifiList);
            ListView userList = (ListView) findViewById(R.id.list);
            userList.setItemsCanFocus(false);
            userList.setAdapter(userAdapter);
            userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position,
                                        long arg3) {
                    HashMap<String, String> Wifidetail = WifiList.get(position);
                    final String ssid = Wifidetail.get("SSID").toString();

                    iniPopupWindow(ssid);
                }
            });




        } else {
            Toast.makeText(MainActivity.this, "No Wifi Found", Toast.LENGTH_SHORT).show();// display toast

        }

    }
    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
       // startService(intent);
        registerReceiver(broadcastReceiver, new IntentFilter(MultipleScan.BROADCAST_ACTION));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void iniPopupWindow(final String ssid ) {
        try {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.connect_popup,
                    (ViewGroup) findViewById(R.id.popup_element));
            popup = new PopupWindow(layout, 300, 370, true);
            popup.showAtLocation(layout, Gravity.CENTER, 0, 0);

            final EditText editText_password = (EditText) layout.findViewById(R.id.password);

            Button btnconnect = (Button) layout.findViewById(R.id.btn_connect);
            btnconnect.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {



                    vib.connectwifi(getApplicationContext(),ssid,editText_password.getText().toString());
                    popup.dismiss();


                }

            });
            ImageView dismiss = (ImageView) layout.findViewById(R.id.dismiss);
            dismiss.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v)
                {

                    popup.dismiss();


                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
