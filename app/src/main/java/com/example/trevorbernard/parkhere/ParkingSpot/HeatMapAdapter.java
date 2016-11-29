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
 * Created by mac on 11/28/16.
 */

public class HeatMapAdapter extends ArrayAdapter<PhysicalSpot> {

    private ArrayList<PhysicalSpot> physicalSpotNames;

    public HeatMapAdapter(Context context, ArrayList<PhysicalSpot> physicalSpots) {
        super(context, 0, physicalSpots);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhysicalSpot physicalSpot = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_row, parent, false);
        }
        if(physicalSpot != null) {

            String Name  = getItem(position).getName() + "\n AT ADDRESS: " + getItem(position).getAddress();

            int heatNum = getItem(position).getTimesListed();

            TextView name = (TextView) convertView.findViewById(R.id.name);
            name.setText(Name);
            name.setTextColor(Color.BLACK);

            if(heatNum < 1){
                convertView.setBackgroundColor(Color.GREEN);
            }
            else if(heatNum < 3 &&  heatNum > 0){
                convertView.setBackgroundColor(Color.YELLOW);
            }
            else {
                convertView.setBackgroundColor(Color.RED);
            }




            /*
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

            */
        }

        return convertView;

    }

}
