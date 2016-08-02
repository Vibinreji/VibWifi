package techibi.vibwifi_lib;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

/**
 * Created by System8 on 7/30/2016.
 */
public class vibwifi
{

    public vibwifi() {

    }

    public void  StartScan(@NonNull Context context, int intevel)
        {

            Intent intent = new Intent(context, MultipleScan.class);
            intent.putExtra("intervel", intevel);
            context.startService(intent);


        }



    public void StopScan(@NonNull Context context) {

            Intent intent = new Intent(context, MultipleScan.class);
            context.stopService(intent);
        }
    public void singlescan(@NonNull Context context) {

          SingleScan singlescan = new SingleScan(context);

    }
    public void connectwifi(@NonNull Context context,String ssid,String password) {

        Connect connect = new Connect(context,ssid,password);

    }
}
