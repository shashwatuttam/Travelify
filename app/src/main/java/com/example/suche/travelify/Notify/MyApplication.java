package com.example.suche.travelify.Notify;

/**
 * Created by Shashwat Aggarwal on 4/23/2017.
 */


import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.example.suche.travelify.R;
import com.example.suche.travelify.TypeSelection;

import java.util.List;
import java.util.UUID;

public class MyApplication extends Application {

    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification("Attraction Nearby!", "Found an attraction nearby, Check in Travelify", list.get(0).getMajor(), list.get(0).getMinor(), list.get(0).getProximityUUID().toString());
            }

            @Override
            public void onExitedRegion(Region region) {
                showNotification(
                        "Good Bye!", "Hope to see you again :D", region.getMajor(), region.getMinor(), region.getProximityUUID().toString());
            }
        });

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("monitored region",UUID.fromString(Constants.UID),
                        Constants.major, Constants.minor));
            }

        });


    }

    public void showNotification(String title, String message, int major, int minor, String uid) {
        // Show Notification.
        Intent notifyIntent = new Intent(this, TypeSelection.class);
        notifyIntent.putExtra(Constants.IS_BEACON, true);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        NotificationManager notificationManager =  (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

}
