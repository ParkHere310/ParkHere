package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Hexi on 2016/10/23.
 */

public class SearchResultActivity extends Activity {
    ListView listView;
    DatabaseReference mDatabase;
    Query queryRef;
    double longitude;
    double latitude;

    ArrayList<ParkingSpot> parkingSpots;
    ArrayList<String> stringSpots;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);

        listView = (ListView) findViewById(R.id.list);
        initiateVariables();
    }
    private void initiateVariables() {
        longitude = -118.2;
        latitude = 34;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        parkingSpots = new ArrayList<ParkingSpot>();
        stringSpots = new ArrayList<String>();
        populateParkingSpots();
    }

    private void populateParkingSpots() {

        queryRef = mDatabase.child("ParkingSpots").orderByChild("longitude").startAt(longitude - 0.36).endAt(longitude + 0.36);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Location source = new Location("");
                source.setLongitude(longitude);
                source.setLatitude(latitude);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ParkingSpot spot = (snapshot.getValue(ParkingSpot.class));

                    //if the spot is within this range
                    if(spot.getLatitude() >= latitude-0.36 && spot.getLatitude() <= latitude + 0.36) {

                        //if the spot is within time, and unoccupied
                        /*if(time2Start.compareTo(new Date(spot.getTimeWindow().getStartDateTime())) >= 0
                                && time2Start.compareTo(new Date(spot.getTimeWindow().getEndDateTime())) < 0
                                && time2End.compareTo(new Date(spot.getTimeWindow().getEndDateTime())) < 0
                                && time2End.compareTo(new Date(spot.getTimeWindow().getStartTime())) > 0
                                && spot.getOccupantUID().equals("-1"))*/ {

                            Location spotloc = new Location("");
                            spotloc.setLongitude(spot.getLongitude());
                            spotloc.setLatitude(spot.getLatitude());
                            Double distance = (double) spotloc.distanceTo(source); // in meters
                            distance = toMiles(distance);

                            parkingSpots.add(spot);
                            stringSpots.add(new DecimalFormat("#.##").format(distance) + " miles " + spot.getName() + ": " + spot.getAddress());
                        }
                    }
                }
       
                ArrayAdapter arrayAdapter = new ArrayAdapter<String>(SearchResultActivity.this,android.R.layout.simple_list_item_1,stringSpots);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static double toMiles(double meters)
    {
        return (meters / 1609.344);
    }

    public class ParkingSpotDistance {
        public ParkingSpot spot;
        public double distance;
    }

}
