package com.cracc.cracc2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

/**
 * Created by cc1057 on 11/10/17.
 */

public class timepicker {

    public static Dialog timepicker1(Context c, final NumberPicker date,
                                   final NumberPicker hour, final NumberPicker minute ,
                                   final NumberPicker ampm, BottomSheetDialog d)
    {
        d.setTitle("Datepicker");

        Window window = d.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);



        date.setMaxValue(5);
        date.setMinValue(0);
        hour.setMaxValue(12);
        hour.setMinValue(1);
        minute.setMaxValue(59);
        minute.setMinValue(0);
        ampm.setMaxValue(1);
        ampm.setMinValue(0);
        Calendar cal = Calendar.getInstance();
        String s[] = new String[6];
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];
        Calendar cal1 = new GregorianCalendar(year, month, day);
        int daysInMonth = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
        s[0] = "Today";
        for( int i = 0; i<6;i++)
        {
            if(i!=0)
            {
                s[i] = weekday+" "+getMonthFromInt(month)+" "+day;
            }
            if(day == daysInMonth && month == 11)
            {
                year = year+1;
                month = 0;
                day = 1;
            }
            else if( day == daysInMonth )
            {
                month = month+1;
                day = 1;
            }
            else
            {
                day +=1;
            }
            cal1 = new GregorianCalendar(year, month, 12);
            daysInMonth = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(year,month,day);
            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];

        }


        date.setWrapSelectorWheel(false);
        date.setDisplayedValues(s);
        hour.setWrapSelectorWheel(false);
        minute.setWrapSelectorWheel(false);
        ampm.setWrapSelectorWheel(false);
        ampm.setDisplayedValues( new String[] { "AM", "PM"} );
        date.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        hour.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        minute.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        ampm.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


        /*
        v.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tv.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        */


        d.show();
        return d;
    }

    public static String getMonthFromInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month.substring(0,3);
    }

    public static String getString(int num) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];
        Calendar cal1 = new GregorianCalendar(year, month, day);
        int daysInMonth = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
        for( int i = 0; i<num;i++)
        {

            if(day == daysInMonth && month == 11)
            {
                year = year+1;
                month = 0;
                day = 1;
            }
            else if( day == daysInMonth )
            {
                month = month+1;
                day = 1;
            }
            else
            {
                day +=1;
            }
            cal1 = new GregorianCalendar(year, month, 12);
            daysInMonth = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(year,month,day);
            dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            weekday = new DateFormatSymbols().getShortWeekdays()[dayOfWeek];

        }
        return "" + getMonthFromInt(month) +" "+ day +","+ year;
    }



}
