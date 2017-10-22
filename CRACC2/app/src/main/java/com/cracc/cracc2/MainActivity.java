package com.cracc.cracc2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    private Button login;


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
        Button text1 = (Button) findViewById(R.id.login_facebook);
        Button text2 = (Button) findViewById(R.id.login_google);
        login = (Button) findViewById(R.id.login);
        EditText text4 = (EditText) findViewById(R.id.loginemail);
        EditText text5 = (EditText) findViewById(R.id.loginpass);
        Button text6 = (Button) findViewById(R.id.creatnewaccount);
        Button text7 = (Button) findViewById(R.id.forgot);
        text.setTypeface(myTypeface1);
        text1.setTypeface(myTypeface1);
        text2.setTypeface(myTypeface1);
        login.setTypeface(myTypeface3);
        text4.setTypeface(myTypeface2);
        text5.setTypeface(myTypeface2);
        text6.setTypeface(myTypeface2);
        text7.setTypeface(myTypeface2);
    }


    public void mainpage(View v)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}

