# VibWifi
This Android library used to Scan Wifi single or multiple times and get connect the scaned wifi 



Anyone can simple scan and connect the wifi using vibwifi library

You can scan wifi using below 

```html
vibwifi vib= new vibwifi();
vib.StartScan(MainActivity.this,1000);
```

For single scan use below code

```html
vibwifi vib= new vibwifi();
vib.singlescan(MainActivity.this);

```
Get scanned results via intent

```html
  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            updateUI(intent);
        }
    };

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
```

You can also connect the wifi using below codes

```html
vib.connectwifi(getApplicationContext(),ssid,password);

```


