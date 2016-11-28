package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.trevorbernard.parkhere.Connectors.UserConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotAdapter;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotDistance;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hexi on 2016/10/23.
 */




    public static double toMiles(double meters)
    {
        return (meters / 1609.344);
    }


    //This is the void called on selection of a parking spot item
    private void CreateOnlickCallback() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent myIntent = new Intent(SearchResultActivity.this, RentSpotActivity.class);
                String spotUID = parkingSpotDistances.get(position).parkingSpot.getUID();
                String distance = new DecimalFormat("#.#").format(parkingSpotDistances.get(position).distance);
                myIntent.putExtra("spotUID", spotUID);
                myIntent.putExtra("distance", distance);
                SearchResultActivity.this.startActivity(myIntent);
            }
        });



    }

    private ArrayList<ParkingSpot> sortByPrice(ArrayList<ParkingSpot> spotList) {
        Collections.sort(spotList, new Comparator<ParkingSpot>() {
            @Override
            public int compare(ParkingSpot o1, ParkingSpot o2) {
                return new Integer(o1.getPrice()).compareTo(o2.getPrice());
            }
        });
        return spotList;
    }

    private ArrayList<ParkingSpot> sortBySpotRating(ArrayList<ParkingSpot> spotList) {
        Collections.sort(spotList, new Comparator<ParkingSpot>() {
            @Override
            public int compare(ParkingSpot o1, ParkingSpot o2) {
                return new Double(o1.getRating().calculateRating()).compareTo(o2.getRating().calculateRating());
            }
        });
        return spotList;
    }

    private ArrayList<ParkingSpot> sortByOwnerRating(ArrayList<ParkingSpot> spotList) {

        Collections.sort(spotList, new Comparator<ParkingSpot>() {
            @Override
            public int compare(ParkingSpot o1, ParkingSpot o2) {
                User u1 = UserConnector.getUserFromUID(o1.getOwnerUID());
                User u2 = UserConnector.getUserFromUID(o2.getOwnerUID());
                return new Double(u1.getRating().calculateRating()).compareTo(u2.getRating().calculateRating());
            }
        });
        return spotList;
    }

    public User getUserFromUID(String uID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        final ArrayList<User> list = new ArrayList<User>();
        Query queryRef = mDatabase.orderByChild("UID").equalTo(uID); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    list.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // This is really shitty code and I'm sorry i wrote it.
        while(list.isEmpty()) {
            try {
                Thread.sleep(5);
            } catch(Exception e) {

            }
        }
        return list.get(0);
    }

}
