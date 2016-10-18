package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
}
