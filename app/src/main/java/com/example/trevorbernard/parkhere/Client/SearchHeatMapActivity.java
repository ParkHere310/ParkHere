package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.ViewGroup;
import android.graphics.Color;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotAdapter;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotDistance;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotReservation;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotReservationAdapter;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpotAdapter;
import com.example.trevorbernard.parkhere.ParkingSpot.PhysicalSpotDistance;
import com.example.trevorbernard.parkhere.ParkingSpot.HeatMapAdapter;
import com.example.trevorbernard.parkhere.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by zilongxiao on 11/27/16.
 */

public class SearchHeatMapActivity extends Activity{

    private ListView heatMapListView;
    Query queryRef;
    ArrayList<PhysicalSpot> physicalSpots;
    ArrayList<String> physicalSpotNames;
    double longitude;
    double latitude;
    String address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchheatmap);

        initiateVariables();
        CreateOnlickCallback();
    }

    private void initiateVariables() {
        //Instantiate variables and Arraylists
        heatMapListView = (ListView) findViewById(R.id.HeatMapListView);
        physicalSpots = new ArrayList<PhysicalSpot>();
        physicalSpotNames = new ArrayList<String>();
        Intent previousIntent = getIntent();
        address = previousIntent.getExtras().getString("address");

        //find the longitude and latitude of the address
        Location source = new Location("");
        Locale current = getResources().getConfiguration().locale;
        Geocoder geocoder = new Geocoder(this,current);
        List<Address> codedAddress;
        try {
            System.out.println(address);
            codedAddress = geocoder.getFromLocationName(address,5);
            if(codedAddress.size() == 0) {
                System.out.println("didnt code address");
                latitude = 34;
                longitude = -118;
            } else {
                latitude = codedAddress.get(0).getLatitude();
                longitude = codedAddress.get(0).getLongitude();
            }
        } catch (IOException ioe) {
            System.out.println("IOE Exception in geocoding address");
            ioe.printStackTrace();
        }

        //look for physical spots that are close to that location
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        queryRef = mDatabase.child("PhysicalSpots").orderByChild("longitude").startAt(longitude - 0.36).endAt(longitude + 0.36);
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                physicalSpots.clear();
                Location source = new Location("");
                source.setLongitude(longitude);
                source.setLatitude(latitude);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //if the spot is within this range
                    PhysicalSpot spot = (snapshot.getValue(PhysicalSpot.class));
                    if(spot.getLatitude() >= latitude-0.36 && spot.getLatitude() <= latitude + 0.36) {
                        physicalSpots.add(spot);
                        physicalSpotNames.add(spot.getName() + "\n AT ADDRESS: " + spot.getAddress());
                    }
                }

               // ArrayAdapter arrayAdapter = new ArrayAdapter<String>(SearchHeatMapActivity.this,android.R.layout.simple_list_item_1,physicalSpotNames);

                ArrayAdapter<PhysicalSpot> arrayAdapter = new HeatMapAdapter(SearchHeatMapActivity.this, physicalSpots);

                heatMapListView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void CreateOnlickCallback() {
        heatMapListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(SearchHeatMapActivity.this, PhysicalListedSpotActivity.class);
                String spotUID = physicalSpots.get(position).getUID();
                myIntent.putExtra("spotUID", spotUID);
                myIntent.putExtra("editable",false);
                SearchHeatMapActivity.this.startActivity(myIntent);
            }
        });
    }

}
