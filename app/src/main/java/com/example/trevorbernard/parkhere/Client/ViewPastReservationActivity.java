package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.trevorbernard.parkhere.Connectors.UserConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotReservation;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotReservationAdapter;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
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
 * Created by Hexi on 2016/10/22.
 */

public class ViewPastReservationActivity extends Activity {

    private ListView list;
    private Query queryRef, queryRef2;
    private ArrayList<ParkingSpotReservation> parkingSpotReservations;
   // String[] mReservations = {"Reservation ID 1","Reservation ID 2",
    //        "Reservation ID 3"}; // FOR TESTING PURPOSE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpastreservation);
        initiateVariables();
        //populateListView();
        CreateOnclickCallback();
    }

    private void initiateVariables() {
        list = (ListView) findViewById(R.id.listView);
        parkingSpotReservations = new ArrayList<ParkingSpotReservation>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("PastReservations");
        queryRef = mDatabase.orderByChild("ownerUID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()); // limited to 10
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkingSpotReservations.clear();
                double count = 1;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Reservation res = postSnapshot.getValue(Reservation.class);
                    //spotsList.add(spot);
                    ParkingSpotReservation psr = new ParkingSpotReservation();
                    psr.reservation = res;
                    parkingSpotReservations.add(psr);
                    getSpots(psr, parkingSpotReservations);


                    /*ArrayAdapter<ParkingSpotDistance> arrayAdapter = new ParkingSpotAdapter(ViewCurrentReservationActivity.this, parkingSpotDistances);
                    //ArrayAdapter arrayAdapter = new ArrayAdapter<String>(SearchResultActivity.this,android.R.layout.simple_list_item_1,stringSpots);
                    list.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();*/
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //ONLY FOR TESTING PURPOSES
    /*private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, mReservations);
        list.setAdapter(adapter);
     }*/

    private void CreateOnclickCallback() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(ViewPastReservationActivity.this, PastReservedSpotActivity.class);
                String spotUID = parkingSpotReservations.get(position).reservation.getUID();
                myIntent.putExtra("reservationID", spotUID);
                ViewPastReservationActivity.this.startActivity(myIntent);
            }
        });
    }

    private List<Reservation> getPastResercationList() {
        return UserConnector.getPastReservationsforUserUID(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());
    }

    // This function return the current reservation list. Use it in the current reservation list activity
    private List<Reservation> getResercationList() {
        return UserConnector.getReservationsforUserUID(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());
    }

    private void getSpots(final ParkingSpotReservation psr, final ArrayList<ParkingSpotReservation> parkingSpotReservations) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        queryRef2 = mDatabase.orderByChild("seekerUID").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()); // limited to 10
        queryRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double count = 1;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    psr.parkingSpot = spot;
                    parkingSpotReservations.add(psr);

                }
                ArrayAdapter<ParkingSpotReservation> arrayAdapter = new ParkingSpotReservationAdapter(ViewPastReservationActivity.this, parkingSpotReservations);
                list.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
