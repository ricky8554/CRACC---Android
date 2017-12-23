package com.cracc.cracc2;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

/**
 * Created by cc1057 on 11/3/17.
 */

public class BitMapstore {


    private File cacheDir;
    public BitMapstore(Context context)
    {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "CRACCimage");
            //file = new File(this.getCacheDir(), filename);
        }
        else
        {
            cacheDir = context.getCacheDir();
        }
        if(!cacheDir.exists())
        {
            cacheDir.mkdirs();
        }

    }
    public BitMapstore(Context context, String video)
    {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "CRACCvideo");
            //file = new File(this.getCacheDir(), filename);
        }
        else
        {
            cacheDir = context.getCacheDir();
        }
        if(!cacheDir.exists())
        {
            cacheDir.mkdirs();
        }

    }
    public File getFile(String uid)
    {
        String filename = String.valueOf(uid.hashCode());
        File f = new File(cacheDir, filename);
        return f;

    }


    public void clear()
    {
        File[] files = cacheDir.listFiles();
        if(files == null)
        {
            return;
        }
        for(File f: files)
        {
            f.delete();
        }

    }


    private final static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final static int cacheSize = maxMemory / 256;

    private static LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
        }
    };


    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public static void clean() {
        mMemoryCache.evictAll();
    }

}
