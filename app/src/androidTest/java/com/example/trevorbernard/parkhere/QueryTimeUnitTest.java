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

import static org.junit.Assert.*;

/**
 * Created by metzm on 11/5/2016.
 */

public class QueryTimeUnitTest {
    private Query queryRef;

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
        long time1 = System.currentTimeMillis();
        final ArrayList<Long> times = new ArrayList<Long>();
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
                            times.add(System.currentTimeMillis());
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
        assertTrue("Query time <= 2 sec", times.get(0) - time1 <= 2000);

    }
}
