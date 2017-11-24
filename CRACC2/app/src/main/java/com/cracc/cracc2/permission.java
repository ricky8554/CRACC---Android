package com.cracc.cracc2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by cc1057 on 11/5/17.
 */

public class permission {


    public static void checkpermission(Activity c, final int b, final Activity a, String s, final String permission) {
        if (ContextCompat.checkSelfPermission(c, permission)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation? delete this
            if (ActivityCompat.shouldShowRequestPermissionRationale(c,
                    permission)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(c)
                        .setTitle(s + " Permission Needed")
                        .setMessage("This app needs the " + s + " permission, please accept to use" + s + " functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(a,
                                        new String[]{permission},
                                        b);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(c,
                        new String[]{permission},
                        b);
            }
        }
    }

    public static void checkpermissionall(Activity c, final int b, final Activity a, String s, final String[] permission) {

        for (String str : permission) {
            if (ContextCompat.checkSelfPermission(c, str)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(c,
                        permission,
                        b);
                break;

            }
        }

    }
}

