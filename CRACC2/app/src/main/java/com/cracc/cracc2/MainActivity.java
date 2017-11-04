package com.cracc.cracc2;

import android.accounts.Account;
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
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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


import static com.facebook.share.internal.ShareConstants.IMAGE_URL;

public class MainActivity extends AppCompatActivity {

    //private static HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();
    /**
     * Global instance of the JSON factory.
     */
    //private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private Button login;
    private Button forgotlogin;
    private Button sendcode;
    private EditText forgetmail;
    private FrameLayout forgotpasswordframe;
    private FrameLayout createaccountframe1;
    private FrameLayout createaccountframe2;
    private FrameLayout createaccountframe3;
    private Button profileimage;
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
    private static final int RC_SIGN_IN = 9001;
    private DatabaseReference firedatabase;
    private FirebaseAuth mAuth;
    private Bitmap bitmap1 = null;
    private Uri iconuri = null;
    private DatabaseReference cracc;
    private StorageReference filepath;
    private String uid;
    private Uri downloadUrl = null;
    private int firsttimelogin = 0;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    //facebook
    private LoginButton facebookbutton;
    private Button login_facebook;
    private CallbackManager mCallbackManager;


    //for cache use
    public static final String MyPREFERENCES = "CRACC.com.profile";
    public static final String EMAIL = "emailKey";
    public static final String GENDER = "genderKey";
    public static final String FIRST_NAME = "firstnamekey";
    public static final String LAST_NAME = "lastnamekey";
    public static final String BIRTHDAY = "birthdaykey";
    public static final String STARS = "starskey";
    public static final String PHOTOAVATAR = "Photoavatar";
    public static final String USERID= "UserId";
    public static final String LOGINTYPE = "LoginType";
    private String birthday;
    private String LastName;
    private String FirstName;
    private int Stars;
    private String Email;
    private String Photoavatar = "";
    private String Userid;
    private String gender = "";

    private BitmapDrawable bd = null;             //informationsetting icon bitmapdrable
    private Bitmap bm;                      //bit map

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

    private void initialize() {
        Typeface myTypeface1 = Typeface.createFromAsset(getAssets(), "Myriad-Pro-Bold.ttf");
        Typeface myTypeface2 = Typeface.createFromAsset(getAssets(), "Myriad-Pro-Italic.ttf");
        Typeface myTypeface3 = Typeface.createFromAsset(getAssets(), "Myriad-Pro.ttf");
        TextView text = (TextView) findViewById(R.id.frontword);
        TextView forgetmessage = (TextView) findViewById(R.id.forgetmessage);
        TextView createaccountmessage = (TextView) findViewById(R.id.createaccountmessage);
        TextView createaccountmessage2 = (TextView) findViewById(R.id.createaccountmessage2);
        TextView createaccountmessage3 = (TextView) findViewById(R.id.createaccountmessage3);
        login_facebook = (Button) findViewById(R.id.btn_facebook_signup);
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

        firedatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        //set text font
        text.setTypeface(myTypeface1);
        login_facebook.setTypeface(myTypeface1);
        google.setTypeface(myTypeface1);
        login.setTypeface(myTypeface3);
        loginemail.setTypeface(myTypeface2);
        loginpass.setTypeface(myTypeface2);
        createnewaccount.setTypeface(myTypeface2);
        forgot.setTypeface(myTypeface2);
        forgotlogin.setTypeface(myTypeface2);
        sendcode.setTypeface(myTypeface3);
        forgetmessage.setTypeface(myTypeface3);
        createaccountmessage2.setTypeface(myTypeface3);
        createaccountmessage3.setTypeface(myTypeface3);
        forgetmail.setTypeface(myTypeface2);
        createaccountlogin.setTypeface(myTypeface2);
        createaccountnext.setTypeface(myTypeface3);
        createaccountfirstname.setTypeface(myTypeface2);
        createaccountmessage.setTypeface(myTypeface3);
        createaccountlastname.setTypeface(myTypeface2);
        profileimage.setTypeface(myTypeface3);
        createaccount2male.setTypeface(myTypeface3);
        createaccount2female.setTypeface(myTypeface3);

        createaccount2next.setTypeface(myTypeface3);
        createaccount2birthday.setTypeface(myTypeface2);
        createaccount2back.setTypeface(myTypeface2);

        createaccount3confirmpassword.setTypeface(myTypeface3);
        createaccount3password.setTypeface(myTypeface3);
        createaccount3email.setTypeface(myTypeface3);
        createaccount3back.setTypeface(myTypeface2);
        createaccount3signup.setTypeface(myTypeface3);

        createaccountgoooglesignup.setTypeface(myTypeface3);
        createaccountgoooglemale.setTypeface(myTypeface3);
        createaccountgoooglefemale.setTypeface(myTypeface3);
        createaccountgoooglebirthday.setTypeface(myTypeface2);

        //facebook

        facebookbutton = findViewById(R.id.btn_facebook_signup);
        mCallbackManager = CallbackManager.Factory.create();
        facebookbutton.setReadPermissions("email", "public_profile");
        facebookbutton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
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

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



        setAllFrametoInvisible();
    }


