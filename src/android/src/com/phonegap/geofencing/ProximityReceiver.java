package com.phonegap.geofencing;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static com.phonegap.geofencing.DGGeofencing.TAG;

public class ProximityReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
  String id = (String) intent.getExtras().get("id");
  Log.d(TAG, "received proximity alert for region " + id);

    if(DGGeofencing.getInstance() != null){
        DGGeofencing.getInstance().fireRegionChangedEvent(intent);
    }  
    else {

        String status = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false) ? "entering" : "exiting";

        intent=new Intent(context,CordovaApp.class);
        intent.putExtra("pntype","1");

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int defaults = Notification.DEFAULT_ALL;
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                    .setDefaults(defaults)
                    .setSmallIcon(context.getApplicationInfo().icon)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("Floral")
                    .setTicker("Floral")
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true);

            String message = "You are "+status+" to region.";
            if (message != null) {
                mBuilder.setContentText(message);
            } 

            String msgcnt = "1";
            if (msgcnt != null) {
                mBuilder.setNumber(Integer.parseInt(msgcnt));
            }

            int notId = 0;

            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String appName = "Test";

            mNotificationManager.notify((String) appName, notId, mBuilder.build());
        
    }
    
  }
  
}
