/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.android.quakereport.QueryUtils.extractEarthquakes;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Place>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private PlaceAdapter adapter;
    ProgressBar progressBar;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public android.support.v4.content.Loader<List<Place>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(getString(R.string.settings_min_magnitude_key), getString(R.string.settings_min_magnitude_default));
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", "time");
        Log.v("URL: ",uriBuilder.toString());
        String str = uriBuilder.toString();
        return new EarthquakeLoader(EarthquakeActivity.this,str);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Place>> loader, List<Place> data) {
    adapter.clear();
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        earthquakeListView.setAdapter(adapter);
        TextView textView = (TextView) findViewById(R.id.empty_case);
         progressBar = (ProgressBar) findViewById(R.id.progress_view);
//       Create a new {@link ArrayAdapter} of earthquakes
//adapter = new PlaceAdapter(this);
if(data!=null&&!data.isEmpty()) {
    progressBar.setVisibility(View.GONE);
    adapter.addAll(data);
}
else{
    progressBar.setVisibility(View.GONE);
    textView.setText("No Earthquake Found");
}
//         Set the adapter on the {@link ListView}
//         so the list can be populated in the user interface

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Place>> loader) {
    adapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        adapter=new PlaceAdapter(this);
        // Create a fake list of earthquake locations.
//        ArrayList<Place> earthquakes = new ArrayList<>();
//        earthquakes.add(new Place(7.2f,"San Francisco","12-5-2017"));
//        earthquakes.add(new Place(7.2f,"London","12-5-2017"));
//        earthquakes.add(new Place(7.2f,"Tokyo","12-5-2017"));
//        earthquakes.add(new Place(7.2f,"Mexico City","12-5-2017"));
//        earthquakes.add(new Place(7.2f,"Moscow","12-5-2017"));
//        earthquakes.add(new Place(7.2f,"Rio de Janeiro","12-5-2017"));
//        earthquakes.add(new Place(7.2f,"Paris","12-5-2017"));
        // Find a reference to the {@link ListView} in the layout
//        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
//        PlaceAdapter adapter = new PlaceAdapter(this,earthquakes);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
//        earthquakeListView.setAdapter(adapter);
//        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            }
//        });/
        TextView textView = (TextView) findViewById(R.id.no_internet);
        progressBar = (ProgressBar) findViewById(R.id.progress_view);
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected)
getSupportLoaderManager().initLoader(0,null,this).forceLoad();
        else{
            textView.setText("No Internet Connectivity");
        progressBar.setVisibility(View.GONE);
        }

    }


}

