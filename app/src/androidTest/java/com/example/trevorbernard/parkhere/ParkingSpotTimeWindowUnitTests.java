package com.example.trevorbernard.parkhere;

import android.support.annotation.NonNull;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
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

import static org.junit.Assert.assertNotNull;

/**
 * Created by metzm on 11/5/2016.
 */

public class ParkingSpotTimeWindowUnitTests {
    private Query queryRef, queryRef2, queryRef3, queryRef4;

    @Before
    public void setUp() {
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
    public void PSHasStartDate() throws Exception {
        final ArrayList<ParkingSpot> spots = new ArrayList<ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
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
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                                spots.add(spot);
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
            assertNotNull("ParkingSpot has start date", spot.getTimeWindow().getStartDateTime());
        }
    }

    @Test
    public void PSHasStartTime() throws Exception {
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
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                                spots.add(spot);
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
            assertNotNull("ParkingSpot has start time", spot.getTimeWindow().getStartDateTime());
        }
    }

    @Test
    public void PSHasEndDate() throws Exception {
        final ArrayList<ParkingSpot> spots = new ArrayList<ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        queryRef3 = mDatabase;
        final ArrayList<Boolean> done = new ArrayList<Boolean>();
        done.add(false);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    queryRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                                spots.add(spot);
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
            assertNotNull("ParkingSpot has end date", spot.getTimeWindow().getStartDateTime());
        }
    }

    @Test
    public void PSHasEndTime() throws Exception {
        final ArrayList<ParkingSpot> spots = new ArrayList<ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        queryRef4 = mDatabase;
        final ArrayList<Boolean> done = new ArrayList<Boolean>();
        done.add(false);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    queryRef4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                                spots.add(spot);
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
            assertNotNull("ParkingSpot has end time", spot.getTimeWindow().getStartDateTime());
        }
    }
}
