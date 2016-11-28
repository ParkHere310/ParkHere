package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotAdapter;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotDistance;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpotAdapter;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpotDistance;
import com.example.trevorbernard.parkhere.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zilongxiao on 11/27/16.
 */

public class PostSpotHelperActivity extends Activity{

    private Button newPhysicalSpotButton;
    private ListView myPhysicalSpotListView;
    private ArrayList<PhysicalSpotDistance> physicalSpotDistances;
    private Query queryRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postspothelper);

        initiateVariable();

        createOnclickCallback();
    }

    private void initiateVariable() {
        newPhysicalSpotButton = (Button) findViewById(R.id.NewPhysicalSpotButton);
        myPhysicalSpotListView = (ListView) findViewById(R.id.MyPhysicalSpotListView);
        physicalSpotDistances = new ArrayList<PhysicalSpotDistance>();

        //fetch data into an array
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        queryRef = mDatabase.child("PhysicalSpots").orderByChild("ownerUID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()); // limited to 10
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                physicalSpotDistances.clear();
                double count = 1;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    System.out.println("asdfsafd");
                    PhysicalSpot spot = postSnapshot.getValue(PhysicalSpot.class);
                    //spotsList.add(spot);
                    PhysicalSpotDistance psd = new PhysicalSpotDistance();
                    psd.parkingSpot = spot;
                    psd.distance = count;
                    physicalSpotDistances.add(psd);
                    count++;

                }

                //populate
                ArrayAdapter<PhysicalSpotDistance> arrayAdapter = new PhysicalSpotAdapter(PostSpotHelperActivity.this, physicalSpotDistances);
                myPhysicalSpotListView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    private void createOnclickCallback() {
        myPhysicalSpotListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(PostSpotHelperActivity.this, PostSpotActivity.class);
                String spotUID = physicalSpotDistances.get(position).parkingSpot.getUID();
                myIntent.putExtra("physicalSpotUID", spotUID);
                PostSpotHelperActivity.this.startActivity(myIntent);
            }
        });

        newPhysicalSpotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PostSpotHelperActivity.this, PostSpotActivity.class);
                myIntent.putExtra("physicalSpotUID","newSpot");
                PostSpotHelperActivity.this.startActivity(myIntent);
            }
        });
    }
}
