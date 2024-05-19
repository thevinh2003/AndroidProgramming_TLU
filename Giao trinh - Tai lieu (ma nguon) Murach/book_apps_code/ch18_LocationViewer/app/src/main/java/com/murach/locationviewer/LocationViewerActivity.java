package com.murach.locationviewer;

import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.content.IntentSender;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationViewerActivity extends Activity implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final int UPDATE_INTERVAL = 5000;         // 5 seconds
    public static final int FASTEST_UPDATE_INTERVAL = 2000; // 2 seconds

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private TextView coordinatesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_viewer);

        coordinatesTextView = (TextView)findViewById(R.id.cooridinatesTextView);
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL);

        LocationManager locationManager =
                (LocationManager) getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "Please enable GPS!",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    //**************************************************************
    // Implement ConnectionCallbacks interface
    //****************************************************************
    @Override
    public void onConnected(Bundle bundle) {
        Location location =
                LocationServices.FusedLocationApi
                        .getLastLocation(googleApiClient);
        if (location != null) {
            coordinatesTextView.setText(
                    location.getLatitude() + "|" + location.getLongitude());
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    //**************************************************************
    // Implement OnConnectionFailedListener
    //****************************************************************
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Connection failed. Error code: " +
                            connectionResult.getErrorCode())
                    .show();
        }
    }

    //**************************************************************
    // Implement LocationListener
    //****************************************************************
    @Override
    public void onLocationChanged(Location location) {
        coordinatesTextView.setText(
                location.getLatitude() + "|" + location.getLongitude());
    }
}