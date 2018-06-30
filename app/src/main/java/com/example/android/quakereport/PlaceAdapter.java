package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 6/24/2017.
 */

public class PlaceAdapter extends ArrayAdapter<Place> {
    PlaceAdapter(Context  context, List list){
        super(context,0,list);
    }
    PlaceAdapter(Context  context){
        super(context, 0, new ArrayList<Place>());
    }
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Place placeOfEarthquake = getItem(position);
        View listView = convertView;
        if(listView == null){
        listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
    }
        TextView mag = (TextView) listView.findViewById(R.id.magnitude_text_view);
        mag.setText(""+placeOfEarthquake.getMagnitude());
        TextView place = (TextView) listView.findViewById(R.id.city_text_view);
        place.setText(placeOfEarthquake.getPlace());
        TextView nearTo = (TextView) listView.findViewById(R.id.near_of_text_view);
        nearTo.setText(placeOfEarthquake.getNearTo());
        TextView dateStamp = (TextView) listView.findViewById(R.id.date_text_view);
        dateStamp.setText(placeOfEarthquake.getDateStamp());
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(placeOfEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        return listView;
    }
}
