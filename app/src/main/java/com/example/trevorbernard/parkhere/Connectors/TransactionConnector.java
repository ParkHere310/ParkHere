package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by metzm on 10/16/2016.
 */

public class TransactionConnector {



    public static boolean addReservation(Reservation res) {
        // Adds Reservation to Database, updates seekerUID of ParkingSpot
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Reservations");
        String uid = mDatabase.push().getKey();
        res.setUID(uid);
        mDatabase.child(uid).setValue(res);

        FirebaseDatabase.getInstance().getReference().getRoot().child("ParkingSpots")
                .child(res.getParkingSpotUID()).child("occupantUID").setValue(res.getSeekerUID());


        //TODO: make seeker pay here

        return true;
    }

    public static boolean cancelReservation(Reservation res, User user) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Reservations");
        String uid = mDatabase.push().getKey();
        res.setUID(uid);
        mDatabase.child(uid).removeValue();
        return true;
    }
}
