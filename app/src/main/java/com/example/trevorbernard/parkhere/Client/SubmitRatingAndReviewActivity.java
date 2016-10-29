package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
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
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.User.User;

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
    int userRating;
    int spotRating;

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
                spotComment = EditTextSpotComment.toString();
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
                userComment = EditTextUserComment.toString();
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
                //Submit rating and review to our database
                //TO BE UPDATED
                Intent myIntent = new Intent(SubmitRatingAndReviewActivity.this, MainActivity.class);
                SubmitRatingAndReviewActivity.this.startActivity(myIntent);
            }
        });
    }

    private void initiateVariable() {
        submitButton = (Button) findViewById(R.id.SubmitButton);
        EditTextUserComment = (EditText) findViewById(R.id.CommentUserText);
        EditTextSpotComment = (EditText) findViewById(R.id.CommentSpotText);
        userRatingBar = (RatingBar) findViewById(R.id.UserRatingBar);
        spotRatingBar = (RatingBar) findViewById(R.id.SpotRatingBar);
        userRating = 0;
        spotRating = 0;
    }




    //ask iman if need help

    //for user
    //add ONLY review
    public void addUserRatingFromGUI(User user, int num){

        String userUID = user.getUID();

        UserConnector.addRatingToUser(userUID, num);

    }
    //add rating with Review
    public void addUserRatingReviewFromGUI(User user, int num, String rev) {


        String userUID = user.getUID();


        //add rating
        UserConnector.addRatingToUser(userUID, num);
        //add review
        UserConnector.addReviewToUser(userUID, rev);

    }

    //for spot
    //add ONLY review
    public void addSpotRatingFromGUI(ParkingSpot spot, int num){

        String spotUID = spot.getUID();

        UserConnector.addRatingToUser(spotUID, num);

    }
    //add rating with Review
    public void addSpotRatingReviewFromGUI(ParkingSpot spot, int num, String rev) {

        String spotUID = spot.getUID();

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
