package com.example.android.quakereport;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

/**
 * Created by Admin on 7/16/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Place>> {
    public String str = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=10&minmag=6&orderby=time" ;
    public EarthquakeLoader(Context context,String mag) {
        super(context);
    this.str = mag;
    }


    public List<Place> loadInBackground() {
        if (str == null) {
            return null;
        }
        try {
            return QueryUtils.extractEarthquakes(QueryUtils.getURLData(this.str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}