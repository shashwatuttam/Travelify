package com.example.suche.travelify;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class StartScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, PlaceSelectionListener {

    private static final String LOG_TAG = StartScreen.class.getSimpleName();
    //location values
    public static double latitude,longitude;
    public static String placename;
    public static int usegps = 0;
    public static int usesearch=-1;
    final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private ImageView gpsbutton;
    // Helper method to format information about a place nicely.
    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(LOG_TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        // GPS
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        latitude = 0;   //intial assignment for checking

        //Autocomplete
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(this);


        final LinearLayout gotomain = (LinearLayout) findViewById(R.id.gotomain);
        gotomain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(usesearch==-1 && usegps==0)
                {
                    Toast.makeText(StartScreen.this, "Please select a place or use GPS!", Toast.LENGTH_SHORT).show();
                }
                else{
                    // Perform action on click
                    Intent mainIntent = new Intent(StartScreen.this, TypeSelection.class);
                    // Start the new activity
                    startActivity(mainIntent);
                }
            }
        });

        gpsbutton = (ImageView) findViewById(R.id.GPSButtonView);
        gpsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGoogleApiClient.isConnected()){
                    getMyLocation();
                }
                else
                    Toast.makeText(StartScreen.this, "Still connecting!", Toast.LENGTH_SHORT).show();
                if(usegps==0 && latitude!=0){
                    usegps = 1;
                    gpsbutton.setColorFilter(ContextCompat.getColor(StartScreen.this,R.color.blue));
                }
                else{
                    usegps = 0;
                    gpsbutton.setColorFilter(ContextCompat.getColor(StartScreen.this,R.color.black));
                }
            }
        });

    }

    private void getMyLocation(){

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            Toast.makeText(this,"Current location detected!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Turn on GPS to use this feature!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getMyLocation();

                } else {
                    Toast.makeText(this, "Please allow location to use this feature!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //getMyLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this,
                "onConnectionSuspended: " + String.valueOf(i),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,
                "onConnectionFailed: \n" + connectionResult.toString(),
                Toast.LENGTH_LONG).show();
    }

    //Auto complete method overrides
    //Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(LOG_TAG, "Place Selected: " + place.getName());

        //set value of parameters
        placename = (String) place.getName();
        usegps = 0;
        usesearch=1;
        gpsbutton.setColorFilter(ContextCompat.getColor(StartScreen.this,R.color.black));
    }

    //Callback invoked when PlaceAutocompleteFragment encounters an error.
    @Override
    public void onError(Status status) {
        Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

}



