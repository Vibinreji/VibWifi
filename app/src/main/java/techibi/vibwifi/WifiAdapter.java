package techibi.vibwifi;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class WifiAdapter extends ArrayAdapter<HashMap<String, String>> {
    Context context;
    int layoutResourceId;
    UserHolder holder = null;
    PopupWindow popupWindow;
    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

    public WifiAdapter(Context context, int layoutResourceId,
                       ArrayList<HashMap<String, String>> addressList) {
        super(context, layoutResourceId,addressList);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = addressList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        holder = null;
        ArrayList<HashMap<String, String>>hashmap_Current;


        if (row == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.ssid = (TextView) row.findViewById(R.id.ssid);
            holder.level= (TextView) row.findViewById(R.id.level);
            holder.capability= (TextView) row.findViewById(R.id.capabilities);
            holder.bssid= (TextView) row.findViewById(R.id.bssid);

            holder.complete= (ImageView) row.findViewById(R.id.strength);

            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        HashMap<String, String> Wifidata = data.get(position);
        holder.ssid.setText(Wifidata.get("SSID").toString());
        holder.level.setText("Wifi level "+Wifidata.get("level").toString());
        holder.capability.setText("capabilities "+Wifidata.get("capabilities").toString());
        holder.bssid.setText("BSSID "+Wifidata.get("BSSID").toString());



        int level = Integer.parseInt(Wifidata.get("level").toString());
                 if (level <= -70) {
                     holder.complete.setImageResource(R.drawable.ic_no_wifi_white);
                    } else if (level <= -60) {
                     holder.complete.setImageResource(R.drawable.ic_quat_wifi_white);
                    } else if (level <= -40) {
                     holder.complete.setImageResource(R.drawable.ic_half_wifi_white);
                    } else if (level <= -30) {
                     holder.complete.setImageResource(R.drawable.ic_full_wifi_white);
                    }


        return row;

    }

    static class UserHolder {
        TextView ssid;
        TextView level;
        TextView capability;
        TextView bssid;

        ImageView complete;
    }
}

