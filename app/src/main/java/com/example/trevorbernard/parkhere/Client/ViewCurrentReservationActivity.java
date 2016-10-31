package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

import static com.example.trevorbernard.parkhere.R.id.listView;

/**
 * Created by junseob on 10/29/16.
 */

public class ViewCurrentReservationActivity extends Activity {


    private Query queryRef, queryRef2;
    private ArrayList<ParkingSpotReservation> parkingSpotReservations;
    private ListView list;


    private static final String TAG = "ViewCurrentReservationActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcurrentreservation);

        initiateVariable();
        CreateOnclickCallback();
    }


    private void initiateVariable() {
        list = (ListView) findViewById(listView);
        parkingSpotReservations = new ArrayList<ParkingSpotReservation>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Reservations");
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

    private void CreateOnclickCallback() {
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
                ArrayAdapter<ParkingSpotReservation> arrayAdapter = new ParkingSpotReservationAdapter(ViewCurrentReservationActivity.this, parkingSpotReservations);
                list.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
