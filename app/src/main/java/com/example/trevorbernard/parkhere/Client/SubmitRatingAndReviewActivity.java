package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.trevorbernard.parkhere.Connectors.UserConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpot;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Hexi on 2016/10/23.
 */

public class SubmitRatingAndReviewActivity extends Activity{

    Button submitButton;
    EditText EditTextUserComment;
    EditText EditTextSpotComment;
    RatingBar userRatingBar;
    RatingBar spotRatingBar;
    String userComment;
    String spotComment;
    String reservationID;
    int userRating;
    int spotRating;
    private Query queryRef, queryRef2, queryRef3;
    private Reservation mRes;
    private ParkingSpot mListing;
    private PhysicalSpot mSpot;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitratingandreview);

        initiateVariable();
        createClickCallback();
    }

    private void createClickCallback() {
        //Update String that keeps track of EditTextSpotComment
        EditTextSpotComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                spotComment = EditTextSpotComment.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Update String that keeps track of EditTextUserComment
        EditTextUserComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userComment = EditTextUserComment.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Update variable that keeps user rating
        userRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userRating = (int) rating;
            }
        });

        //Update variable taht keeps spot rating
        spotRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                spotRating = (int) rating;
            }
        });

        //TO BE UPDATED
        //Direct the page back to the Main Activity
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                queryRef = mDatabase.child("Reservations").orderByChild("uid").equalTo(reservationID);
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Reservation res = postSnapshot.getValue(Reservation.class);
                            retreiveParkingSpot(res);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void retreiveParkingSpot(Reservation res) {
        mRes = res;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        queryRef2 = mDatabase.child("ParkingSpots").orderByChild("uid").equalTo(res.getParkingSpotUID());
        queryRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    retreivePhysicalSpot(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void retreivePhysicalSpot(ParkingSpot spot) {
        mListing = spot;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        queryRef3 = mDatabase.child("PhysicalSpots").orderByChild("uid").equalTo(spot.getPhysicalSpotUID());
        queryRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    PhysicalSpot spot = postSnapshot.getValue(PhysicalSpot.class);
                    finalizePhysicalSpot(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void finalizePhysicalSpot(PhysicalSpot spot) {
        mSpot = spot;

        //Submit rating and review to our database

        if (userRating == 0 || spotRating == 0) {
            //NOT SUBMIT, RETURN WARNING
            return;
        } else {
            //User Rating & Review
            if (userComment.equals("")) {
                addUserRatingFromGUI(mSpot.getOwnerUID(),userRating);
            } else {
                addUserRatingReviewFromGUI(mSpot.getOwnerUID(),userRating,userComment);
            }
            //Spot Rating & Review
            if (spotComment.equals("")) {
                addSpotRatingFromGUI(mSpot.getUID(),spotRating);
            } else {
                addSpotRatingReviewFromGUI(mSpot.getUID(),spotRating,spotComment);
            }
        }
        Intent myIntent = new Intent(SubmitRatingAndReviewActivity.this, MainActivity.class);
        SubmitRatingAndReviewActivity.this.startActivity(myIntent);
    }

    private void initiateVariable() {
        mSpot = null;
        Intent myIntent = this.getIntent();
        //reservationID = myIntent.getStringExtra("reservationID");
        reservationID = myIntent.getExtras().getString("reservationUID");

        submitButton = (Button) findViewById(R.id.SubmitButton);
        EditTextUserComment = (EditText) findViewById(R.id.CommentUserText);
        EditTextSpotComment = (EditText) findViewById(R.id.CommentSpotText);
        userRatingBar = (RatingBar) findViewById(R.id.UserRatingBar);
        spotRatingBar = (RatingBar) findViewById(R.id.SpotRatingBar);
        LayerDrawable stars = (LayerDrawable) userRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        LayerDrawable starss = (LayerDrawable) spotRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        userRating = 0;
        spotRating = 0;
    }




    //ask iman if need help

    //for user
    //add ONLY review
    public void addUserRatingFromGUI(String userUID, int num){

        UserConnector.addRatingToUser(userUID, num);

    }
    //add rating with Review
    public void addUserRatingReviewFromGUI(String userUID, int num, String rev) {


        //add rating
        UserConnector.addRatingToUser(userUID, num);
        //add review
        UserConnector.addReviewToUser(userUID, rev);

    }

    //for spot
    //add ONLY review
    public void addSpotRatingFromGUI(String physicalSpotUID, int num){


        UserConnector.addRatingToSpot(physicalSpotUID, num);

    }
    //add rating with Review
    public void addSpotRatingReviewFromGUI(String spotUID, int num, String rev) {


        //add rating
        UserConnector.addRatingToSpot(spotUID, num);
        //add review
        UserConnector.addReviewToSpot(spotUID, rev);

    }






    //this is for a merge conflict for me and jun
    /* post spot activity
    void postSpotFromGUI(
            String name,
            String description,
            int price,
            boolean isSUV,
            boolean isCovered,
            boolean isHandicap,
            String address
            ) {

        //make new spot
        //the parking spot itself will add the seller user to the class
        ParkingSpot spot = new ParkingSpot( name,
                description, price, isSUV, isCovered, isHandicap, address);

        SpotConnector.postSpot(spot);
    }

     */

}
