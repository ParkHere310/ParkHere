package com.example.trevorbernard.parkhere.Connectors;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.example.trevorbernard.parkhere.User.Rating;
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

public class UserConnector {
    // Commented out for testing purposes
    public static ArrayList<ParkingSpot> getListingsForUser(User user) {
        final ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        Query queryRef = mDatabase.orderByChild("ownerUID").equalTo(user.getUID()); // limited to 10
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
        return list;
    }

    public static ArrayList<Reservation> getReservationsforUser(User user){
        final ArrayList<Reservation> list = new ArrayList<Reservation>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Reservations");
        Query queryRef = mDatabase.orderByChild("ownerUID").equalTo(user.getUID()); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Reservation spot = postSnapshot.getValue(Reservation.class);
                    list.add(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list;
    }

    public static ArrayList<Reservation> getReservationsforUserUID(String Uid){
        final ArrayList<Reservation> list = new ArrayList<Reservation>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Reservations");
        Query queryRef = mDatabase.orderByChild("ownerUID").equalTo(Uid); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Reservation spot = postSnapshot.getValue(Reservation.class);
                    list.add(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list;
    }

    public static ArrayList<Reservation> getPastReservationsforUser(User user){
        final ArrayList<Reservation> list = new ArrayList<Reservation>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("PastReservations");
        Query queryRef = mDatabase.orderByChild("ownerUID").equalTo(user.getUID()); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Reservation spot = postSnapshot.getValue(Reservation.class);
                    list.add(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list;
    }

    public static ArrayList<Reservation> getPastReservationsforUserUID(String Uid){
        final ArrayList<Reservation> list = new ArrayList<Reservation>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("PastReservations");
        Query queryRef = mDatabase.orderByChild("ownerUID").equalTo(Uid); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Reservation spot = postSnapshot.getValue(Reservation.class);
                    list.add(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list;
    }

    public static boolean addRatingToUser(String reviewedUID, int num){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(reviewedUID).child("rating");
        User user = getUserFromUID(reviewedUID);
        Rating rating = user.getRating();
        rating.addRating(num);
        mDatabase.setValue(rating);
        return true;
    }

    public static boolean addRatingToSpot(String reviewedUID, int num){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("ParkingSpots").child(reviewedUID).child("rating");
        ParkingSpot spot = SpotConnector.getParkingSpotFromUID(reviewedUID);
        Rating rating = spot.getRating();
        rating.addRating(num);
        mDatabase.setValue(rating);
        return true;
    }



    public static boolean addReviewToUser(User reviewed, String rev){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("Users").child(reviewed.getUID()).child("reviews").push().getKey();
        mDatabase.getRoot().child("Users").child(reviewed.getUID()).child("reviews").child(uid).setValue(rev);
        return true;
    }

    public static boolean addReviewToSpot(ParkingSpot reviewed, String rev){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("ParkingSpots").child(reviewed.getUID()).child("reviews").push().getKey();
        mDatabase.getRoot().child("ParkingSpots").child(reviewed.getUID()).child("reviews").child(uid).setValue(rev);
        return true;
    }

    public static boolean addReviewToUser(String reviewedUID, String rev){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("Users").child(reviewedUID).child("reviews").push().getKey();
        mDatabase.getRoot().child("Users").child(reviewedUID).child("reviews").child(uid).setValue(rev);
        return true;
    }

    public static boolean addReviewToSpot(String reviewedUID, String rev){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("ParkingSpots").child(reviewedUID).child("reviews").push().getKey();
        mDatabase.getRoot().child("ParkingSpots").child(reviewedUID).child("reviews").child(uid).setValue(rev);
        return true;
    }

    public static boolean checkIn(User user, Reservation res) {
        // TODO: Update boolean, complete transaction to owner, move to PastReservations
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Reservatons");
        mDatabase.child(res.getUID()).removeValue();

        res.setCompleted(true);

        //TODO: PayPal complete Transaction

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("PastReservations")
                .child(res.getUID());
        mDatabase.setValue(res);


        return true;
    }

    public static boolean checkIn(Reservation res) {
        // TODO: Update boolean, complete transaction to owner, move to PastReservations
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
        mDatabase.child("Reservations").child(res.getUID()).removeValue();

        res.setCompleted(true);

        //TODO: PayPal complete Transaction

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("PastReservations")
                .child(res.getUID());
        mDatabase.setValue(res);

        return true;
    }

    /*public static boolean checkIn(String resUID) {
        // TODO: Update boolean, complete transaction to owner, move to PastReservations
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().getRoot();
        mDatabase.child("Reservations").child(res.getUID()).removeValue();

        res.setCompleted(true);

        //TODO: PayPal complete Transaction

        mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("PastReservations")
                .child(res.getUID());
        mDatabase.setValue(res);

        return true;
    }*/

    public static User getUserFromUID(String uID) {
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
        return list.get(0);
    }

    public static Reservation getReservationFromUID(String uID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Reservations");
        final ArrayList<Reservation> list = new ArrayList<Reservation>();
        Query queryRef = mDatabase.orderByChild("UID").equalTo(uID); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Reservation res = postSnapshot.getValue(Reservation.class);
                    list.add(res);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list.get(0);
    }

    public static Reservation getPastReservationFromUID(String uID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("PastReservations");
        final ArrayList<Reservation> list = new ArrayList<Reservation>();
        Query queryRef = mDatabase.orderByChild("UID").equalTo(uID); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Reservation res = postSnapshot.getValue(Reservation.class);
                    list.add(res);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list.get(0);
    }


//
}
