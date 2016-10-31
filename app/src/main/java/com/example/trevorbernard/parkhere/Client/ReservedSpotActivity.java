package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.Connectors.TransactionConnector;
import com.example.trevorbernard.parkhere.Connectors.UserConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by junseob on 10/29/16.
 */

public class ReservedSpotActivity extends Activity {
    private Button cancelButton;
    private Button checkinButton;
    private TextView title;
    private TextView date;
    private TextView startTime;
    private TextView endTime;
    private TextView description;
    private TextView address;
    private TextView price;
    private TextView type; // TODO: set these
    private TextView covered;
    private TextView handicapped;

    Query queryRef;
    Query queryRef2;
    String reservationUID;
    Reservation mReservation;
    ParkingSpot mParkingSpot;



    private static final String TAG = "ReservedSpotActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcurrentreservation);




        initiateVariable();

        CreateOnclickCallback();
    }


    private void initiateVariable() {
        cancelButton = (Button)findViewById(R.id.UploadReviewButton);
        checkinButton = (Button) findViewById(R.id.checkinButton);

        /*
        do a bunch of setTexts with the textviews below using the info in the pertinent
        object
         */

        title = (TextView)findViewById(R.id.title);
        date = (TextView)findViewById(R.id.actual_date);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        type = (TextView) findViewById(R.id.isType);
        price = (TextView) findViewById(R.id.actual_price);
        handicapped = (TextView) findViewById(R.id.isHandicapped);
        covered = (TextView) findViewById(R.id.isCovered);
        description = (TextView) findViewById(R.id.actual_description);
        address = (TextView) findViewById(R.id.address_label);

        Intent myIntent = this.getIntent();
        reservationUID = myIntent.getStringExtra("reservationID");

        //Get RESERVATION from specific RESERVATION ID
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("PastReservations");
        queryRef = mDatabase.orderByChild("UID").equalTo(reservationUID);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Reservation res = postSnapshot.getValue(Reservation.class);
                    setText(res);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void CreateOnclickCallback() {


        //When uploadVerificationButton is clicked
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionConnector.cancelReservation(reservationUID,mParkingSpot);
                // Put code to return occupantID to "-1" here

            }
        });

        checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserConnector.checkIn(mReservation);
            }
        });
    }

    public void setText(Reservation mReservation) {
        this.mReservation = mReservation;
        //Get ParkingSpot from specific ParkingSpot ID
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().getRoot().child("ParkingSpots");
        queryRef2 = mDatabase.orderByChild("UID").equalTo(mReservation.getParkingSpotUID());
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    setText2(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setText2(ParkingSpot mSpot) {
        mParkingSpot = mSpot;
        title.setText(mSpot.getAddress());
        Date start = new Date(mSpot.getTimeWindow().getStartDateTime());
        Date end = new Date(mSpot.getTimeWindow().getEndDateTime());
        date.setText(start.getDate());
        startTime.setText(start.getHours() + ":" + start.getMinutes());
        endTime.setText(end.getHours() + ":" + end.getMinutes());
        price.setText(mSpot.getPrice());
        description.setText(mSpot.getDescription());
        address.setText(mSpot.getDescription());
    }
}
