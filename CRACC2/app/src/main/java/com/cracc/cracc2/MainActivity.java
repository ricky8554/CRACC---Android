package com.cracc.cracc2;

import android.accounts.Account;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.IOUtils;
import com.google.api.services.people.v1.People;
import com.google.api.services.people.v1.PeopleScopes;
import com.google.api.services.people.v1.model.Date;
import com.google.api.services.people.v1.model.Gender;
import com.google.api.services.people.v1.model.Person;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;


import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.facebook.share.internal.ShareConstants.IMAGE_URL;

public class MainActivity extends AppCompatActivity {

    //private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    /**
     * Global instance of the JSON factory.
     */
    //private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_READ = 3;
    private static final int RC_SIGN_IN = 9001;
    private static final int REQUEST_EXTERNAL_STORAGE = 2;

    private static final String TAG = MainActivity.class.getSimpleName();

    //test

    //BitMapstore bitmapcache = new BitMapstore(context);

    //getimage
    //File f = bitmapcache.getFile(uid);
    //Bitmap b = decode(f);



    private Button login;
    private Button forgotlogin;
    private Button sendcode;
    private EditText forgetmail;
    private FrameLayout forgotpasswordframe;
    private FrameLayout createaccountframe1;
    private FrameLayout createaccountframe2;
    private FrameLayout createaccountframe3;
    protected Button profileimage;
    private Button createaccountnext;
    private Button createaccountlogin;
    private Button createaccount2male;
    private Button createaccount2female;
    private EditText createaccountlastname;
    private EditText createaccountfirstname;
    private EditText createaccount2birthday;
    private EditText createaccount3email;
    private EditText createaccount3password;
    private EditText loginpass;
    private EditText createaccount3confirmpassword;
    private EditText loginemail;
    private Button createaccount2next;
    private Button createaccount2back;
    private Button createaccount3signup;
    private Button createaccount3back;
    private Button createaccountgoooglesignup;
    private Button createaccountgoooglemale;
    private Button createaccountgoooglefemale;
    private EditText createaccountgoooglebirthday;
    private FrameLayout createaccountgooogleframe;


    private GoogleApiClient mGoogleApiClient;
    private Button google;


    private DatabaseReference firedatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    private Uri iconuri = null;
    private DatabaseReference cracc = firedatabase.child("CRACC");
    private StorageReference filepath;
    private String uid;
    private Uri downloadUrl = null;
    private int firsttimelogin = 0;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    //facebook delete
    private LoginButton facebookbutton;
    private Button login_facebook;
    private CallbackManager mCallbackManager;


    //for cache use delete
    public static final String MyPREFERENCES = "CRACC.com.profile";
    public static final String EMAIL = "emailKey";
    public static final String GENDER = "genderKey";
    public static final String NAME = "namekey";
    public static final String BIRTHDAY = "birthdaykey";
    public static final String STARS = "starskey";
    public static final String PHOTOAVATAR = "Photoavatar";
    public static final String USERID = "UserId";
    public static final String LOGINTYPE = "LoginType";
    //delete
    private String birthday;
    private String Name;
    private int Stars;
    private String Email;
    private String Photoavatar = "";
    private String Userid;
    private String gender = "";
    private ProgressBar progressBar;
    private ObjectAnimator animation;
    private PercentRelativeLayout mainpage;

