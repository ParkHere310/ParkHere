package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpot;
import com.example.trevorbernard.parkhere.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by junseob on 11/26/16.
 */

public class PhysicalListedSpotActivity extends Activity {
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



    private static final String TAG = "PhysicalListedSpotActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicalspot);




        initiateVariable();

        CreateOnclickCallback();
    }


    private void initiateVariable() {
        title = (TextView)findViewById(R.id.title);

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


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        queryRef = mDatabase.child("PhysicalSpots").orderByChild("uid").equalTo(spotUID);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    PhysicalSpot spot = postSnapshot.getValue(PhysicalSpot.class);
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
                Intent myIntent = new Intent(PhysicalListedSpotActivity.this, EditReservationActivity.class);
                myIntent.putExtra("spot", spotUID);
                PhysicalListedSpotActivity.this.startActivity(myIntent);
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpotConnector.removeSpot(spotUID);
                Intent myIntent = new Intent(PhysicalListedSpotActivity.this, MainActivity.class);
                PhysicalListedSpotActivity.this.startActivity(myIntent);
            }
        });
    }

    private void setText(PhysicalSpot mSpot) {
        //System.out.println("Making GUI");
        title.setText(mSpot.getName());

    }
}
