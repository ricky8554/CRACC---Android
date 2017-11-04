package com.cracc.cracc2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import static android.R.attr.defaultValue;

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
    private static int RESULT_LOAD_IMAGE = 1;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private View mapView;
    private Button location;                    //button set location back
    private Button management;                  //button opent the game management board
    private Button chat;                        //button open the char board
    private Button mainicon;                    //button open the control board
    private Button settingicon;
    private Button informationicon;
    private BitmapDrawable bd = null;
    private BitmapDrawable bd1 = null;
    private BitmapDrawable bd2 = null;
    private BitmapDrawable bd3 = null;//informationsetting icon bitmapdrable
    private Bitmap bm;
    //bit map
    private Button controlboardbaricon;
    private Button star1;
    private Button star2;
    private Button star3;
    private Button star4;
    private Button star5;
    private FrameLayout activity_maps_base_frame; //base grame for the exit a frame
    private FrameLayout gamemanagement;         //game management frame
    private FrameLayout chatboard;              //chat board frame
    private FrameLayout controlboard;           //control board frame
    private FrameLayout creategame;             //create game frame
    private FrameLayout information;            //information frame
    private FrameLayout interest;               //interest frame
    private FrameLayout community;              //community frame
    private FrameLayout setting;              //setting in the information
    private EditText typeinlocation;            //location field in create game
    private EditText typeinname;                //name field in create game
    private EditText typeindate;                //date field in create game
    private EditText typeintime;                //time field in create game
    private EditText typeinnumpeople;           //number of people field in create game


    public static final String MyPREFERENCES = "CRACC.com.profile";
    public static final String EMAIL = "emailKey";
    public static final String GENDER = "genderKey";
    public static final String FIRST_NAME = "firstnamekey";
    public static final String LAST_NAME = "lastnamekey";
    public static final String BIRTHDAY = "birthdaykey";
    public static final String STARS = "starskey";
    public static final String PHOTOAVATAR = "Photoavatar";
    public static final String USERID = "UserId";
    public static final String LOGINTYPE = "LoginType";
    private String birthday;
    private String LastName;
    private String FirstName;
    private int Stars;
    private String Email;
    private String uid;
    private String gender = "";
    private String LoginType;
    private DatabaseReference cracc = FirebaseDatabase.getInstance().getReference().child("CRACC");
    private Bitmap iconbitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initialize();
        setAllFrametoInvisible();
        System.out.println("hello");
        //set the android navigation bar invisible, need to improvent
        /*
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        */

    }

    //set All frame to invisible
    private void setAllFrametoInvisible() {
        activity_maps_base_frame.setVisibility(View.GONE);
        gamemanagement.setVisibility(View.GONE);
        chatboard.setVisibility(View.GONE);
        controlboard.setVisibility(View.GONE);
        creategame.setVisibility(View.GONE);
        information.setVisibility(View.GONE);
        interest.setVisibility(View.GONE);
        community.setVisibility(View.GONE);
        setting.setVisibility(View.GONE);
    }

    //initialize the value of global variable, and assign the listener to proper variable.
    private void initialize() {
        //set the layout moved up when type in
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //innitialize the map variable
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        mapView = mapFrag.getView();
        //================assign each frame variable with it assosiate frame in xml================
        activity_maps_base_frame = findViewById(R.id.activity_maps_base_frame);
        location = findViewById(R.id.location);
        settingicon = findViewById(R.id.settingicon);
        informationicon = findViewById(R.id.informationicon);
        interest = findViewById(R.id.interestframe);
        community = findViewById(R.id.communityframe);
        creategame = findViewById(R.id.creategame);
        information = findViewById(R.id.informationframe);
        setting = findViewById(R.id.informationsettingframe);
        gamemanagement = findViewById(R.id.gamemanagement);
        controlboard = findViewById(R.id.controlboard);
        chatboard = findViewById(R.id.chatboard);
        management = findViewById(R.id.management);
        chat = findViewById(R.id.chatbutton);
        mainicon = findViewById(R.id.mainicon);
        textureView = (TextureView) findViewById(R.id.camera);
        controlboardbaricon = findViewById(R.id.controlboardbaricon);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        SharedPreferences settings = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        birthday = settings.getString(BIRTHDAY, "");
        FirstName = settings.getString(FIRST_NAME, "");
        Stars = settings.getInt(STARS, 0);
        uid = settings.getString(USERID, "");
        LoginType = settings.getString(LOGINTYPE, "");

        if(BitMapstore.getBitmapFromMemCache("iconbitmap") != null)
        {
            System.out.println("hello");
            iconbitmap = getCroppedBitmap(BitMapstore.getBitmapFromMemCache("iconbitmap"), 200);
            BitmapDrawable bdrawable = new BitmapDrawable(this.getResources(), iconbitmap);
            bd1 = new BitmapDrawable(this.getResources(), iconbitmap);
            bd2 = new BitmapDrawable(this.getResources(), iconbitmap);
            bd3 = new BitmapDrawable(this.getResources(), iconbitmap);
            bd = bdrawable;

            controlboardbaricon.setBackground(bd);
            mainicon.setBackground(bd1);
            settingicon.setBackground(bd2);
            informationicon.setBackground(bd3);
        }

        //============assign each edittext variable with it assosiate object in xml================
        typeinlocation = (EditText) findViewById(R.id.createlocation);
        typeinname = (EditText) findViewById(R.id.createname);
        typeindate = (EditText) findViewById(R.id.createdate);
        typeintime = (EditText) findViewById(R.id.createtime);
        typeinnumpeople = (EditText) findViewById(R.id.createpeople);
        //=====================set the clickListener to button or frame==========================
        textureView.setSurfaceTextureListener(surfaceTextureListener);
        activity_maps_base_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllFrametoInvisible();
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyLocation();
            }
        });

        setListner(management, gamemanagement);
        setListner(chat, chatboard);
        setListner(mainicon, controlboard);
    }

    //this function is for empty call which can be set on the empty frame to prevent frome exit
    public void empty(View v) {
    }

    //this method is called by the outside button which wish to clean or exit the frame
    public void clean(View v) {
        setAllFrametoInvisible();
        if (v == setting)
            display(information);
    }

    //this method is call by the creategame button under the control board under the main icon
    public void creategame(View v) {
        checkForaudioPermission();
        checkForPermission();
        setAllFrametoInvisible();
        display(creategame);
    }

    //this method is call by the information button under the control board under the main icon
    public void information(View v) {
        setAllFrametoInvisible();
        display(information);
    }

    public void inforamtionsetting(View v) {
        //checkForManagePermission();
        checkForREADPermission();

        setAllFrametoInvisible();
        display(setting);
    }

    //this method is call by the interest button under the control board under the main icon
    public void interest(View v) {
        setAllFrametoInvisible();
        interest.setVisibility(View.VISIBLE);
    }

    //this method is call by the community button under the control board under the main icon
    public void community(View v) {
        setAllFrametoInvisible();
        community.setVisibility(View.VISIBLE);
    }

    //this method is call by the logout button under the control board under the main icon
    public void logout(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void enterCamera(View v) {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);
        if (cameraDevice != null) {
            cameraDevice.close();
        }
    }

    public void star(View v) {

        if (v == star1)
            setStar(1);
        else if (v == star2)
            setStar(2);
        else if (v == star3)
            setStar(3);
        else if (v == star4)
            setStar(4);
        else if (v == star5)
            setStar(5);

    }

    //set the image of personal icon at information setting from the local image library
    public void setSettingicon(View v) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED /*|| ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.MANAGE_DOCUMENTS) != PackageManager.PERMISSION_GRANTED*/) {
            return;
        }


        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void save(View v) {
        setAllFrametoInvisible();
        information(v);
        if (bd != null)
            informationicon.setBackground(bd);
    }

    //=============================call by inside class=======================================
    //display the content call by different button depend on which view it gave
    private void display(View v) {
        v.setVisibility(View.VISIBLE);
        activity_maps_base_frame.setVisibility(View.VISIBLE);
    }

    //setLisentner
    private void setListner(View v, final View v1) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display(v1);
            }
        });
    }

    private void setStar(int c) {
        star1.setBackgroundResource(R.drawable.star2);
        star2.setBackgroundResource(R.drawable.star2);
        star3.setBackgroundResource(R.drawable.star2);
        star4.setBackgroundResource(R.drawable.star2);
        star5.setBackgroundResource(R.drawable.star2);

        switch (c) {
            case 5:
                star5.setBackgroundResource(R.drawable.star);
            case 4:
                star4.setBackgroundResource(R.drawable.star);
            case 3:
                star3.setBackgroundResource(R.drawable.star);
            case 2:
                star2.setBackgroundResource(R.drawable.star);
            case 1:
                star1.setBackgroundResource(R.drawable.star);

        }
    }

    //hide the keyboard
    /*
    private void hideKeyboard(){

        Log.i("++++++++++++","========================d=d=d=d=d=d=d=d=d==ddddd");
            if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(typeinname.getWindowToken(), 0);
            }

    }
    */
