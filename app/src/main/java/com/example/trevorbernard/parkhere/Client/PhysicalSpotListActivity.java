package com.example.trevorbernard.parkhere.Client;

/**
 * Created by junseob on 10/27/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotDistance;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpot;
import com.example.trevorbernard.parkhere.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PhysicalSpotListActivity extends Activity {
    private Query queryRef;
    private ArrayList<ParkingSpotDistance> parkingSpotDistances;
    private ListView list;
    private Button newButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicalspotlist);
        parkingSpotDistances = new ArrayList<ParkingSpotDistance>();
        initiateVariables();

        //populateListView();

        createClickCallback();
    }

    private void createClickCallback() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*
                Intent myIntent = new Intent(ViewListingsActivity.this, ListedSpotActivity.class);
                String spotUID = parkingSpotDistances.get(position).parkingSpot.getUID();
                myIntent.putExtra("spotUID", spotUID);
                ViewListingsActivity.this.startActivity(myIntent);
                */
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PhysicalSpotListActivity.this, PhysicalSpot.class);
                PhysicalSpotListActivity.this.startActivity(myIntent);
            }
        });

    }

    //ONLY FOR TESTING PURPOSES
    /*private void populateListView() {
        String[] mReservations = {"Reservation Address 1","Reservation Address 2",
                "Reservation Address3 "};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.layout_reservationitem, mReservations);
        ListView list = (ListView) findViewById(listView);
        list.setAdapter(adapter);
    }*/


    private void initiateVariables() {
        list = (ListView) findViewById(R.id.listView);
        newButton = (Button) findViewById(R.id.new_button);
        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView clickedTextView = (TextView) viewClicked;
                Toast.makeText(ViewListingsActivity.this,
                        "I just clicked on: " + clickedTextView.toString(),Toast.LENGTH_LONG).show();
            }
        });*/

        //final ArrayList<ParkingSpot> spotsList = new ArrayList<ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        queryRef = mDatabase.orderByChild("ownerUID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()); // limited to 10
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkingSpotDistances.clear();
                double count = 1;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    System.out.println("asdfsafd");
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    //spotsList.add(spot);
                    ParkingSpotDistance psd = new ParkingSpotDistance();
                    psd.parkingSpot = spot;
                    psd.distance = count;
                    parkingSpotDistances.add(psd);
                    count++;

                }
                //ArrayAdapter<ParkingSpotDistance> arrayAdapter = new ParkingSpotAdapter(ViewListingsActivity.this, parkingSpotDistances);
                //list.setAdapter(arrayAdapter);
                //arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
