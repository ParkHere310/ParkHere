package com.example.trevorbernard.parkhere;

import android.support.annotation.NonNull;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.User.Rating;
import com.example.trevorbernard.parkhere.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by mac on 11/7/16.
 */

public class UserAndParkingRatings0to5CanEnter {


    private Query queryRef,queryRef2;
    private int counter;

    @Before
    public void setUp() {
        counter = 0;
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword("test44@test.com", "test1!").addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                        }
                    }
            );
        }
    }




    @Test
    public void UserHaveRating0To5() throws Exception {
        final ArrayList<User> users = new ArrayList<User>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        queryRef = mDatabase;
        final ArrayList<Boolean> done = new ArrayList<Boolean>();
        done.add(false);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                                counter = -1;
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                //put a rating of 0 to 5 every time.
                                User user = postSnapshot.getValue(User.class);
                                if(user.getRating() != null) {
                                    if(counter == 5) {
                                        counter = 0;
                                    } else{
                                        counter++;
                                    }
                                    user.getRating().addRating(counter);
                                    users.add(user);
                                }
                            }
                            done.add(0, true);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (Exception e) {
                }
            }
        });
        thread.start();

        while(done.get(0) == false) {
            Thread.sleep(10);
        }
        for(User user : users) {
            assertTrue("Was able to add user rating between 0 and 5", user.getRating().calculateRating() >= 0 &&
                    user.getRating().calculateRating() <= 5);
        }
    }


    @Test
    public void ParkingSpotsHaveRating0to5() throws Exception {
        final ArrayList<ParkingSpot> spots = new ArrayList<ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        queryRef2 = mDatabase;
        final ArrayList<Boolean> done = new ArrayList<Boolean>();
        done.add(false);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    queryRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            counter = -1;
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                                if(spot.getRating() != null) {
                                    //put a rating of 0 to 5 everytime
                                    if(counter == 5) {
                                        counter = 0;
                                    } else{
                                        counter++;
                                    }
                                    spot.getRating().addRating(counter);
                                    spots.add(spot);
                                }
                            }
                            done.add(0, true);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (Exception e) {
                }
            }
        });
        thread.start();
        while(done.get(0) == false) {
            Thread.sleep(10);
        }
        for(ParkingSpot spot : spots) {
            assertTrue("ParkingSpot rating is between 0 and 5", spot.getRating().calculateRating() >= 0 &&
                    spot.getRating().calculateRating() <= 5);
        }
    }



}
