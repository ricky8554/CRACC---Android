package com.cracc.cracc2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.util.IOUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;


/**
 * Created by cc1057 on 11/4/17.
 * The class intend to download the image
 */

public class Downloadimage {

    public static File createImageFile(Context context, String uid ) {
        String imageFileName = UUID.nameUUIDFromBytes(uid.getBytes()).toString();
        File mFileTemp = null;
        String root = context.getDir("my_sub_dir", Context.MODE_PRIVATE).getAbsolutePath();
        File myDir = new File(root + "/Img");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        try {
            mFileTemp = File.createTempFile(imageFileName, ".jpg", myDir.getAbsoluteFile());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return mFileTemp;
    }

    public static Bitmap getbitmapfromURI( String imageURL,Context context )
    {
        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageURL).openStream();
            //save the
            BitMapstore bitmapcache = new BitMapstore(context);
            File f = bitmapcache.getFile("iconbitmap");
            OutputStream os = new FileOutputStream(f);
            IOUtils.copy(input, os);
            os.close();

            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
            BitMapstore.addBitmapToMemoryCache("iconbitmap", bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Uri downloaduriimage(Bitmap bitmap,int firsttimelogin,File file)
    {
        Uri iconuri = null;
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

        return iconuri;
    }

    public static void uploadtoFirebase(final DatabaseReference cracc, StorageReference filepath,
                                        final String uid, Uri iconuri, int firsttimelogin, final String signintype, final Context context )
    {

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
                    Uri downloadUrl;
                    downloadUrl = taskSnapshot.getDownloadUrl();

                    ///change this with google
                    cracc.child("User Info").child(signintype).child(uid).child("avatarUrl").setValue(downloadUrl.toString());
                    SharedPreferences sharedpreferences;
                    SharedPreferences.Editor editor = context.getSharedPreferences(value.MyPREFERENCES, Context.MODE_PRIVATE).edit();
                    editor.putString(value.PHOTOAVATAR, downloadUrl.toString());
                    editor.commit();

                }
            });

        }
    }

    public static Bitmap decodeFile(File f)
    {
        try{
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while(true)
            {
                if(width_tmp /2 < REQUIRED_SIZE ||height_tmp /2 < REQUIRED_SIZE)
                {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *=2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f),null,o2);
        }catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
