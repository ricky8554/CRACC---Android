package com.cracc.cracc2;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.Manifest;

public class MainActivity extends AppCompatActivity {


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
    private EditText createaccount3confirmpassword;
    private Button createaccount2next;
    private Button createaccount2back;
    private Button createaccount3signup;
    private Button createaccount3back;


    private BitmapDrawable bd = null;             //informationsetting icon bitmapdrable
    private Bitmap bm;                      //bit map

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initialize();

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
        Button facebook = (Button) findViewById(R.id.login_facebook);
        Button google = (Button) findViewById(R.id.login_google);
        login = (Button) findViewById(R.id.login);
        EditText loginemail = (EditText) findViewById(R.id.loginemail);
        EditText loginpass = (EditText) findViewById(R.id.loginpass);
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
        createaccount3email= findViewById(R.id.createaccount3email);
        createaccount3password = findViewById(R.id.createaccount3password);
        createaccount3confirmpassword= findViewById(R.id.createaccount3confirmpassword);

        createaccount3back = findViewById(R.id.createaccount3back);
        createaccount3signup = findViewById(R.id.createaccount3signup);

        //set text font
        text.setTypeface(myTypeface1);
        facebook.setTypeface(myTypeface1);
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

        setAllFrametoInvisible();
    }


    private void setAllFrametoInvisible() {
        forgotpasswordframe.setVisibility(View.GONE);
        createaccountframe1.setVisibility(View.GONE);
        createaccountframe2.setVisibility(View.GONE);
        createaccountframe3.setVisibility(View.GONE);
    }

    public void createaccount3back(View v)
    {
        createaccountframe3.setVisibility(View.GONE);
        createaccountframe2.setVisibility(View.VISIBLE);
    }

    public void createaccount3(View v)
    {
        createaccountframe3.setVisibility(View.VISIBLE);
    }

    public void gender(View v )
    {
        if(v == createaccount2male)
        {
            createaccount2male.setBackgroundResource(R.drawable.genderbutton2);
            createaccount2male.setTextColor(Color.parseColor("#FFFFFF"));
            createaccount2female.setBackgroundResource(R.drawable.genderbutton1);
            createaccount2female.setTextColor(Color.parseColor("#6e6e6e"));
        }
        else if(v == createaccount2female)
        {
            createaccount2female.setBackgroundResource(R.drawable.genderbutton2);
            createaccount2female.setTextColor(Color.parseColor("#FFFFFF"));
            createaccount2male.setBackgroundResource(R.drawable.genderbutton1);
            createaccount2male.setTextColor(Color.parseColor("#6e6e6e"));
        }


    }
    public void createaccount2back(View v)
    {
        createaccountframe2.setVisibility(View.GONE);
        createaccountframe1.setVisibility(View.VISIBLE);
    }
    public void createaccount2(View v )
    {
        createaccountframe2.setVisibility(View.VISIBLE);
    }
    public void createaccount1(View v )
    {
        createaccountframe1.setVisibility(View.VISIBLE);
    }
    public void getPicture(View v )
    {
        checkForREADPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED /*|| ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.MANAGE_DOCUMENTS) != PackageManager.PERMISSION_GRANTED*/) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }
    public void forgetpassword(View v )
    {
        forgotpasswordframe.setVisibility(View.VISIBLE);
    }
    public void loginpage(View v )
    {
        setAllFrametoInvisible();
    }
    public void mainpage(View v)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
    public void empty1(View v )
    {

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
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        bm = getScaledBitmap(picturePath,800,800);
        bd = new BitmapDrawable(getResources(), getCroppedBitmap(BitmapFactory.decodeFile(picturePath), 400));
        profileimage.setBackground(bd);
        profileimage.setText("");
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

