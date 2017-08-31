package com.example.suche.travelify;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.example.suche.travelify.Notify.Constants;

import java.util.List;
import java.util.UUID;

/**
 * Created by Shashwat Uttam on 4/4/2017.
 */

public class TypeSelection extends AppCompatActivity {


    Boolean discovered = false;
    String  beaconmajor;
    private BeaconManager beaconManager;
    private Region region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type_selection);

        final Intent intent = getIntent();
        if (intent.getBooleanExtra(Constants.IS_BEACON, false)) {
        }

        // Start beacon ranging
        beaconManager = new BeaconManager(this);
        region = new Region("Minion region", UUID.fromString(Constants.UID), null, null);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!discovered && list.size() > 0) {
                    Beacon nearestBeacon = list.get(0);
                    beaconmajor = Integer.toString(nearestBeacon.getMajor());
                    Log.e("Discovered", "Nearest places: " + nearestBeacon.getMajor());
                    discovered = true;
                }
            }
        });

        // Get runtime permissions for Android M
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(TypeSelection.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WAKE_LOCK,
                        //Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.VIBRATE,
                }, 0);
            }
        }


        CardView type1 = (CardView) findViewById(R.id.type1);
        CardView type2 = (CardView) findViewById(R.id.type2);
        CardView type3 = (CardView) findViewById(R.id.type3);
        CardView type4 = (CardView) findViewById(R.id.type4);

        type1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TypeSelection.this,MainActivity.class);
                i.putExtra("TYPE",1);
                startActivity(i);
            }
        });

        type2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TypeSelection.this,MainActivity.class);
                i.putExtra("TYPE",2);
                startActivity(i);
            }
        });

        type3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TypeSelection.this,MainActivity.class);
                i.putExtra("TYPE",3);
                startActivity(i);
            }
        });

        type4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TypeSelection.this,MainActivity.class);
                i.putExtra("TYPE",4);
                startActivity(i);
            }
        });
    }
}
