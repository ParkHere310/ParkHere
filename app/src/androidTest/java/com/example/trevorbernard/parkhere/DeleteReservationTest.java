package com.example.trevorbernard.parkhere;

import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;

import com.example.trevorbernard.parkhere.Client.ReservedSpotActivity;
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
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by junseob on 11/7/16.
 */

public class DeleteReservationTest {

    @Rule
    public ActivityTestRule<ReservedSpotActivity> mActivityRule = new ActivityTestRule<>(
            ReservedSpotActivity.class, false, false);

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
    public void deleteReservationTest() throws InterruptedException {

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

        //onView(withId(R.id.sortBySpotRating)).check(matches(isDisplayed()));

    }

}
