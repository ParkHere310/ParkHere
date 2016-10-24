package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by metzm on 10/16/2016.
 */

public class SearchConnector {
    public static ArrayList<ParkingSpot> search(double longitude, double latitude, String date,
                                                int time) {

        ArrayList<ParkingSpot> parkingSpots;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.child("ParkingSpot").orderByChild("address/mLongitude").startAt(longitude - 0.36).endAt(longitude + 0.36);


        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //parkingSpots.add(snapshot.getValue(ParkingSpot.class));
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