/*---------------the code below is for reading the external device image and adjust size--------*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            bm = getScaledBitmap(picturePath, 800, 800);
            bd = new BitmapDrawable(getResources(), getCroppedBitmap(BitmapFactory.decodeFile(picturePath), 300));
            settingicon.setBackground(bd);
            //getScaledBitmap(String picturePath, int width, int height)
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }

    private Bitmap getScaledBitmap(String picturePath, int width, int height) {
        BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
        sizeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, sizeOptions);

        int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

        sizeOptions.inJustDecodeBounds = false;
        sizeOptions.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(picturePath, sizeOptions);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    private Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;

        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth() / factor), (int) (bmp.getHeight() / factor), false);
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(radius / 2 + 0.7f,
                radius / 2 + 0.7f, radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
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
        if (cameraDevice != null) {
            cameraDevice.close();
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
            case REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {

                        //do somthing
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;

            }
            case REQUEST_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.RECORD_AUDIO)
                            == PackageManager.PERMISSION_GRANTED) {

                        //do somthing
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;

            }
            /*
            case REQUEST_MANAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.MANAGE_DOCUMENTS)
                            == PackageManager.PERMISSION_GRANTED) {

                        //do somthing
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied MANAGE", Toast.LENGTH_LONG).show();
                }
                return;

            }
            */
            case REQUEST_READ: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        //do somthing
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied READ", Toast.LENGTH_LONG).show();
                }
                return;

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }


    }

    //============================Camera==================================================
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_AUDIO = 2;
    private static final int REQUEST_READ = 3;
    //private static final int REQUEST_MANAGE = 4;

    private void checkForREADPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Granted");
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d(TAG, "READ external storage Permission Required!!");
                new AlertDialog.Builder(this)
                        .setTitle("READ external storage Permission Needed")
                        .setMessage("This app needs the READ external storage permission, please accept to use Select image functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                        REQUEST_READ);
                            }
                        })
                        .create()
                        .show();

            }
            ActivityCompat.
                    requestPermissions(MapsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ);

        }
    }

    /*
    private void checkForManagePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_DOCUMENTS);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Granted");
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.MANAGE_DOCUMENTS)) {
                Log.d(TAG, "Manage Permission Required!!");
                new AlertDialog.Builder(this)
                        .setTitle("Manage Permission Needed")
                        .setMessage("This app needs the Manage permission, please accept to use select image functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.MANAGE_DOCUMENTS},
                                        REQUEST_MANAGE);
                            }
                        })
                        .create()
                        .show();

            }
            ActivityCompat.
                    requestPermissions(MapsActivity.this, new String[]{Manifest.permission.MANAGE_DOCUMENTS}, REQUEST_MANAGE);

        }
    }
    */
    private void checkForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Granted");
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.CAMERA)) {
                Log.d(TAG, "Camera Permission Required!!");
                new AlertDialog.Builder(this)
                        .setTitle("Camera Permission Needed")
                        .setMessage("This app needs the Camera permission, please accept to use Camera functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.CAMERA},
                                        REQUEST_CAMERA);
                            }
                        })
                        .create()
                        .show();

            }
            ActivityCompat.
                    requestPermissions(MapsActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);

        }
    }

    private void checkForaudioPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Granted");
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.RECORD_AUDIO)) {
                Log.d(TAG, "Audio Permission Required!!");
                new AlertDialog.Builder(this)
                        .setTitle("Audio Permission Needed")
                        .setMessage("This app needs the Audio Record permission, please accept to use Record Audio functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{android.Manifest.permission.RECORD_AUDIO},
                                        REQUEST_AUDIO);
                            }
                        })
                        .create()
                        .show();

            }
            ActivityCompat.
                    requestPermissions(MapsActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_AUDIO);

        }
    }

    private Size previewsize;
    private TextureView textureView;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder previewBuilder;
    private CameraCaptureSession previewSession;
    Button getpicture;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    public void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String camerId = manager.getCameraIdList()[1];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(camerId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            previewsize = map.getOutputSizes(SurfaceTexture.class)[0];
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            manager.openCamera(camerId, stateCallback, null);
        } catch (Exception e) {
        }
    }

    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice = camera;
            startCamera();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
        }

        @Override
        public void onError(CameraDevice camera, int error) {
        }
    };


    void startCamera() {
        if (cameraDevice == null || !textureView.isAvailable() || previewsize == null) {
            return;
        }
        SurfaceTexture texture = textureView.getSurfaceTexture();
        if (texture == null) {
            return;
        }
        texture.setDefaultBufferSize(previewsize.getWidth(), previewsize.getHeight());
        Surface surface = new Surface(texture);
        try {
            previewBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (Exception e) {
        }
        previewBuilder.addTarget(surface);
        try {
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    previewSession = session;
                    getChangedPreview();
                }

                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                }
            }, null);
        } catch (Exception e) {
        }
    }

    void getChangedPreview() {
        if (cameraDevice == null) {
            return;
        }
        previewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        HandlerThread thread = new HandlerThread("changed Preview");
        thread.start();
        Handler handler = new Handler(thread.getLooper());
        try {
            previewSession.setRepeatingRequest(previewBuilder.build(), null, handler);
        } catch (Exception e) {
        }
    }

}
