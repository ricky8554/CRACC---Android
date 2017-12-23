package com.cracc.cracc2;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by apple on 11/24/17.
 */

public class DailyWeatherReport {
    private String cityName;
    private String country;
    private int currentTemp;
    private String rawDate;


    public static final String WEATHER_TYPE_CLOUDS="Clouds";
    public static final String WEATHER_TYPE_CLEAR="Clear";
    public static final String WEATHER_TYPE_RAIN="Rain";
    public static final String WEATHER_TYPE_WIND="Wind";
    public static final String WEATHER_TYPE_SNOW="Snow";

    public String getCityName() {
        return cityName;
    }

    public String getCountry() {
        return country;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public String getRawDate() {return rawDate;}

    public int getMaxTemp() {
        return maxTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public String getWeather() {
        return weather;
    }

    public long getweatherDate() {
        return weatherDate;
    }


    private int maxTemp;
    private int minTemp;
    private String weather;
    private long weatherDate;

    public DailyWeatherReport(String cityName, String country, int currentTemp, int maxTemp, int minTemp, String weather,String rawDate, Long weatherDate) {
        this.cityName = cityName;
        this.country = country;
        this.currentTemp = currentTemp;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.weather = weather;


        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        java.util.Date theday = new java.util.Date( weatherDate*1000 );
        String reportDate = df.format(theday);
        this.rawDate = reportDate;

        this.weatherDate = weatherDate;
        Log.v("hello", "the weather date " + Long.toString(weatherDate));


        this.rawDate = rawDate;
    }
}
