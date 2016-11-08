package com.example.trevorbernard.parkhere;

//import android.support.test.espresso.contrib.PickerActions;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.trevorbernard.parkhere.Client.PostSpotActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;


/**
 * Created by junseob on 11/6/16.
 */

@RunWith(AndroidJUnit4.class)

public class DateAndHourUIUnitTests {
    private Query queryRef;

    @Rule
    public ActivityTestRule<PostSpotActivity> mActivityRule = new ActivityTestRule<>(
            PostSpotActivity.class, false, false);

    @Test
    public void testStartDate() throws InterruptedException {
        DateAndTime dAndT = new DateAndTime();
        int year = 2016;
        int month = 11;
        int day = 15;

        // onView(withId(R.id.endDatePicker.getId())).perform(click());
        //   onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        //

        // DatePicker endDateP = R.id.endDatePicker;
        //

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
        //i.putExtra("title_field", "Example title");

        mActivityRule.launchActivity(i);
        onView(withId(R.id.startDatePicker)).perform(scrollTo()).perform(click());
        // onView(withId(R.id.postButton)).perform(scrollTo()).perform(click());

        assertTrue("start dates match", (Integer.toString(dAndT.getStartYear()) + "/" + Integer.toString(dAndT.getStartMonth()) + "/" + Integer.toString(dAndT.getStartDay())).
                equals(Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day)));
    }

    @Test
    public void testEndDate() throws InterruptedException {
        DateAndTime dAndT = new DateAndTime();
        int year = 2016;
        int month = 11;
        int day = 15;

       // onView(withId(R.id.endDatePicker.getId())).perform(click());
     //   onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        //

       // DatePicker endDateP = R.id.endDatePicker;
      //

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
        //i.putExtra("title_field", "Example title");

        mActivityRule.launchActivity(i);
        onView(withId(R.id.endDatePicker)).perform(scrollTo()).perform(click());
       // onView(withId(R.id.postButton)).perform(scrollTo()).perform(click());

        assertTrue("end dates match", (Integer.toString(dAndT.getEndYear()) + "/" + Integer.toString(dAndT.getEndMonth()) + "/" + Integer.toString(dAndT.getEndDay())).
                equals(Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(day)));
        //onView(withId(R.id.title_field)).check(matches(isDisplayed()));

    }

    @Test
    public void testStartTime() throws InterruptedException {
        int hour = 9;
        int minutes = 10;

        DateAndTime dAndT = new DateAndTime();


        // onView(withId(R.id.endDatePicker.getId())).perform(click());
        //   onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        //

        // DatePicker endDateP = R.id.endDatePicker;
        //

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
        //i.putExtra("title_field", "Example title");

        mActivityRule.launchActivity(i);
        onView(withId(R.id.startTime_picker)).perform(scrollTo()).perform(click());
        // onView(withId(R.id.postButton)).perform(scrollTo()).perfrm(click());

        assertTrue("start times match", (Integer.toString(dAndT.getStartHour()) + ":" + Integer.toString(dAndT.getStartMin())).
                equals(Integer.toString(hour) + ":" + Integer.toString(minutes)));

    }

    @Test
    public void testEndTime() throws InterruptedException {
        int hour = 10;
        int minutes = 15;

        DateAndTime dAndT = new DateAndTime();


        // onView(withId(R.id.endDatePicker.getId())).perform(click());
        //   onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month + 1, day));
        //

        // DatePicker endDateP = R.id.endDatePicker;
        //

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
        //i.putExtra("title_field", "Example title");

        mActivityRule.launchActivity(i);
        onView(withId(R.id.endTime_picker)).perform(scrollTo()).perform(click());
        // onView(withId(R.id.postButton)).perform(scrollTo()).perfrm(click());

        assertTrue("end times match", (Integer.toString(dAndT.getEndHour()) + ":" + Integer.toString(dAndT.getEndMin())).
                equals(Integer.toString(hour) + ":" + Integer.toString(minutes)));

    }


}