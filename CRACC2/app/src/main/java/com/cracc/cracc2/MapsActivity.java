package com.cracc.cracc2;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
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
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.location.Location;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.R.attr.defaultValue;
import static java.security.AccessController.getContext;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    GoogleApiClient mGoogleApiClient1;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    Marker mChooseLocationMarker;
    private static int RESULT_LOAD_IMAGE = 6;
    private static int PLACE_AUTOCOMPLETE_REQUEST_CODE = 5;
    private static int PLACE_AUTOCOMPLETE_REQUEST_CODE_CREATEGAME = 7;

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_AUDIO = 2;
    private static final int REQUEST_READ = 3;
    private static final int Mutirequestcode = 4;

    private static final String TAG = MapsActivity.class.getSimpleName();
    private View mapView;
    private Button location;                    //button set location back
    private Button management;                  //button opent the game management board
    private Button chat;                        //button open the char board
    private Button mainicon;                    //button open the control board
    private Button settingicon;
    private Button informationicon;
    //informationsetting icon bitmapdrable
    private Bitmap bm;

    private Button mapsearchbar;
    //bit map
    private Button controlboardbaricon;
    private Button star1;
    private Button star2;
    private Button star3;
    private Button star4;
    private Button star5;
    private FrameLayout activity_maps_base_frame;   //base grame for the exit a frame
    private FrameLayout gamemanagement;             //game management frame
    private FrameLayout chatboard;                  //chat board frame
    private FrameLayout controlboard;               //control board frame
    private FrameLayout creategame;                 //create game frame
    private FrameLayout information;                //information frame
    private FrameLayout interest;                   //interest frame
    private FrameLayout community;                  //community frame
    private FrameLayout setting;                    //setting in the information
    private TextView informationtext;
    private TextView age;
    private TextView lastgame;
    private TextView play;
    private TextView canceled;
    private TextView agecontent;
    private TextView lastgamecontent;
    private TextView playcontent;
    private TextView canceledcontent;
    private TextView controlboardbartext;
    private EditText settingfirstname;
    private EditText settinglastname;
    private EditText settingemail;
    private BitmapDrawable bd;
    private boolean creategamevideo = false;

    //creategame button
    private Button videoicon;
    private PercentRelativeLayout videotexture;
    private RelativeLayout videoplay;
    private Button BacktoCreategame;
    private VideoView videoview;

    //creategame
    private Button typeinlocation;            //location field in create game
    private EditText typeinname;                //name field in create game
    private Button typeintime;                //time field in create game
    private Button typeinnumpeople;           //number of people field in create game
    private Button createbutton1;
    private Place creategameplace = null;


    public static final String MyPREFERENCES = "CRACC.com.profile";
    public static final String EMAIL = "emailKey";
    public static final String GENDER = "genderKey";
    public static final String NAME = "namekey";
    public static final String BIRTHDAY = "birthdaykey";
    public static final String STARS = "starskey";
    public static final String PHOTOAVATAR = "Photoavatar";
    public static final String USERID = "UserId";
    public static final String LOGINTYPE = "LoginType";
    private String birthday;
    private String Name;
    private int Stars;
    private String Email;
    private String uid;
    private String gender = "";
    private String LoginType;
    private DatabaseReference cracc = FirebaseDatabase.getInstance().getReference().child("CRACC");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Bitmap iconbitmap = null;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    //date picker
    private String date;
    private int hour;
    private int minute;
    private String ampm;

    //camera
    private boolean hasvideo = false;

    //set font
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initialize();


    }

    //set hide bar
    @Override
    public void onWindowFocusChanged(boolean hasFocas) {
        super.onWindowFocusChanged(hasFocas);
        View decorView = getWindow().getDecorView();
        if (hasFocas) {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    @Override
    protected void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient1 = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient1.connect();
        super.onStart();
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
        if(creategamevideo)
        {
            closeCamera();
            stopBackgroundThread();
        }
        creategamevideo = false;

    }

    //initialize the value of global variable, and assign the listener to proper variable.
    private void initialize() {
        settings = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = settings.edit();

        permission.checkpermissionall(this, Mutirequestcode,
                MapsActivity.this, "Location", new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE});

        Typeface myTypeface1 = Typeface.createFromAsset(getAssets(), "Myriad-Pro-Bold.ttf");
        Typeface myTypeface2 = Typeface.createFromAsset(getAssets(), "Myriad-Pro-Italic.ttf");
        Typeface myTypeface3 = Typeface.createFromAsset(getAssets(), "Myriad-Pro.ttf");
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
        mapsearchbar = findViewById(R.id.mapsearchbar);
        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);
        informationtext = findViewById(R.id.informationtext);
        age = findViewById(R.id.age);
        lastgame = findViewById(R.id.lastgame);
        play = findViewById(R.id.play);
        canceled = findViewById(R.id.canceled);
        agecontent = findViewById(R.id.agecontent);
        lastgamecontent = findViewById(R.id.lastgamecontent);
        playcontent = findViewById(R.id.playcontent);
        canceledcontent = findViewById(R.id.canceledcontent);
        controlboardbartext = findViewById(R.id.controlboardbartext);
        settingfirstname = findViewById(R.id.settingfirstname);
        settinglastname = findViewById(R.id.settinglastname);
        settingemail = findViewById(R.id.settingemail);

        final Activity content = this;
        final BitMapstore bit = new BitMapstore(this,"video");
        videoicon = findViewById(R.id.videoicon);
        videoicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog dialog = new BottomSheetDialog(content);
                dialog.setContentView(R.layout.bottomdialog);
                dialog.show();
                Button playvideo = dialog.findViewById(R.id.playvideo);
                Button deletevideo = dialog.findViewById(R.id.deletevideo);
                playvideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        videoplay.setVisibility(View.VISIBLE);

                        File videoFile = bit.getFile("introvideo");
                        if (videoFile != null) {
                            videoplay.setVisibility(View.VISIBLE);
                            videoview = findViewById(R.id.VideoView);

                            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.setLooping(true);
                                }
                            });
                            String path = videoFile.getAbsolutePath();
                            Uri uri = Uri.parse(path);
                            videoview.setVideoURI(uri);

                            videoview.start();
                        }
                    }
                });
                deletevideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        videoicon.setVisibility(View.GONE);
                        videotexture.setVisibility(View.VISIBLE);
                        bit.clear();

                        hasvideo = false;
                        creategamevideo = true;
                        if(creategamevideo) {
                            startBackgroundThread();
                            if (textureView.isAvailable()) {
                                setupCamera(textureView.getWidth(), textureView.getHeight(), CameraCharacteristics.LENS_FACING_FRONT);
                                connectCamera();
                            } else {
                                textureView.setSurfaceTextureListener(surfaceTextureListener);
                            }
                        }


                    }
                });
                //Utils.bottomsheetdialog(content, R.layout.bottomdialog);
                /*
                videoplay.setVisibility(View.VISIBLE);

                File videoFile = bit.getFile("introvideo");
                if (videoFile != null) {
                    videoplay.setVisibility(View.VISIBLE);
                    videoview = findViewById(R.id.VideoView);

                    videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setLooping(true);
                        }
                    });
                    String path = videoFile.getAbsolutePath();
                    Uri uri = Uri.parse(path);
                    videoview.setVideoURI(uri);

                    videoview.start();
                }
                */
            }
        });

        videotexture = findViewById(R.id.videotexture);
        videoplay = findViewById(R.id.videoplay);
        videoplay.setVisibility(View.GONE);
        Button ok = findViewById(R.id.ok);
        ok.setVisibility(View.GONE);
        BacktoCreategame = findViewById(R.id.BacktoCamera);
        BacktoCreategame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoplay.setVisibility(View.GONE);
                videoview.stopPlayback();
                videoview = null;
                //startPreview();

            }
        });

        videoview = findViewById(R.id.VideoView);


        //get The information
        birthday = settings.getString(BIRTHDAY, "").trim();
        Name = settings.getString(NAME, "");
        Stars = settings.getInt(STARS, 0);
        uid = settings.getString(USERID, "");
        LoginType = settings.getString(LOGINTYPE, "");
        Email = settings.getString(EMAIL, "");
        for (int i = 0; i < 20; i++) {
            if (BitMapstore.getBitmapFromMemCache("iconbitmap") != null) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (BitMapstore.getBitmapFromMemCache("iconbitmap") != null) {
            System.out.println("hello");
            iconbitmap = LoginProcess.getCroppedBitmap(BitMapstore.getBitmapFromMemCache("iconbitmap"), 200);
            BitmapDrawable bdrawable = new BitmapDrawable(this.getResources(), iconbitmap);
            BitmapDrawable bd1 = new BitmapDrawable(this.getResources(), iconbitmap);
            BitmapDrawable bd2 = new BitmapDrawable(this.getResources(), iconbitmap);
            BitmapDrawable bd3 = new BitmapDrawable(this.getResources(), iconbitmap);
            BitmapDrawable bd = bdrawable;

            controlboardbaricon.setBackground(bd);
            mainicon.setBackground(bd1);
            settingicon.setBackground(bd2);
            informationicon.setBackground(bd3);
        }
        String age1 = birthday.substring((birthday.length() - 4));
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String a = "October 15th,2017";
        lastgamecontent.setText(a);
        year = year - Integer.valueOf(age1);
        agecontent.setText(String.valueOf(year));
        playcontent.setText("0");
        canceledcontent.setText("0");
        informationtext.setText(Name);
        controlboardbartext.setText(Name);
        int count = 0;
        for (int i = Name.length() - 1; i >= 0; i--) {
            if (Name.charAt(i) == ' ') {
                count = i + 1;
                break;
            }
        }
        settingemail.setText(Email);
        settinglastname.setText(Name.substring(count).trim());
        settingfirstname.setText(Name.substring(0, count).trim());
        //==set text font


        //============assign each edittext variable with it assosiate object in xml================
        typeinlocation = findViewById(R.id.createlocation);
        typeinlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(content);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE_CREATEGAME);

                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        typeinname = (EditText) findViewById(R.id.createname);
        typeintime = findViewById(R.id.createtime);
        typeintime.setTypeface(myTypeface3);
        typeinnumpeople =  findViewById(R.id.createpeople);
        typeinnumpeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialog = Utils.bottomsheetdialog(content,R.layout.singlenumberpicker);
                NumberPicker num = dialog.findViewById(R.id.singlenumberPicker);
                num.setMaxValue(101);
                num.setMinValue(1);
                num.setWrapSelectorWheel(false);
                num.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                num.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        typeinnumpeople.setText(Integer.toString(newVal));
                    }
                });
                if(typeinnumpeople.getText().equals("")) {
                    typeinnumpeople.setText(Integer.toString(1));
                }
                else
                {
                    typeinnumpeople.setText(typeinnumpeople.getText());
                }
                dialog.show();
            }
        });
        createbutton1 = findViewById(R.id.createbutton1);
        createbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!typeinlocation.getText().equals("") && !typeinname.getText().equals("")
                        && !typeintime.getText().equals("") && !typeinnumpeople.getText().equals(""))
                {


                }
                else
                {
                    //very simple error message, fix this
                    AlertDialog alertDialog = new AlertDialog.Builder(content).create();
                    alertDialog.setTitle("Can Not creat the game");
                    alertDialog.setMessage("Please Fill In all the blank");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

            }
        });
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
        setAllFrametoInvisible();

        closeCamera();
        stopBackgroundThread();
        String hasvideo1 = settings.getString(value.HASVIDEO, "");

        if (hasvideo1.equals("yes")) {
            editor.putString(value.HASVIDEO, "");
            editor.commit();
            creategame(null);
            videoicon.setVisibility(View.VISIBLE);
            videotexture.setVisibility(View.GONE);
            hasvideo = true;
        } else {
            videoicon.setVisibility(View.GONE);
            videotexture.setVisibility(View.VISIBLE);
        }

    }

    //this function is for empty call which can be set on the empty frame to prevent frome exit
    public void empty(View v) {
    }

    public void mapsearch(View v) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    //this method is called by the outside button which wish to clean or exit the frame
    public void clean(View v) {
        setAllFrametoInvisible();
        if (v == setting)
            display(information);
    }

    //this method is call by the creategame button under the control board under the main icon
    public void creategame(View v) {
        permission.checkpermission(this, REQUEST_AUDIO,
                MapsActivity.this, "Audio", android.Manifest.permission.RECORD_AUDIO);
        permission.checkpermission(this, REQUEST_CAMERA,
                MapsActivity.this, "Camera", Manifest.permission.CAMERA);
        setAllFrametoInvisible();

        display(creategame);
        if(v!=null && !hasvideo)
        creategamevideo = true;
        if(textureView.isAvailable() && !hasvideo)
        {
            setupCamera(textureView.getWidth(), textureView.getHeight(),CameraCharacteristics.LENS_FACING_FRONT);
            connectCamera();
            startBackgroundThread();
        }
    }

    //create the timepicker dialog
    public void timepicker(View v) {
        if (v == typeintime) {
            final BottomSheetDialog d = new BottomSheetDialog(this);
            d.requestWindowFeature(Window.FEATURE_NO_TITLE);
            d.setContentView(R.layout.timepickerdialog);
            NumberPicker date1 = (NumberPicker) d.findViewById(R.id.numberPickerdate);
            NumberPicker hour1 = (NumberPicker) d.findViewById(R.id.numberPickerhour);
            NumberPicker minute1 = (NumberPicker) d.findViewById(R.id.numberPickerminute);
            NumberPicker ampm1 = (NumberPicker) d.findViewById(R.id.numberPickerampm);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            timepicker.timepicker1(this, date1, hour1, minute1, ampm1, d);
            Calendar cal = Calendar.getInstance();
            date1.setValue(0);
            hour1.setValue(cal.get(Calendar.HOUR));
            minute1.setValue(cal.get(Calendar.MINUTE));
            int AM_PM = cal.get(Calendar.AM_PM);
            if (AM_PM == 0) {
                ampm1.setValue(0);
                ampm = "AM";
            }
            else
            {
                ampm1.setValue(1);
                ampm = "PM";
            }

            typeintime.setText(timepicker.getString(0) + " " + hour1.getValue() + ":" + minute1.getValue() + ":" + "00 " + ampm);
            date1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    date = timepicker.getString(newVal);
                    typeintime.setText(date + " " + hour + ":" + minute + ":" + "00 " + ampm);
                }
            });
            hour1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    hour = newVal;
                    typeintime.setText(date + " " + hour + ":" + minute + ":" + "00 " + ampm);
                }
            });
            minute1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    minute = newVal;
                    typeintime.setText(date + " " + hour + ":" + minute + ":" + "00 " + ampm);
                }
            });
            ampm1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    if (newVal == 1)
                        ampm = "PM";
                    else
                        ampm = "AM";
                    typeintime.setText(date + " " + hour + ":" + minute + ":" + "00 " + ampm);
                }
            });


        }
    }


    //this method is call by the information button under the control board under the main icon
    public void information(View v) {
        setAllFrametoInvisible();
        display(information);
    }

    public void inforamtionsetting(View v) {
        //checkForManagePermission();
        permission.checkpermission(this, REQUEST_READ,
                MapsActivity.this, "Read external storage", android.Manifest.permission.READ_EXTERNAL_STORAGE);

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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            BitMapstore.clean();
            editor.clear();
            editor.apply();
            BitMapstore b = new BitMapstore(this);
            b.clear();


            if (LoginType.equals("Google")) {
                Auth.GoogleSignInApi.signOut(mGoogleApiClient1).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                mAuth.signOut();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            } else if (LoginType.equals("Facebook")) {
                LoginManager.getInstance().logOut();
                mAuth.signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();//delete

            } else {
                mAuth.signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();//delete
            }


        }
    }

    public void enterCamera(View v) {
        Intent intent = new Intent(this, Camera.class);
        startActivity(intent);

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
        if (bd != null) {
            informationicon.setBackground(bd);
            bd = null;
        }
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
            bm = LoginProcess.getScaledBitmap(picturePath, 800, 800);
            bd = new BitmapDrawable(getResources(), LoginProcess.getCroppedBitmap(BitmapFactory.decodeFile(picturePath), 300));
            settingicon.setBackground(bd);
        } else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
                if (mChooseLocationMarker != null) {
                    mChooseLocationMarker.remove();
                }
                mapsearchbar.setText(place.getName());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(place.getLatLng());
                Bitmap marker1 = BitmapFactory.decodeResource(getResources(), R.drawable.direction);
                int size = getResources().getInteger(R.integer.mapmarker);
                Bitmap marker = Bitmap.createScaledBitmap(marker1, size, size, false);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(marker));
                mChooseLocationMarker = mGoogleMap.addMarker(markerOptions);

                //move map camera and set the zoom in percentage
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 15));


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
        else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_CREATEGAME) {
            if (resultCode == RESULT_OK) {
                creategameplace = PlaceAutocomplete.getPlace(this, data);
                typeinlocation.setText(creategameplace.getName());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }


/*------------------------------ The code below is for google map and check permission----------------------*/

    private void getMyLocation() {

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        //markerOptions.title("Current Position");
        int size = getResources().getInteger(R.integer.mapmarker);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
                Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.direction), size, size, false)));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), 15));
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        if(creategamevideo)
        {
            closeCamera();
            stopBackgroundThread();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();


        if(creategamevideo) {
            startBackgroundThread();
            if (textureView.isAvailable()) {
                setupCamera(textureView.getWidth(), textureView.getHeight(), CameraCharacteristics.LENS_FACING_FRONT);
                connectCamera();
            } else {
                textureView.setSurfaceTextureListener(surfaceTextureListener);
            }
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
                permission.checkpermission(this, MY_PERMISSIONS_REQUEST_LOCATION,
                        MapsActivity.this, "Location", android.Manifest.permission.ACCESS_FINE_LOCATION);
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
        mLocationRequest.setInterval(100000);
        mLocationRequest.setFastestInterval(100000);
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
        //markerOptions.title("Current Position");
        Bitmap marker1 = BitmapFactory.decodeResource(getResources(), R.drawable.direction);
        int size = getResources().getInteger(R.integer.mapmarker);
        Bitmap marker = Bitmap.createScaledBitmap(marker1, size, size, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(marker));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera and set the zoom in percentage
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                location.getLongitude()), 15));

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Mutirequestcode: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0
                            && grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        // permission was granted, yay! Do the
                        // location-related task you need to do.
                        if (ContextCompat.checkSelfPermission(this,
                                permissions[i])
                                == PackageManager.PERMISSION_GRANTED) {

                            if (mGoogleApiClient == null) {
                                buildGoogleApiClient();
                            }
                            mGoogleMap.setMyLocationEnabled(true);
                        }

                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                        Toast.makeText(this, "permission denied" + permissions[i], Toast.LENGTH_LONG).show();
                    }
                }
                return;

            }
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
                    Toast.makeText(this, "permission denied location", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(this, "permission denied camera", Toast.LENGTH_LONG).show();
                }
                return;

            }
            case REQUEST_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.RECORD_AUDIO)
                            == PackageManager.PERMISSION_GRANTED) {

                        //do somthing
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied audio", Toast.LENGTH_LONG).show();
                }
                return;

            }
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

    //private static final int REQUEST_MANAGE = 4;


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

