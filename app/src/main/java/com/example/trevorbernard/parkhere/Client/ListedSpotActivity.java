package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.Date;

/**
 * Created by Hexi on 2016/10/20.
 */

public class ListedSpotActivity extends Activity {
    private TextView title;
    private TextView date;
    private TextView startTime;
    private TextView endTime;
    private TextView type;
    private TextView covered;
    private TextView handicapped;

    private Button editButton;
    private Button removeButton;
    private String spotUID;
    private Query queryRef;



    private static final String TAG = "ListedSpotActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listedspot);




        initiateVariable();

        CreateOnclickCallback();
    }


    private void initiateVariable() {
        title = (TextView)findViewById(R.id.title);
        date = (TextView)findViewById(R.id.actual_date);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        type = (TextView) findViewById(R.id.isType);
        handicapped = (TextView) findViewById(R.id.isHandicapped);
        covered = (TextView) findViewById(R.id.isCovered);

        editButton = (Button) findViewById(R.id.editButton);
        removeButton = (Button) findViewById(R.id.removeButton);

        /*
        do a bunch of setTexts with the textviews below using the info in the pertinent
        object
         */
        Intent previousIntent = this.getIntent();
        spotUID = previousIntent.getExtras().getString("spotUID");
        System.out.println(spotUID);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        queryRef = mDatabase.orderByChild("uid").equalTo(spotUID);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    setText(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void CreateOnclickCallback() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ListedSpotActivity.this, EditReservationActivity.class);
                myIntent.putExtra("spot", spotUID);
                ListedSpotActivity.this.startActivity(myIntent);
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotConnector.removeSpot(spotUID);
                Intent myIntent = new Intent(ListedSpotActivity.this, MainActivity.class);
                ListedSpotActivity.this.startActivity(myIntent);
            }
        });
    }

    private void setText(ParkingSpot mSpot) {
        //System.out.println("Making GUI");
        title.setText(mSpot.getName());
        Date start = new Date(mSpot.getTimeWindow().getStartDateTime());
        Date end = new Date(mSpot.getTimeWindow().getEndDateTime());
        date.setText(start.getMonth()+ "/" + start.getDate() + "/" + start.getYear());
        startTime.setText(start.getHours() + ":" + start.getMinutes());
        endTime.setText(end.getHours() + ":" + end.getMinutes());
    }


}
