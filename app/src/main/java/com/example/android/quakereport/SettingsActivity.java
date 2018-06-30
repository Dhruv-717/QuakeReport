package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //to add the preference screen made in xml directory
            addPreferencesFromResource(R.xml.settings_main);
        Preference magnitude = findPreference(getResources().getString(R.string.settings_min_magnitude_key));
        bindSummaryToValue(magnitude);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object o) {
            String str = o.toString();
            preference.setSummary(str);
            return true;
        }

        private void bindSummaryToValue(Preference preference){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String mag = sharedPreferences.getString(getString(R.string.settings_min_magnitude_key),getString(R.string.settings_min_magnitude_default));
            onPreferenceChange(preference,mag);
        }


    }
}