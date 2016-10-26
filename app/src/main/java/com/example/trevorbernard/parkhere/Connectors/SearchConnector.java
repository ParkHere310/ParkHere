package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by metzm on 10/16/2016.
 */

public class SearchConnector {
    public static ArrayList<ParkingSpot> search(double longitude, final double latitude, Date time) {

        final ArrayList<ParkingSpot> parkingSpots = new ArrayList<ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.child("ParkingSpot").orderByChild("longitude").startAt(longitude - 0.36).endAt(longitude + 0.36);
        final Date time2 = new Date(time.getTime());

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ParkingSpot spot = (snapshot.getValue(ParkingSpot.class));
                    if(spot.getLatitude() >= latitude-0.36 && spot.getLatitude() <= latitude + 0.36
                            && time2.compareTo(new Date(spot.getTimeWindow().getStartDateTime())) >= 0
                            && time2.compareTo(new Date(spot.getTimeWindow().getEndDateTime()))<=0
                            && spot.getOccupantUID().equals("-1")) {
                        parkingSpots.add(spot);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
        return list;
    }



}