/*
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
*/
    private TextureView textureView;
    private int mTotalRotation;
    private String mCameraId;
    private Size mPreviewSize;
    private CameraDevice mCameraDevice;
    private CaptureRequest.Builder mCaptureRequestBuilder;
    private CameraCaptureSession mPreviewCaptureSession;
    private HandlerThread mBackgroundHandlerThread;
    private Handler mBackgroundHandler;

private static SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    private static class CompareSizeByArea implements Comparator<Size> {

        @Override
        public int compare(Size lhs, Size rhs) {
            return Long.signum((long) (lhs.getWidth() * lhs.getHeight()) -
                    (long) (rhs.getWidth() * rhs.getHeight()));
        }
    }

    private CameraDevice.StateCallback mCameraDeviceStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            startPreview();

        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            camera.close();
            mCameraDevice = null;
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            camera.close();
            mCameraDevice = null;
        }
    };

    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            setupCamera(width, height,CameraCharacteristics.LENS_FACING_FRONT);
            connectCamera();
            startBackgroundThread();
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

    private void setupCamera(int width, int height, int cameraface) {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            for (String cameraId : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId);
                if (cameraManager.getCameraIdList().length > 1 && cameraCharacteristics.get(CameraCharacteristics.LENS_FACING) !=
                        cameraface) {// notic front
                    continue;
                }
                StreamConfigurationMap map = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];
                mCameraId = cameraId;
                return;
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void connectCamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) {
                    cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, null);
                } else {
                    if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
                        Toast.makeText(this,
                                "Video app required access to camera", Toast.LENGTH_SHORT).show();
                    }
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
                    }, REQUEST_CAMERA);
                }

            } else {
                cameraManager.openCamera(mCameraId, mCameraDeviceStateCallback, null);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void startPreview() {
        SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
        surfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(), mPreviewSize.getHeight());
        Surface previewSurface = new Surface(surfaceTexture);

        try {
            mCaptureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mCaptureRequestBuilder.addTarget(previewSurface);

            mCameraDevice.createCaptureSession(Arrays.asList(previewSurface),
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(CameraCaptureSession session) {
                            Log.d(TAG, "onConfigured: startPreview");
                            mPreviewCaptureSession = session;
                            try {
                                mPreviewCaptureSession.setRepeatingRequest(mCaptureRequestBuilder.build(),
                                        null, mBackgroundHandler);
                            } catch (CameraAccessException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onConfigureFailed(CameraCaptureSession session) {
                            Log.d(TAG, "onConfigureFailed: startPreview");

                        }
                    }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (mCameraDevice != null) {
            mCameraDevice.close();
            mCameraDevice = null;
        }

    }



    private void startBackgroundThread() {
        mBackgroundHandlerThread = new HandlerThread("Camera2VideoImage");
        mBackgroundHandlerThread.start();
        mBackgroundHandler = new Handler(mBackgroundHandlerThread.getLooper());
    }

    private void stopBackgroundThread() {
        if( mBackgroundHandlerThread!= null) {
            mBackgroundHandlerThread.quitSafely();
            try {
                mBackgroundHandlerThread.join();
                mBackgroundHandlerThread = null;
                mBackgroundHandler = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}