    //delete
    private Bitmap bm;                      //bit map

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialize();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        // connection failed, should be handled
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }
    @Override
    public void onWindowFocusChanged(boolean hasFocas)
    {
        super.onWindowFocusChanged(hasFocas);
        View decorView = getWindow().getDecorView();
        if(hasFocas)
        {
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
                    View.SYSTEM_UI_FLAG_FULLSCREEN|
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null) {
            Bitmap a = BitMapstore.getBitmapFromMemCache("iconbitmap");
            if(a == null)
            {
                BitMapstore bitmapcache = new BitMapstore(context);
                File f = bitmapcache.getFile("iconbitmap");
                Bitmap bmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                if(bmap != null) {
                    BitMapstore.addBitmapToMemoryCache("iconbitmap", bmap);
                    mainpage("login");

                }
                else
                {
                    Email = currentUser.getEmail();
                    uid = currentUser.getUid();
                    String string1 = "";
                    for (int i = 0; i < Email.length(); i++) {
                        if (Email.substring(i, i + 1).equals(".")) {
                            string1 += ",";
                        } else {
                            string1 += Email.substring(i, i + 1);
                        }
                    }
                    final String string2 = string1;;

                    cracc.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            if(snapshot.child("Google").child(uid).exists()){
                                String uri = snapshot.child("User Info")
                                        .child("Google").child(uid)
                                        .child("avatarUrl").getValue(String.class);
                                new DownloadImage().execute(uri);
                                LoginProcess.settingsignin("Google", uid, snapshot,
                                        sharedpreferences.edit());

                                mainpage("login");

                            } else if(snapshot.child("Facebook").child(uid).exists()){
                                String uri = snapshot.child("User Info")
                                        .child("Facebook").child(uid)
                                        .child("avatarUrl").getValue(String.class);
                                new DownloadImage().execute(uri);
                                LoginProcess.settingsignin("Facebook", uid, snapshot,
                                        sharedpreferences.edit());

                                mainpage("login");

                            }
                            else if (snapshot.child("Email").child(string2).exists()) {

                                String uri = snapshot.child("User Info")
                                        .child("Email").child(uid)
                                        .child("avatarUrl").getValue(String.class);
                                new DownloadImage().execute(uri);
                                LoginProcess.settingsignin("Email", uid, snapshot,
                                        sharedpreferences.edit());

                                mainpage("login");


                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //throw databaseError.toException();
                        }
                    });

                }

            }
            else
            {
                mainpage("login");
            }


        }
        //mainpage("string");
        //updateUI(currentUser);
    }


    private void initialize() {
        permission.checkpermission(this,REQUEST_EXTERNAL_STORAGE,
                MainActivity.this, "Write external storage" , Manifest.permission.WRITE_EXTERNAL_STORAGE  );
        Typeface myTypeface1 = Typeface.createFromAsset(getAssets(), "Myriad-Pro-Bold.ttf");
        Typeface myTypeface2 = Typeface.createFromAsset(getAssets(), "Myriad-Pro-Italic.ttf");
        TextView text = (TextView) findViewById(R.id.frontword);
        login_facebook = (Button) findViewById(R.id.login_facebook);
        google = (Button) findViewById(R.id.login_google);
        login = (Button) findViewById(R.id.login);
        loginemail = (EditText) findViewById(R.id.loginemail);
        loginpass = (EditText) findViewById(R.id.loginpass);
        Button createnewaccount = (Button) findViewById(R.id.creatnewaccount);
        Button forgot = (Button) findViewById(R.id.forgot);
        forgotlogin = findViewById(R.id.forgotlogin);
        sendcode = findViewById(R.id.sendcode);
        forgetmail = findViewById(R.id.forgetmail);
        forgotpasswordframe = findViewById(R.id.forgotpasswordframe);
        createaccountlogin = findViewById(R.id.createaccountlogin);
        createaccountnext = findViewById(R.id.createaccountnext);
        createaccountlastname = findViewById(R.id.createaccountlastname);
        createaccountfirstname = findViewById(R.id.createaccountfirstname);
        profileimage = findViewById(R.id.profileimage);
        createaccountframe1 = findViewById(R.id.createaccountframe1);
        createaccountframe2 = findViewById(R.id.createaccountframe2);
        createaccount2female = findViewById(R.id.createaccount2female);
        createaccount2male = findViewById(R.id.createaccount2male);

        createaccount2back = findViewById(R.id.createaccount2back);
        createaccount2birthday = findViewById(R.id.createaccount2birthday);
        createaccount2next = findViewById(R.id.createaccount2next);

        createaccountframe3 = findViewById(R.id.createaccountframe3);
        createaccount3email = findViewById(R.id.createaccount3email);
        createaccount3password = findViewById(R.id.createaccount3password);
        createaccount3confirmpassword = findViewById(R.id.createaccount3confirmpassword);

        createaccount3back = findViewById(R.id.createaccount3back);
        createaccount3signup = findViewById(R.id.createaccount3signup);

        createaccountgoooglesignup = findViewById(R.id.createaccountgoooglesignup);
        createaccountgoooglemale = findViewById(R.id.createaccountgoooglemale);
        createaccountgoooglefemale = findViewById(R.id.createaccountgoooglefemale);
        createaccountgoooglebirthday = findViewById(R.id.createaccountgoooglebirthday);
        createaccountgooogleframe = findViewById(R.id.createaccountgooogleframe);

        mainpage = findViewById(R.id.mainpage);

        mAuth = FirebaseAuth.getInstance(); // delete
        //set text font
        text.setTypeface(myTypeface1);
        login_facebook.setTypeface(myTypeface1);
        google.setTypeface(myTypeface1);
        loginemail.setTypeface(myTypeface2);
        loginpass.setTypeface(myTypeface2);
        createnewaccount.setTypeface(myTypeface2);
        forgot.setTypeface(myTypeface2);
        forgotlogin.setTypeface(myTypeface2);
        forgetmail.setTypeface(myTypeface2);
        createaccountlogin.setTypeface(myTypeface2);
        createaccountfirstname.setTypeface(myTypeface2);
        createaccountlastname.setTypeface(myTypeface2);

        createaccount2birthday.setTypeface(myTypeface2);
        createaccount2back.setTypeface(myTypeface2);
        createaccount3back.setTypeface(myTypeface2);


        createaccountgoooglebirthday.setTypeface(myTypeface2);



        //facebook
        //delete
        facebookbutton = findViewById(R.id.btn_facebook_signup);
        mCallbackManager = CallbackManager.Factory.create();
        facebookbutton.setReadPermissions("email", "public_profile");
        facebookbutton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                firebaseAuth(null, loginResult.getAccessToken());
                animation.start ();
                mainpage.getBackground().setColorFilter(Color.parseColor("#CC000000"), PorterDuff.Mode.SRC_ATOP);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE); //delete

        //progress bar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        animation = ObjectAnimator.ofInt (progressBar, "progress", 0, 500); // see this max value coming back here, we animale towards that value
        animation.setDuration (5000); //in milliseconds
        animation.setInterpolator (new DecelerateInterpolator());


        setAllFrametoInvisible();
    }


    private void setAllFrametoInvisible() {
        forgotpasswordframe.setVisibility(View.GONE);
        createaccountframe1.setVisibility(View.GONE);
        createaccountframe2.setVisibility(View.GONE);
        createaccountframe3.setVisibility(View.GONE);
        createaccountgooogleframe.setVisibility(View.GONE);
        facebookbutton.setVisibility(View.GONE);
    }

    public void facebookclick(View v) {
        facebookbutton.performClick();
    }

    public void googlesignin(View v) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    protected void googlesigningfinal() {
        createaccountgooogleframe.setVisibility(View.VISIBLE);

    }

    public void createaccount3back(View v) {
        createaccountframe3.setVisibility(View.GONE);
        createaccountframe2.setVisibility(View.VISIBLE);
    }

    public void createaccount3(View v) {
        birthday = createaccount2birthday.getText().toString();
        createaccountframe3.setVisibility(View.VISIBLE);
    }

    public void gender(View v) {
        if (v == createaccount2male) {
            createaccount2male.setBackgroundResource(R.drawable.genderbutton2);
            createaccount2male.setTextColor(Color.parseColor("#FFFFFF"));
            createaccount2female.setBackgroundResource(R.drawable.genderbutton1);
            createaccount2female.setTextColor(Color.parseColor("#6e6e6e"));
            gender = "male";
        } else if (v == createaccount2female) {
            createaccount2female.setBackgroundResource(R.drawable.genderbutton2);
            createaccount2female.setTextColor(Color.parseColor("#FFFFFF"));
            createaccount2male.setBackgroundResource(R.drawable.genderbutton1);
            createaccount2male.setTextColor(Color.parseColor("#6e6e6e"));
            gender = "female";
        } else if (v == createaccountgoooglemale) {
            createaccountgoooglemale.setBackgroundResource(R.drawable.genderbutton2);
            createaccountgoooglemale.setTextColor(Color.parseColor("#FFFFFF"));
            createaccountgoooglefemale.setBackgroundResource(R.drawable.genderbutton1);
            createaccountgoooglefemale.setTextColor(Color.parseColor("#6e6e6e"));
            gender = "male";
        } else if (v == createaccountgoooglefemale) {
            createaccountgoooglefemale.setBackgroundResource(R.drawable.genderbutton2);
            createaccountgoooglefemale.setTextColor(Color.parseColor("#FFFFFF"));
            createaccountgoooglemale.setBackgroundResource(R.drawable.genderbutton1);
            createaccountgoooglemale.setTextColor(Color.parseColor("#6e6e6e"));
            gender = "female";
        }


    }

    public void createaccount2back(View v) {
        createaccountframe2.setVisibility(View.GONE);
        createaccountframe1.setVisibility(View.VISIBLE);
    }

    public void createaccount2(View v) {
        Name = createaccountfirstname.getText().toString() + createaccountlastname.getText().toString();


        createaccountframe2.setVisibility(View.VISIBLE);
    }

    public void createaccount1(View v) {
        createaccountframe1.setVisibility(View.VISIBLE);
    }

    public void getPicture(View v) {
        permission.checkpermission(this,REQUEST_READ ,
                MainActivity.this, "READ external storage" , Manifest.permission.READ_EXTERNAL_STORAGE  );
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED /*|| ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.MANAGE_DOCUMENTS) != PackageManager.PERMISSION_GRANTED*/) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    public void forgetpassword(View v) {
        forgotpasswordframe.setVisibility(View.VISIBLE);
    }

    public void loginpage(View v) {
        setAllFrametoInvisible();
    }

    public void mainpage(View v) {


        if (v == createaccountgoooglesignup) {

            cracc.child("User Info").child("Google").child(uid).child("Gender").setValue(gender);
            cracc.child("User Info").child("Google").child(uid).child("Birthday").setValue(createaccountgoooglebirthday.getText().toString());
            birthday = createaccountgoooglebirthday.getText().toString();

            editor = sharedpreferences.edit();
            editor.putString(GENDER, gender);
            editor.putString(BIRTHDAY, birthday);
            editor.commit();

            mainpage("login");

        } else if (v == createaccount3signup) {
            animation.start ();
            mainpage.getBackground().setColorFilter(Color.parseColor("#CC000000"), PorterDuff.Mode.SRC_ATOP);
            Email = createaccount3email.getText().toString();
            mAuth.createUserWithEmailAndPassword(Email, createaccount3password.getText().toString())
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                uid = user.getUid();
                                String result = UUID.nameUUIDFromBytes(uid.getBytes()).toString();
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference().child("Avatar");
                                filepath = storageRef.child(result.toUpperCase());

                                new DownloadImage().execute("emails");
                                LoginProcess.settingSignup("Email", uid, cracc, Email, Name, gender, birthday);

                                mainpage("email");

                            } else {

                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                updateUI(null,null,null);
                            }
                        }
                    });


        } else if (v == login) {
            if (loginemail.getText().toString().isEmpty() || loginpass.getText().toString().isEmpty()) {

            } else {
                animation.start ();
                mainpage.getBackground().setColorFilter(Color.parseColor("#CC000000"), PorterDuff.Mode.SRC_ATOP);
                mAuth.signInWithEmailAndPassword(loginemail.getText().toString(), loginpass.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Email = user.getEmail();
                                    uid = user.getUid();
                                    String string1 = "";
                                    for (int i = 0; i < Email.length(); i++) {
                                        if (Email.substring(i, i + 1).equals(".")) {
                                            string1 += ",";
                                        } else {
                                            string1 += Email.substring(i, i + 1);
                                        }
                                    }
                                    final String string2 = string1;;
                                    cracc.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            if (snapshot.child("Email").child(string2).exists()) {

                                                String uri = snapshot.child("User Info")
                                                        .child("Email").child(uid)
                                                        .child("avatarUrl").getValue(String.class);
                                                new DownloadImage().execute(uri);
                                                LoginProcess.settingsignin("Email", uid,  snapshot,
                                                        sharedpreferences.edit());

                                                mainpage("email");


                                            } else {

                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            //throw databaseError.toException();
                                        }
                                    });

                                } else {
                                    // If sign in fails, display a message to the user.
                                    updateUI(null,null,null);
                                }

                                // ...
                            }
                        });


            }
        }

    }

    protected void mainpage(String s) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void empty1(View v) {

    }

    //-----------------------image---------------------------------------------
    //delete all


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
            bm = LoginProcess.getScaledBitmap(picturePath, 100, 100);
            BitMapstore.addBitmapToMemoryCache("iconbitmap", bm);
            profileimage.setBackground(new BitmapDrawable(getResources(),
                    LoginProcess.getCroppedBitmap(BitmapFactory.decodeFile(picturePath), 400)));
            profileimage.setText("");

        } else if (requestCode == RC_SIGN_IN) { //delete
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                animation.start ();
                mainpage.getBackground().setColorFilter(Color.parseColor("#CC000000"), PorterDuff.Mode.SRC_ATOP);
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuth(account, null);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        } else {
            //facebook

            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void firebaseAuth(final GoogleSignInAccount acct, final AccessToken token) {

        AuthCredential credential = null;
        if(acct!=null) { //this is google
            credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        }
        else if ( token!=null ) {//this is facebook
            credential = FacebookAuthProvider.getCredential(token.getToken());
        }
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            if(acct != null)
                            updateUI(user, "Google" ,acct);
                            else if(token!=null) {
                                getinformation(token);
                                updateUI(user, "Facebook", null);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null,null,null);
                        }

                    }
                });
    }


    private void getinformation(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {

                            Email= response.getJSONObject().getString("email");
                            Name = response.getJSONObject().getString("first_name") + " "
                                    + response.getJSONObject().getString("last_name");
                            gender = response.getJSONObject().getString("gender");

                            Object ageob = response.getJSONObject().get("age_range");
                            String agerange = ageob.toString();
                            int index = agerange.indexOf("max");
                            int index1 = agerange.indexOf("min");
                            String age = "";

                            if (index != -1) {
                                for (int i = index + 5; i < agerange.length(); i++) {
                                    if (Character.isDigit(agerange.charAt(i))) {
                                        age = age + agerange.substring(i, i + 1);
                                    } else
                                        break;
                                }
                            } else if (index1 != -1) {
                                for (int i = index1 + 5; i < agerange.length(); i++) {
                                    if (Character.isDigit(agerange.charAt(i))) {
                                        System.out.println(agerange.charAt(i));
                                        age = age + agerange.substring(i, i + 1);
                                    } else
                                        break;
                                }
                            }
                            int year = Calendar.getInstance().get(Calendar.YEAR);
                            birthday = "" + (year - Integer.valueOf(age));

                            if (Profile.getCurrentProfile() != null) {
                                iconuri = Profile.getCurrentProfile().getProfilePictureUri(200, 200);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender,age_range");
        request.setParameters(parameters);
        request.executeAsync();

        Log.d(TAG, "handleFacebookAccessToken:" + token);
    }


    //for facebook
    private void updateUI(FirebaseUser user, final String logintype, final GoogleSignInAccount acct) {



        if (logintype == null) {

        } else {
            if(logintype.equals("Google"))
            {
                Name = acct.getDisplayName();
                Email = acct.getEmail();
                iconuri = acct.getPhotoUrl();
            }
            uid = user.getUid();

            cracc.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.child(logintype).child(uid).exists()) {
                        String uri = snapshot.child("User Info").child(logintype)
                                .child(uid).child("avatarUrl")
                                .getValue(String.class);
                        new DownloadImage().execute(uri);
                        LoginProcess.settingsignin(logintype, uid, snapshot,
                                sharedpreferences.edit());

                        mainpage("login");

                    } else {
                        firsttimelogin = 1;

                        String result = UUID.nameUUIDFromBytes(uid.getBytes()).toString();
                        filepath = FirebaseStorage.getInstance().getReference().child("Avatar").child(result.toUpperCase());
                        new DownloadImage().execute(iconuri.toString());

                        //setup the value in firebase
                        if( logintype.equals("Google") ) {
                            LoginProcess.settingSignup(logintype, uid, cracc, Email, Name, "", "");
                        }
                        else if( logintype.equals("FaceBook"))
                        LoginProcess.settingSignup(logintype, uid, cracc, Email, Name, gender, birthday);
                        mainpage("login");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    throw databaseError.toException();
                }
            });
        }


    }

    private Context context = this;
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog

        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            Bitmap bitmap = null;
            String imageURL = URL[0];
            if(imageURL.equals("emails"))
            {
                bitmap = bm;
                firsttimelogin =1;
            }
            else {
                try {
                    // Download Image from URL

                    InputStream input = new java.net.URL(imageURL).openStream();
                    bitmap = BitmapFactory.decodeStream(input);
                    BitMapstore.addBitmapToMemoryCache("iconbitmap", bitmap);
                    //save the

                    BitMapstore bitmapcache = new BitMapstore(context);
                    File f = bitmapcache.getFile("iconbitmap");
                    OutputStream os = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                    os.close();
                    /*
                    Utils.copyStream(input, os);
                    */


                    // Decode Bitmap

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //move above part to main
            File file = Downloadimage.createImageFile(getApplicationContext(), uid);
            iconuri = Downloadimage.downloaduriimage(bitmap,firsttimelogin ,file);
            Downloadimage.uploadtoFirebase(cracc, filepath, uid, iconuri, firsttimelogin);



            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            progressBar.clearAnimation();
        }
    }


    //delete next 3 function3



}
