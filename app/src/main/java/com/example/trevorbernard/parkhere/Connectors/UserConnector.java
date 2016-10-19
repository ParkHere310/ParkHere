package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by metzm on 10/16/2016.
 */

public class UserConnector {
    /* Commented out for testing purposes
    public static ArrayList<ParkingSpot> getListingsForUser(User user) {
        final ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.orderByChild("ParkingSpots/owner/email").equalTo(user.getEmail());
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    list.add(spot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list;
    }

    public static ArrayList<Reservation> getReservationsforUser(User user){
        final ArrayList<Reservation> list = new ArrayList<Reservation>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.orderByChild("Reservations/owner/email").equalTo(user.getEmail());
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Reservation spot = postSnapshot.getValue(Reservation.class);
                    list.add(spot);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list;
        return list;
    }
    public static ArrayList<Reservation> getPastReservationsforUser(User user){
        ArrayList<Reservation> list = new ArrayList<Reservation>();
        return list;
    }

    public static boolean addReviewToUser(User submitter, User reviewed, String rev){
        return true;
    }

    public static boolean addReviewToSpot(User submitter, ParkingSpot reviewed, String rev){
        return true;
    }

    public static boolean checkIn(User user, Reservation res) {
        return true;
    }
*/
}
