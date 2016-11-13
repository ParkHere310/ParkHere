package com.example.trevorbernard.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.trevorbernard.parkhere.Client.EditReservationActivity;
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
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by junseob on 11/6/16.
 */

@RunWith(AndroidJUnit4.class)
public class EditUnitTest {

    @Rule
    public ActivityTestRule<EditReservationActivity> mActivityRule = new ActivityTestRule<>(
            EditReservationActivity.class, false, false);

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
    public void testEditReservation() throws InterruptedException {
/*
        Intent i = new Intent();
        i.putExtra("title_field", "Example title");

        mActivityRule.launchActivity(i);
        //onView(withId(R.id.postButton)).perform(scrollTo()).perform(closeSoftKeyboard()).perform(click());

        onView(withId(R.id.title_field)).check(matches(isDisplayed()));
*/
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
        Intent i = new Intent();
        i.putExtra("title_field", "Example title");

        mActivityRule.launchActivity(i);
        onView(withId(R.id.title_field)).check(matches(isDisplayed()));

    }
}
