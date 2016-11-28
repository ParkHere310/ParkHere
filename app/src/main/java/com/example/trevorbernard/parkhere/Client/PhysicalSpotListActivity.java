package com.example.trevorbernard.parkhere.Client;

/**
 * Created by junseob on 10/27/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


public class PhysicalSpotListActivity extends Activity {
    private Query queryRef;
    private ArrayList<PhysicalSpotDistance> physicalSpotDistances;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physicalspotlist);
        physicalSpotDistances = new ArrayList<PhysicalSpotDistance>();
        initiateVariables();

        //populateListView();

        createClickCallback();
    }

    private void createClickCallback() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(PhysicalSpotListActivity.this, PhysicalListedSpotActivity.class);
                String spotUID = physicalSpotDistances.get(position).parkingSpot.getUID();
                myIntent.putExtra("spotUID", spotUID);
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
        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView clickedTextView = (TextView) viewClicked;
                //Toast.makeText(PhysicalSpotListActivity.this,
                 //       "I just clicked on: " + clickedTextView.toString(),Toast.LENGTH_LONG).show();
            }
        });*/

        //final ArrayList<ParkingSpot> spotsList = new ArrayList<ParkingSpot>();
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
                ArrayAdapter<PhysicalSpotDistance> arrayAdapter = new PhysicalSpotAdapter(PhysicalSpotListActivity.this, physicalSpotDistances);
                list.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
