# VibWifi
This Android library used to Scan Wifi single or multiple times and get connect the scaned wifi 



Anyone can simple scan and connect the wifi using vibwifi library



Add vibwifi library via dependencies 

  
```html
compile 'com.techibi:vibwifi_lib:0.1.1'
```

Enable Location permission in manifest file

```html
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
```

You can scan wifi using below code

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
Marshmallow devices need runtime permissions

```html
if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
   requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                 PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION);

}else{

 vibwifi vib= new vibwifi();
 vib.singlescan(MainActivity.this);
   
}

@Override
 public void onRequestPermissionsResult(int requestCode, String[] permissions,
         int[] grantResults) {
     if (requestCode == PERMISSIONS_REQUEST_CODE_ACCESS_COARSE_LOCATION
             && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
         // Do something with granted permission
         
         vibwifi vib= new vibwifi();
        vib.singlescan(MainActivity.this);
       
     }
 }
```
