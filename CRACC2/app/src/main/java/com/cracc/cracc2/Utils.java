package com.cracc.cracc2;

import android.app.Activity;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by andrei on 29.06.2015.
 */
public class Utils {

    public static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;

        try {
            byte[] bytes = new byte[buffer_size];
            while(true) {
                int count = is.read(bytes, 0, buffer_size);
                if(count == -1) {
                    break;
                }
                os.write(bytes, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BottomSheetDialog bottomsheetdialog(Activity activity,int content)
    {
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.setContentView(content);
        //dialog.show();
        return dialog;
    }
}
