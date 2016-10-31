package com.example.trevorbernard.parkhere.ParkingSpot;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.R;
import com.google.android.gms.vision.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by trevorbernard on 10/29/16.
 */

public class ParkingSpotAdapter extends ArrayAdapter<ParkingSpotDistance> {

    private ArrayList<ParkingSpot> spots;

    public ParkingSpotAdapter(Context context, ArrayList<ParkingSpotDistance> parkingSpots) {
        super(context, 0, parkingSpots);
        this.spots = spots;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ParkingSpotDistance parkingSpotDistance = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_row, parent, false);
        }
        if(parkingSpotDistance != null) {
            ParkingSpot parkingSpot = parkingSpotDistance.parkingSpot;
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView distance = (TextView) convertView.findViewById(R.id.distance);
            TextView rating = (TextView) convertView.findViewById(R.id.rating);
            TextView price = (TextView) convertView.findViewById(R.id.price);

            name.setTextColor(Color.BLACK);

            name.setText(parkingSpot.getName());
            price.setText(" |  Rate: $ " + new DecimalFormat("#.00").format(parkingSpot.getPrice()/100));
            distance.setText(new DecimalFormat("#.##").format(parkingSpotDistance.distance) + " miles");
            if(parkingSpot.getRating() == null) {
                rating.setText("Rating: " + 3.0);
            } else {
                rating.setText("Rating: " + new DecimalFormat("#.#").format(parkingSpot.getRating().calculateRating()));
            }
        }

        return convertView;

    }

}
