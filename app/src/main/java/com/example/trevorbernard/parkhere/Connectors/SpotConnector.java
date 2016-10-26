package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.User.User;
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

public class SpotConnector {
    public static boolean postSpot(ParkingSpot spot, User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("ParkingSpots").push().getKey();
        spot.setUID(uid);
        mDatabase.child("ParkingSpots").child(uid).setValue(spot);
        return true;
    }

    public static boolean editSpot(ParkingSpot spot, User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ParkingSpots").child(spot.getUID()).setValue(spot);
        return true;
    }

    public static boolean removeSpot(ParkingSpot spot, User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ParkingSpots").child(spot.getUID()).setValue(null);
        return true;
    }

    public static ParkingSpot getParkingSpotFromUID(String uID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        final ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
        Query queryRef = mDatabase.orderByChild("ownerUID").equalTo(uID); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    list.add(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list.get(0);
    }
}
