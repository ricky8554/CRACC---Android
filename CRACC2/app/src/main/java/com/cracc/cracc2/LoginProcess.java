package com.cracc.cracc2;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

/**
 * Created by cc1057 on 11/4/17.
 * This page is intend to handle the LoginProcess of Google and FaceBook And Email
 */

public class LoginProcess{

    public static String settingsignin(String logintype, String uid, DataSnapshot snapshot,
                                       SharedPreferences.Editor editor)
    {
        String birthday = snapshot.child("User Info").child(logintype).child(uid).child("Birthday").getValue(String.class);
        String Name = snapshot.child("User Info").child(logintype).child(uid).child("Name").getValue(String.class);
        int Stars = snapshot.child("User Info").child(logintype).child(uid).child("Stars").getValue(Integer.class);
        String Email = snapshot.child("User Info").child(logintype).child(uid).child("Email").getValue(String.class);
        String gender = snapshot.child("User Info").child(logintype).child(uid).child("Gender").getValue(String.class);
        String uri = snapshot.child("User Info").child(logintype).child(uid).child("avatarUrl").getValue(String.class);

        editor.putString("emailKey", Email);
        editor.putString("genderKey", gender);
        editor.putString("namekey", Name);
        editor.putString("birthdaykey", birthday);
        editor.putInt("starskey", Stars);
        editor.putString("UserId", uid);
        editor.putString("LoginType", logintype);
        editor.commit();

        return uri;
    }

    public static void settingSignup(String SignupType, String uid, DatabaseReference cracc,
                                     String Email, String Name, String gender, String birthday)
    {
        String string1 = "";
        for (int i = 0; i < Email.length(); i++) {
            if (Email.substring(i, i + 1).equals(".")) {
                string1 += ",";
            } else {
                string1 += Email.substring(i, i + 1);
            }
        }
        if(!SignupType.equals("Email"))
            cracc.child(SignupType).child(string1).child("Timestamp").setValue(ServerValue.TIMESTAMP);
        else
            cracc.child(SignupType).child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);

        cracc.child("User").child(SignupType).child(uid).child("Chat List").child("default").setValue("defaults");
        cracc.child("User").child(SignupType).child(uid).child("Community List").child("default").setValue("defaults");
        cracc.child("User").child(SignupType).child(uid).child("Game Created").child("default").setValue("defaults");
        cracc.child("User").child(SignupType).child(uid).child("Game Joined").child("default").setValue("defaults");
        cracc.child("User").child(SignupType).child(uid).child("Interested List").child("default").setValue("defaults");

        cracc.child("User Info").child(SignupType).child(uid).child("Birthday").setValue(birthday);
        cracc.child("User Info").child(SignupType).child(uid).child("Email").setValue(Email);
        cracc.child("User Info").child(SignupType).child(uid).child("Gender").setValue(gender);
        cracc.child("User Info").child(SignupType).child(uid).child("Name").setValue(Name);
        cracc.child("User Info").child(SignupType).child(uid).child("Stars").setValue(0);
        cracc.child("User Info").child(SignupType).child(uid).child("Timestamp").setValue(ServerValue.TIMESTAMP);
        cracc.child("User Info").child(SignupType).child(uid).child("Type").setValue(SignupType);
        if(!SignupType.equals("Email"))
        cracc.child("User Info").child(SignupType).child(uid).child("avatarUrl").setValue("");
    }

    public static Bitmap getScaledBitmap(String picturePath, int width, int height) {
        BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
        sizeOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, sizeOptions);

        int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

        sizeOptions.inJustDecodeBounds = false;
        sizeOptions.inSampleSize = inSampleSize;

        return BitmapFactory.decodeFile(picturePath, sizeOptions);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
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

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
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


}
