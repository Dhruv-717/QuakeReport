package com.example.android.quakereport;

import android.util.Log;

/**
 * Created by Admin on 6/24/2017.
 */

public class Place {
    private double magnitude;
    private String place;
    private String nearTo;
    private String dateStamp;

    Place(double mag, String placeOfEarthquake, String date) {
        this.magnitude = mag;
        boolean hasFound = false;
        int i = 0;
        while (!hasFound && i < placeOfEarthquake.length() - 6) {
            if (placeOfEarthquake.substring(i, i + 4).equals(" of ")) {
                this.nearTo = placeOfEarthquake.substring(0, i + 3);
                this.place = placeOfEarthquake.substring(i + 4);
                Log.v("Place", "In \"of\" condition");
                hasFound = true;
                break;
            }
            i++;
        }
if(!hasFound) {
    this.nearTo = "Near The";
    this.place = placeOfEarthquake;
}
        this.dateStamp =date;
}

    public double getMagnitude(){
        return this.magnitude;
    }
    public String getPlace(){
        return this.place;
    }
    public String getNearTo(){
        return this.nearTo;
    }
    public String getDateStamp(){
        return this.dateStamp;
    }
}