    private void setAllFrametoInvisible() {
        forgotpasswordframe.setVisibility(View.GONE);
        createaccountframe1.setVisibility(View.GONE);
        createaccountframe2.setVisibility(View.GONE);
        createaccountframe3.setVisibility(View.GONE);
        createaccountgooogleframe.setVisibility(View.GONE);
        login_facebook.setVisibility(View.GONE);
    }

    public void facebookclick(View v) {
        facebookbutton.performClick();
    }

    public void googlesignin(View v) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    private void googlesigningfinal() {
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
        FirstName = createaccountfirstname.getText().toString() + createaccountlastname.getText().toString();


        createaccountframe2.setVisibility(View.VISIBLE);
    }

    public void createaccount1(View v) {
        createaccountframe1.setVisibility(View.VISIBLE);
    }

    public void getPicture(View v) {
        checkForREADPermission();
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
            String a = "";
            if (downloadUrl != null) {
                a = downloadUrl.toString();
            }
            cracc.child("User Info").child("Google").child(uid).child("avatarUrl").setValue(a);
            cracc.child("User Info").child("Google").child(uid).child("Gender").setValue(gender);
            cracc.child("User Info").child("Google").child(uid).child("Birthday").setValue(createaccountgoooglebirthday.getText().toString());

            Userid = uid;


            birthday = createaccountgoooglebirthday.getText().toString();

            editor = sharedpreferences.edit();
            editor.putString(EMAIL, Email);
            editor.putString(GENDER, gender);
            editor.putString(FIRST_NAME, FirstName);
            editor.putString(LAST_NAME, LastName);
            editor.putString(BIRTHDAY, birthday);
            editor.putString(USERID, Userid);
            editor.putString(PHOTOAVATAR, Photoavatar);
            editor.putInt(STARS, Stars);
            editor.putString(LOGINTYPE, "Google");
            editor.apply();

            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        } else if( v==createaccount3signup){
            cracc = firedatabase.child("CRACC");
            Email = createaccount3email.getText().toString();
            String password = createaccount3password.getText().toString();
            mAuth.createUserWithEmailAndPassword(Email, password)
                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                uid = user.getUid();
                                Userid = user.getUid();
                                String result = UUID.nameUUIDFromBytes(uid.getBytes()).toString();
                                FirebaseStorage storage = FirebaseStorage.getInstance();
                                StorageReference storageRef = storage.getReference().child("Avatar");
                                filepath = storageRef.child(result.toUpperCase());

                                new DownloadImage1().execute("");


                                String string1 = "";
                                for (int i = 0; i < Email.length(); i++)
                                {
                                    if(Email.substring(i ,i+1).equals(".") )
                                    {
                                        string1 += ",";
                                    }
                                    else
                                    {
                                        string1 += Email.substring(i ,i+1);
                                    }
                                }


                                cracc.child("Email").child(string1).child("Timestamp").setValue(ServerValue.TIMESTAMP);
                                //google.child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);
                                //cracc.child("User").child("Google").setValue(user);
                                cracc.child("User").child("Email").child(uid).child("Chat List").child("default").setValue("defaults");
                                cracc.child("User").child("Email").child(uid).child("Community List").child("default").setValue("defaults");
                                cracc.child("User").child("Email").child(uid).child("Game Created").child("default").setValue("defaults");
                                cracc.child("User").child("Email").child(uid).child("Game Joined").child("default").setValue("defaults");
                                cracc.child("User").child("Email").child(uid).child("Interested List").child("default").setValue("defaults");

                                cracc.child("User Info").child("Email").child(uid).child("Birthday").setValue(birthday);
                                cracc.child("User Info").child("Email").child(uid).child("Email").setValue(Email);
                                cracc.child("User Info").child("Email").child(uid).child("Gender").setValue(gender);
                                cracc.child("User Info").child("Email").child(uid).child("Name").setValue(FirstName);
                                cracc.child("User Info").child("Email").child(uid).child("Stars").setValue(0);
                                cracc.child("User Info").child("Email").child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);
                                cracc.child("User Info").child("Email").child(uid).child("Type").setValue("email");
                                editor = sharedpreferences.edit();
                                editor.putString(EMAIL, Email);
                                editor.putString(GENDER, gender);
                                editor.putString(FIRST_NAME, FirstName);
                                editor.putString(BIRTHDAY, birthday);
                                editor.putInt(STARS, Stars);
                                editor.putString(USERID, uid);
                                editor.putString(LOGINTYPE, "Facebook");
                                editor.commit();
                                mainpage( "email" );


                            } else {

                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                updateUI(null);
                            }

                            // ...
                        }
                    });



        }
        else if(v == login) {
            if (loginemail.getText().toString().isEmpty() || loginpass.getText().toString().isEmpty()) {

            } else {
                mAuth.signInWithEmailAndPassword(loginemail.getText().toString(), loginpass.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    uid = user.getUid();
                                    Email = user.getEmail();
                                    String string1 = "";
                                    for (int i = 0; i < Email.length(); i++) {
                                        if (Email.substring(i, i + 1).equals(".")) {
                                            string1 += ",";
                                        } else {
                                            string1 += Email.substring(i, i + 1);
                                        }
                                    }
                                    final String string2 = string1;
                                    cracc = firedatabase.child("CRACC");
                                    cracc.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot snapshot) {
                                            if (snapshot.child("Email").child(string2).exists()) {
                                                String uri = snapshot.child("User Info").child("Email").child(uid).child("avatarUrl").getValue(String.class);
                                                new DownloadImage().execute(uri);
                                                birthday = snapshot.child("User Info").child("Email").child(uid).child("Birthday").getValue(String.class);
                                                FirstName = snapshot.child("User Info").child("Email").child(uid).child("Name").getValue(String.class);
                                                Stars = snapshot.child("User Info").child("Email").child(uid).child("Stars").getValue(Integer.class);
                                                Email = snapshot.child("User Info").child("Email").child(uid).child("Email").getValue(String.class);
                                                gender = snapshot.child("User Info").child("Email").child(uid).child("Gender").getValue(String.class);

                                                editor = sharedpreferences.edit();
                                                editor.putString(EMAIL, Email);
                                                editor.putString(GENDER, gender);
                                                editor.putString(FIRST_NAME, FirstName);
                                                editor.putString(BIRTHDAY, birthday);
                                                editor.putInt(STARS, Stars);
                                                editor.putString(USERID, uid);
                                                editor.putString(LOGINTYPE, "Email");
                                                editor.commit();
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
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    updateUI(null);
                                }

                                // ...
                            }
                        });


            }
        }

    }

    private void mainpage( String s ) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void empty1(View v) {

    }

    //-----------------------image---------------------------------------------
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_READ = 3;
    private static final String TAG = MainActivity.class.getSimpleName();

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
            bm = getScaledBitmap(picturePath, 100, 100);
            BitMapstore bit = new BitMapstore();
            bit.addBitmapToMemoryCache("iconbitmap", bm);
            bd = new BitmapDrawable(getResources(), getCroppedBitmap(BitmapFactory.decodeFile(picturePath), 400));
            profileimage.setBackground(bd);
            profileimage.setText("");


            //getScaledBitmap(String picturePath, int width, int height)
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);


        } else if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        } else {
            //facebook
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
/*
//handle the succes sign in data
private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            final String uid = acct.getIdToken();
            if(uid == null)
            {
                return;
            }
            else
            {
                DatabaseReference cracc = firedatabase.child("CRACC");
                DatabaseReference google = cracc.child("Google");
                google.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.child(uid).exists()) {
                            Log.i("dadddddddd","dddddddddddddddddddddddddddddddddddddd");
                        }else{
                            Log.i("dadddddddd","dddddddddddddddddddddddddddddddddddddd");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //throw databaseError.toException();
                        Log.i("dadddddddd","ddddddddddddddddddddddddddddddddddddd``````d");
                    }
                });
            }
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //firebaseAuthWithGoogle(acct);
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    */

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        final GoogleSignInAccount acct1 = acct;
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                updateUI(user, acct1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //updateUI(null);
                        }
                        int a = 20;
                        // ...
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        final AccessToken tok = token;
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            uid = user.getUid();

                            getinformation(tok);
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            /*Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();*/
                            updateUI(null);
                        }

                        // ...
                    }
                });



    }

    private void getinformation(AccessToken token)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {

                            Log.i("Response", response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String Gender = response.getJSONObject().getString("gender");
                            //String age_min = object.getJSONObject("age_range").getString("min");

                            Object a = response.getJSONObject().get("age_range");

                            String c = a.toString();
                            int index = c.indexOf("max");
                            int index1 = c.indexOf("min");
                            String age = "";

                            if (index != -1) {
                                for (int i = index + 5; i < c.length(); i++) {
                                    if (Character.isDigit(c.charAt(i))) {
                                        age = age + c.substring(i, i + 1);
                                    } else
                                        break;
                                }
                            } else if (index1 != -1) {
                                for (int i = index1 + 5; i < c.length(); i++) {
                                    if (Character.isDigit(c.charAt(i))) {
                                        System.out.println(c.charAt(i));
                                        age = age + c.substring(i, i + 1);
                                    } else
                                        break;
                                }
                            }


                            int year = Calendar.getInstance().get(Calendar.YEAR);

                            birthday = "" + (year - Integer.valueOf(age));

                            LastName = lastName;
                            FirstName = firstName;
                            Stars = 0;
                            Email = email;
                            gender = Gender;


                            Profile profile = Profile.getCurrentProfile();


                            if (Profile.getCurrentProfile() != null) {
                                iconuri = Profile.getCurrentProfile().getProfilePictureUri(200, 200);
                            }

                            Log.i("Login" + "Email", email);
                            Log.i("Login" + "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            Log.i("Login" + "Gender", gender);


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
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //updateUI(currentUser);
    }
    //for facebook
    private void updateUI(FirebaseUser user)
    {

        if(user == null )
        {

        }
        else
        {
            cracc = firedatabase.child("CRACC");
            final DatabaseReference facebook = cracc.child("Facebook");
            cracc.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.child("Facebook").child(uid).exists()) {
                        birthday = snapshot.child("User Info").child("Facebook").child(uid).child("Birthday").getValue(String.class);
                        //LastName = snapshot.child("User Info").child("Google").child(uid).child("Birthday").getValue(String.class);
                        FirstName = snapshot.child("User Info").child("Facebook").child(uid).child("Name").getValue(String.class);
                        Stars = snapshot.child("User Info").child("Facebook").child(uid).child("Stars").getValue(Integer.class);
                        Email = snapshot.child("User Info").child("Facebook").child(uid).child("Email").getValue(String.class);
                        gender = snapshot.child("User Info").child("Facebook").child(uid).child("Gender").getValue(String.class);
                        String uri = snapshot.child("User Info").child("Facebook").child(uid).child("avatarUrl").getValue(String.class);
                        editor = sharedpreferences.edit();
                        editor.putString(EMAIL, Email);
                        editor.putString(GENDER, gender);
                        editor.putString(FIRST_NAME, FirstName);
                        editor.putString(BIRTHDAY, birthday);
                        editor.putInt(STARS, Stars);
                        editor.putString(USERID, uid);
                        editor.putString(LOGINTYPE, "Facebook");
                        editor.commit();
                        new DownloadImage().execute(uri);
                        mainpage("facebooklogin");


                    }else{
                        firsttimelogin = 1;
                        // Create a storage reference from our app
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference().child("Avatar");
                        //StorageReference riversRef = storageRef.child("images/"+personPhoto.getLastPathSegment());
                        String result = UUID.nameUUIDFromBytes(uid.getBytes()).toString();
                        filepath = storageRef.child(result.toUpperCase());

                        new DownloadImage().execute(iconuri.toString());



                        cracc.child("Facebook").child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);
                        //google.child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);
                        //cracc.child("User").child("Google").setValue(user);
                        cracc.child("User").child("Facebook").child(uid).child("Chat List").child("default").setValue("defaults");
                        cracc.child("User").child("Facebook").child(uid).child("Community List").child("default").setValue("defaults");
                        cracc.child("User").child("Facebook").child(uid).child("Game Created").child("default").setValue("defaults");
                        cracc.child("User").child("Facebook").child(uid).child("Game Joined").child("default").setValue("defaults");
                        cracc.child("User").child("Facebook").child(uid).child("Interested List").child("default").setValue("defaults");

                        cracc.child("User Info").child("Facebook").child(uid).child("Birthday").setValue(birthday);
                        cracc.child("User Info").child("Facebook").child(uid).child("Email").setValue(Email);
                        cracc.child("User Info").child("Facebook").child(uid).child("Gender").setValue(gender);
                        cracc.child("User Info").child("Facebook").child(uid).child("Name").setValue(FirstName + " " + LastName);
                        cracc.child("User Info").child("Facebook").child(uid).child("Stars").setValue(0);
                        cracc.child("User Info").child("Facebook").child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);
                        cracc.child("User Info").child("Facebook").child(uid).child("Type").setValue("Facebook.com");
                        cracc.child("User Info").child("Facebook").child(uid).child("avatarUrl").setValue("");
                        Userid = uid;

                        editor = sharedpreferences.edit();
                        editor.putString(EMAIL, Email);
                        editor.putString(GENDER, gender);
                        editor.putString(FIRST_NAME, FirstName);
                        editor.putString(LAST_NAME, LastName);
                        editor.putString(BIRTHDAY, birthday);
                        editor.putInt(STARS, Stars);
                        editor.putString(USERID, Userid);
                        editor.putString(PHOTOAVATAR, Photoavatar);
                        editor.putString(LOGINTYPE, "Facebook");
                        editor.apply();
                        mainpage("facebook");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //throw databaseError.toException();
                }
            });
        }


    }
    //for google
    private void updateUI(FirebaseUser user, GoogleSignInAccount acct) throws IOException {
        /*
        final List<String> scopes =  new ArrayList<String>();
        HttpTransport httpTransport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();
        Person meProfile = null;

        scopes.add(Scopes.PROFILE);
        // On worker thread
        GoogleAccountCredential credential =
                GoogleAccountCredential.usingOAuth2(MainActivity.this, scopes);
        credential.setSelectedAccount(
                new Account(acct.getEmail(), "com.google"));
        People service = new People.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(getString(R.string.app_name) )
                .build();
// All the person details

        try{
        meProfile = service.people().get("people/me").execute();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

// e.g. Gender
        String gender = null;
        if(meProfile != null) {
            List<Gender> genders = meProfile.getGenders();
            if (genders != null && genders.size() > 0) {
                gender = genders.get(0).getValue();
            }
        }
*/
        uid = user.getUid();

        final String personName = acct.getDisplayName();
        final String personGivenName = acct.getGivenName();
        final String personFamilyName = acct.getFamilyName();
        final String personEmail = acct.getEmail();
        final String gender1;
        final Uri personPhoto = acct.getPhotoUrl();




        /*
        if(gender != null)
        {
            gender1 = gender;
        }
        else
        {
            gender1="";
        }
        */
        cracc = firedatabase.child("CRACC");
        final DatabaseReference google = cracc.child("Google");
        cracc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("Google").child(uid).exists()) {
                    birthday = snapshot.child("User Info").child("Google").child(uid).child("Birthday").getValue(String.class);
                    //LastName = snapshot.child("User Info").child("Google").child(uid).child("Birthday").getValue(String.class);
                    FirstName = snapshot.child("User Info").child("Google").child(uid).child("Name").getValue(String.class);
                    Stars = snapshot.child("User Info").child("Google").child(uid).child("Stars").getValue(Integer.class);
                    Email = snapshot.child("User Info").child("Google").child(uid).child("Email").getValue(String.class);
                    gender = snapshot.child("User Info").child("Google").child(uid).child("Gender").getValue(String.class);
                    String uri = snapshot.child("User Info").child("Google").child(uid).child("avatarUrl").getValue(String.class);
                    editor = sharedpreferences.edit();
                    editor.putString(EMAIL, Email);
                    editor.putString(GENDER, gender);
                    editor.putString(FIRST_NAME, FirstName);
                    editor.putString(BIRTHDAY, birthday);
                    editor.putInt(STARS, Stars);
                    editor.putString(USERID, uid);
                    editor.putString(LOGINTYPE, "Google");
                    editor.apply();
                    new DownloadImage().execute(uri);
                    mainpage("google");

                }else{
                    firsttimelogin = 1;
                    LastName  = personFamilyName;
                    FirstName = personGivenName;
                    Stars = 0;
                    Email = personEmail;

                    // Create a storage reference from our app
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReference().child("Avatar");
                    //StorageReference riversRef = storageRef.child("images/"+personPhoto.getLastPathSegment());
                    String result = UUID.nameUUIDFromBytes(uid.getBytes()).toString();
                    filepath = storageRef.child(result.toUpperCase());

                    new DownloadImage().execute(personPhoto.toString());



                    google.child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);
                    //google.child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);
                    //cracc.child("User").child("Google").setValue(user);
                    cracc.child("User").child("Google").child(uid).child("Chat List").child("default").setValue("defaults");
                    cracc.child("User").child("Google").child(uid).child("Community List").child("default").setValue("defaults");
                    cracc.child("User").child("Google").child(uid).child("Game Created").child("default").setValue("defaults");
                    cracc.child("User").child("Google").child(uid).child("Game Joined").child("default").setValue("defaults");
                    cracc.child("User").child("Google").child(uid).child("Interested List").child("default").setValue("defaults");

                    cracc.child("User Info").child("Google").child(uid).child("Birthday").setValue("");
                    cracc.child("User Info").child("Google").child(uid).child("Email").setValue(personEmail);
                    cracc.child("User Info").child("Google").child(uid).child("Gender").setValue("");
                    cracc.child("User Info").child("Google").child(uid).child("Name").setValue(personName);
                    cracc.child("User Info").child("Google").child(uid).child("Stars").setValue(0);
                    cracc.child("User Info").child("Google").child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);
                    cracc.child("User Info").child("Google").child(uid).child("Type").setValue("Google");
                    cracc.child("User Info").child("Google").child(uid).child("avatarUrl").setValue("");

                    googlesigningfinal();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //throw databaseError.toException();
            }
        });
    }

    private File createImageFile() {
        String result = UUID.nameUUIDFromBytes(uid.getBytes()).toString();
        String imageFileName = result;
        File mFileTemp = null;
        String root= getApplicationContext().getDir("my_sub_dir",Context.MODE_PRIVATE).getAbsolutePath();
        File myDir = new File(root + "/Img");
        if(!myDir.exists()){
            myDir.mkdirs();
        }
        try {
            mFileTemp=File.createTempFile(imageFileName,".jpg",myDir.getAbsoluteFile());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return mFileTemp;
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
                BitMapstore bit = new BitMapstore();
                bit.addBitmapToMemoryCache("iconbitmap", bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bitmap1 = bitmap;
            File file = createImageFile();
            if (file != null && firsttimelogin == 1) {
                FileOutputStream fout;
                try {
                    fout = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                    fout.flush();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                String a = file.getName();
                iconuri = Uri.fromFile(file);
            }
            if (iconuri != null && firsttimelogin == 1) {
                UploadTask uploadTask = filepath.putFile(iconuri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        downloadUrl = taskSnapshot.getDownloadUrl();

                        ///change this with google
                        cracc.child("User Info").child("Facebook").child(uid).child("avatarUrl").setValue(downloadUrl.toString());

                        Photoavatar = downloadUrl.toString();
                        editor = sharedpreferences.edit();
                        editor.putString(PHOTOAVATAR, Photoavatar);
                        editor.apply();
                    }
                });

            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            bitmap1 = result;
        }
    }

        private class DownloadImage1 extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // Create a progressdialog
            }

            @Override
            protected Bitmap doInBackground(String... URL) {



                Bitmap bitmap = bm;
                File file = createImageFile();
                if (file != null) {
                    FileOutputStream fout;
                    try {
                        fout = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, fout);
                        fout.flush();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String a =  file.getName();
                    iconuri  = Uri.fromFile(file);
                }
                if(iconuri != null) {
                    UploadTask uploadTask = filepath.putFile(iconuri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            System.out.println("ddd");
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            downloadUrl = taskSnapshot.getDownloadUrl();

                            ///change this with google
                            cracc.child("User Info").child("Email").child(uid).child("avatarUrl").setValue(downloadUrl.toString());

                            Photoavatar = downloadUrl.toString();
                            editor = sharedpreferences.edit();
                            editor.putString(PHOTOAVATAR, Photoavatar);
                            editor.apply();
                        }
                    });

                }

                return bitmap;
            }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            bitmap1 = result;
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
            sbmp = Bitmap.createScaledBitmap(bmp, (int)(bmp.getWidth() / factor), (int)(bmp.getHeight() / factor), false);
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
    private void checkForREADPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Granted");
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Log.d(TAG, "READ external storage Permission Required!!");
                new AlertDialog.Builder(this)
                        .setTitle("READ external storage Permission Needed")
                        .setMessage("This app needs the READ external storage permission, please accept to use Select image functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                        REQUEST_READ);
                            }
                        })
                        .create()
                        .show();

            }
            ActivityCompat.
                    requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ);

        }
    }

}

