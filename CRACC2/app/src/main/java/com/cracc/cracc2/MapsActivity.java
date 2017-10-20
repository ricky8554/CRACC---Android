package com.cracc.cracc2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.CameraPosition;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private View mapView;
    private Button location;
    private Button management;
    private Button chat;
    private Button mainicon;
    private FrameLayout gamemanagement;
    private FrameLayout gamemanagementrest;
    private FrameLayout chatboard;
    private FrameLayout chatboardrest;
    private FrameLayout controlboard;
    private FrameLayout controlboardrest;
    private FrameLayout creategame;
    private FrameLayout information;
    private FrameLayout interest;
    private FrameLayout community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //getSupportActionBar().setTitle("Map Location Activity");
        //test

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        mapView = mapFrag.getView();

        //button set location back
        location = (Button) findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
            }
        });

        interest = (FrameLayout) findViewById(R.id.interestframe);
        interest.setVisibility(View.GONE);
        community = (FrameLayout) findViewById(R.id.communityframe);
        community.setVisibility(View.GONE);

        creategame = (FrameLayout) findViewById(R.id.creategame);
        creategame.setVisibility(View.GONE);
        information = (FrameLayout) findViewById(R.id.informationframe);
        information.setVisibility(View.GONE);
        gamemanagement = (FrameLayout) findViewById(R.id.gamemanagement);
        gamemanagement.setVisibility(View.GONE);
        gamemanagementrest = (FrameLayout) findViewById(R.id.gamemanagementrest);
        gamemanagementrest.setVisibility(View.GONE);
        controlboard = (FrameLayout) findViewById(R.id.controlboard);
        controlboard.setVisibility(View.GONE);
        controlboardrest = (FrameLayout) findViewById(R.id.controlboardrest);
        controlboardrest.setVisibility(View.GONE);
        chatboard = (FrameLayout) findViewById(R.id.chatboard);
        chatboard.setVisibility(View.GONE);
        chatboardrest = (FrameLayout) findViewById(R.id.chatboardrest);
        chatboardrest.setVisibility(View.GONE);
        management = (Button) findViewById(R.id.management);
        management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayManagement();
            }
        });
        chat = (Button) findViewById(R.id.chatbutton);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayChatboard();
            }
        });

        gamemanagementrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideManagement();
            }
        });

        chatboardrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideChatboard();
            }
        });

        controlboardrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideControlboard();
            }
        });

        mainicon = (Button) findViewById(R.id.mainicon);
        mainicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayControlboard();
            }
        });

    }

    public void logout(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void interest(View v) {
        hideControlboard();
        interest.setVisibility(View.VISIBLE);
    }

    public void community(View v) {
        hideControlboard();
        community.setVisibility(View.VISIBLE);
    }
    public void clean(View v ) {
        hideControlboard();
    }

    public void creategame(View v) {
        hideControlboard();
        creategame.setVisibility(View.VISIBLE);
        controlboardrest.setVisibility(View.VISIBLE);
    }

    public void information(View v) {
        hideControlboard();
        information.setVisibility(View.VISIBLE);
        controlboardrest.setVisibility(View.VISIBLE);
    }


    private void displayManagement() {
        gamemanagement.setVisibility(View.VISIBLE);
        gamemanagementrest.setVisibility(View.VISIBLE);
    }


    private void hideManagement() {
        hideControlboard();
    }

    private void displayControlboard() {
        controlboard.setVisibility(View.VISIBLE);
        controlboardrest.setVisibility(View.VISIBLE);
    }

    private void hideControlboard() {
        controlboardrest.setVisibility(View.GONE);
        controlboard.setVisibility(View.GONE);
        creategame.setVisibility(View.GONE);
        information.setVisibility(View.GONE);
        community.setVisibility(View.GONE);
        interest.setVisibility(View.GONE);
        chatboardrest.setVisibility(View.GONE);
        chatboard.setVisibility(View.GONE);
        gamemanagementrest.setVisibility(View.GONE);
        gamemanagement.setVisibility(View.GONE);
    }

    private void displayChatboard() {
        chatboardrest.setVisibility(View.VISIBLE);
        chatboard.setVisibility(View.VISIBLE);

    }

    private void hideChatboard() {
        hideControlboard();
    }


/*------------------------------ The code below is for google map and check permission----------------------*/

    private void getMyLocation() {
        LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        //chage map style
        boolean success = googleMap.setMapStyle(new MapStyleOptions(getResources()
                .getString(R.string.style_json)));

        //if the new map style did not implement
        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }

        //chagne buttom view
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            } else {
                //Request Location Permission
                checkLocationPermission();
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
        } else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        //mLocationRequest.setInterval(100000);
        //mLocationRequest.setFastestInterval(100000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera and set the zoom in percentage
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation? delete this
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
