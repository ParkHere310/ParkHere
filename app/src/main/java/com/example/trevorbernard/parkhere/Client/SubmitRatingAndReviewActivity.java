package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.User.User;

/**
 * Created by Hexi on 2016/10/23.
 */

public class SubmitRatingAndReviewActivity extends Activity{

    Button submitButton;
    EditText EditTextComment;
    EditText EditTextRating;
    String comment;
    int rating;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitratingandreview);

        initiateVariable();
        createClickCallback();
    }

    private void createClickCallback() {
        EditTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comment = EditTextComment.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        EditTextRating.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rating = Integer.parseInt(EditTextRating.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SubmitRatingAndReviewActivity.this, MainActivity.class);
                SubmitRatingAndReviewActivity.this.startActivity(myIntent);
            }
        });
    }

    private void initiateVariable() {
        submitButton = (Button) findViewById(R.id.SubmitButton);
        EditTextComment = (EditText) findViewById(R.id.CommentText);
        EditTextRating = (EditText) findViewById(R.id.RatingText);
    }



    public static boolean addReviewToSpot(ParkingSpot reviewed, String rev);

    public static boolean addReviewToUser(User reviewed, String rev);

    //ask iman if need help

    //for user
    //add ONLY review
    public void addUserRatingFromGUI(int num){

    }
    //add rating with Review
    public void addUserRatingReviewFromGUI() {

    }

    //for spot
    //add ONLY review
    public void addSpotRatingFromGUI(int num){

    }
    //add rating with Review
    public void addSpotRatingReviewFromGUI() {

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
