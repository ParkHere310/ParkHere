package com.example.trevorbernard.parkhere.ParkingSpot;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by zilongxiao on 11/27/16.
 */

public class PhysicalSpotAdapter extends ArrayAdapter<PhysicalSpotDistance>{
    private ArrayList<PhysicalSpot> spots;

    public PhysicalSpotAdapter(Context context, ArrayList<PhysicalSpotDistance> physicalSpots) {
        super(context, 0, physicalSpots);
        this.spots = spots;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhysicalSpotDistance physicalSpotDistance = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_row, parent, false);
        }
        if(physicalSpotDistance != null) {
            PhysicalSpot physicalSpot = physicalSpotDistance.parkingSpot;
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView distance = (TextView) convertView.findViewById(R.id.distance);
            TextView rating = (TextView) convertView.findViewById(R.id.rating);

            name.setTextColor(Color.BLACK);

            name.setText(physicalSpot.getName());
            distance.setText(new DecimalFormat("#.##").format(physicalSpotDistance.distance) + " miles");
            if(physicalSpot.getRating() == null) {
                rating.setText("Rating: " + 3.0);
            } else {
                rating.setText("Rating: " + new DecimalFormat("#.#").format(physicalSpot.getRating().calculateRating()));
            }
        }
        return convertView;

    }

}
