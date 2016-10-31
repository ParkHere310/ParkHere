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
import java.util.Date;

/**
 * Created by trevorbernard on 10/30/16.
 */

public class ParkingSpotReservationAdapter extends ArrayAdapter<ParkingSpotReservation> {

    private ArrayList<ParkingSpotReservation> reservations;

    public ParkingSpotReservationAdapter(Context context, ArrayList<ParkingSpotReservation> reservations) {
        super(context, 0, reservations);
        this.reservations = reservations;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ParkingSpotReservation parkingSpotReservation = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_row, parent, false);
        }
        if(parkingSpotReservation != null) {
            ParkingSpot parkingSpot = parkingSpotReservation.parkingSpot;
            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView price = (TextView) convertView.findViewById(R.id.price);
            TextView date = (TextView) convertView.findViewById(R.id.date);

            name.setTextColor(Color.BLACK);

            name.setText(parkingSpot.getName());
            price.setText(" |  Rate: $ " + new DecimalFormat("#.00").format(parkingSpot.getPrice()/100));
            date.setText( new Date(parkingSpot.getTimeWindow().getStartDateTime()).getMonth() + "/" + new Date(parkingSpot.getTimeWindow().getStartDateTime()).getDay() );

        }

        return convertView;

    }

}
