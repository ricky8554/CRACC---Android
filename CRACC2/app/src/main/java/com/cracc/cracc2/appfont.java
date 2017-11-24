package com.cracc.cracc2;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by cc1057 on 11/10/17.
 */

public class appfont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Myriad-Pro.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
