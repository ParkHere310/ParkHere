package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.Connectors.TransactionConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.example.trevorbernard.parkhere.Reservation.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Hexi on 2016/10/23.
 */

public class RentSpotActivity extends Activity {

    Button rentButton;
    TextView descriptionTextview;
    TextView priceTextview;

    String distance;
    String spotUID;
    ParkingSpot parkingSpot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentspot);


        initiateVariables();
        createOnclickCallback();

    }

    private void initiateVariables() {
        //Grab info from previous activity
        distance = RentSpotActivity.this.getIntent().getExtras().getString("distance");
        spotUID = RentSpotActivity.this.getIntent().getExtras().getString("spotUID");


        //set up widgets
        rentButton = (Button) findViewById(R.id.RentButton);
        descriptionTextview = (TextView) findViewById(R.id.DescriptionTextView);
        priceTextview = (TextView) findViewById(R.id.PriceTextView);


        /*
        TO BE UPDATED
        */
        //input content into widgets
        populateParkingSpot();
    }

    private void createOnclickCallback() {
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentSpotFromGUI(spotUID);
                Intent myIntent = new Intent (RentSpotActivity.this, MainActivity.class);
                RentSpotActivity.this.startActivity(myIntent);
            }
        });
    }

    private void populateParkingSpot() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        final ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
        Query queryRef = mDatabase.orderByChild("uid").equalTo(spotUID);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    parkingSpot = postSnapshot.getValue(ParkingSpot.class);
                    priceTextview.setText("$ " + parkingSpot.getPrice());
                    descriptionTextview.setText(parkingSpot.getName()+ "\n" + parkingSpot.getAddress()
                            + "\n" + parkingSpot.getDescription());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //iman,
    private void rentSpotFromGUI( String parkingSpotUID) {

        String seekerUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String ownerUID = SpotConnector.getParkingSpotFromUID(parkingSpotUID).getOwnerUID();

        Transaction transaction = null;


        Reservation res = new Reservation(ownerUID, seekerUID,
                parkingSpotUID, transaction);

        TransactionConnector.addReservation(res);
    }

}
