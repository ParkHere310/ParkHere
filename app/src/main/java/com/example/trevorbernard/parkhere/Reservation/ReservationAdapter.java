package com.example.trevorbernard.parkhere.Reservation;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotReservation;
import com.example.trevorbernard.parkhere.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by trevorbernard on 10/30/16.
 */

public class ReservationAdapter extends ArrayAdapter<Reservation> {

    private ArrayList<Reservation> reservations;

    public ReservationAdapter(Context context, ArrayList<Reservation> reservations) {
        super(context, 0, reservations);
        this.reservations = reservations;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Reservation res = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_row, parent, false);
        }
        if(res != null) {

            TextView name = (TextView) convertView.findViewById(R.id.name);
            TextView price = (TextView) convertView.findViewById(R.id.price);
            TextView date = (TextView) convertView.findViewById(R.id.date);

            name.setTextColor(Color.BLACK);
            if(res != null) {
                name.setText((new Date(res.getDate()).getMonth() + 1) + "/" + new Date(res.getDate()).getDate() + " Rental");
                price.setText("$ " + new DecimalFormat("#.00").format(res.getPrice()/(double)100));

            }
        }

        return convertView;

    }

}
