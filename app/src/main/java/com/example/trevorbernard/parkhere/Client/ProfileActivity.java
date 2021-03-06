package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hexi on 2016/10/23.
 Only show user Profile. No Parking Spot Profile is shown.
 */



public class ProfileActivity extends Activity {
    String ownerUID;
    User owner;
    TextView ownerNameTextView;
    ImageView ownerProfileImageView;
    RatingBar ownerRatingBar;
    ListView ownerReviewList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initiateVariables();
    }
    private void initiateVariables() {
        ownerUID = ProfileActivity.this.getIntent().getStringExtra("ownerUID");


        ownerNameTextView = (TextView) findViewById(R.id.OwnerNameTextView);
        ownerProfileImageView = (ImageView) findViewById(R.id.OwnerProfileImage);
        ownerRatingBar = (RatingBar) findViewById(R.id.OwnerRating);
        ownerReviewList = (ListView) findViewById(R.id.OwnerReviewList);
        //set up rating bar color
        LayerDrawable stars = (LayerDrawable) ownerRatingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);

        ownerRatingBar.setEnabled(false);

        retrieveOwner(ownerUID);
    }

    private void retrieveOwner(String ownerUID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Query queryRef = mDatabase.orderByChild("UID").equalTo(ownerUID); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    owner = postSnapshot.getValue(User.class);
                    setText(owner);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setText(User owner) {
        ownerNameTextView.setText(owner.getFirstName() + " " + owner.getLastName());
        //ownerProfileImageView.setProfilePicture();
        ownerRatingBar.setRating((float)owner.getRating().calculateRating());
        List<String> ownerReviews =  owner.getReviews();
        if(ownerReviews == null) {
            ownerReviews = new ArrayList<String>();
            ownerReviews.add("No Reviews");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, ownerReviews);
        ownerReviewList.setAdapter(adapter);
    }

}
