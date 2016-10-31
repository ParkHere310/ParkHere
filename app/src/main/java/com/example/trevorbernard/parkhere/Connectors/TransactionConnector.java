package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
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
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("Reservations").push().getKey();
        res.setUID(uid);
        mDatabase.child("Reservations").child(uid).setValue(res);

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

        // Remove occupant
        ParkingSpot spot = SpotConnector.getParkingSpotFromUID(res.getParkingSpotUID());
        spot.setOccupantUID("-1");
        mDatabase.getRoot().child("ParkingSpots").child(res.getParkingSpotUID()).setValue(spot);

        return true;
    }

    public static boolean cancelReservation(String reservationUid, ParkingSpot spot) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
        mDatabase.child("Reservations").child(reservationUid).removeValue();

        // Remove occupant MOVE TO CANCEL CODE
        spot.setOccupantUID("-1");
        mDatabase.getRoot().child("ParkingSpots").child(spot.getUID()).setValue(spot);

        return true;
    }
}
