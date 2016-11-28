package com.example.trevorbernard.parkhere.Client;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotAdapter;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotDistance;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.example.trevorbernard.parkhere.Reservation.ReservationAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViewPaymentsActivity extends Activity {
    private Query queryRef;
    private ArrayList<Reservation> pastReservations;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlistings);
        pastReservations = new ArrayList<Reservation>();
        initiateVariables();

        //populateListView();


    }





    private void initiateVariables() {
        list = (ListView) findViewById(R.id.listView);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("PastReservations");
        queryRef = mDatabase.orderByChild("ownerUID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()); // limited to 10
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pastReservations.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    System.out.println("asdfsafd");
                    Reservation res = postSnapshot.getValue(Reservation.class);
                    pastReservations.add(res);
                }
                ArrayAdapter<Reservation> arrayAdapter = new ReservationAdapter(ViewPaymentsActivity.this, pastReservations);
                list.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
