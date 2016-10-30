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
import android.widget.TextView;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotAdapter;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotDistance;
import com.example.trevorbernard.parkhere.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hexi on 2016/10/23.
 */

public class SearchResultActivity extends Activity {
    ListView listView;
    DatabaseReference mDatabase;
    Query queryRef;
    double longitude;
    double latitude;

    String address;
    String startTime;
    String endTime;
    String startDate;
    String endDate;

    ArrayList<ParkingSpotDistance> parkingSpotDistances;
    ArrayList<ParkingSpot> parkingSpots;
    ArrayList<String> stringSpots;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);

        initiateVariables();
        populateParkingSpots();
        CreateOnlickCallback();
    }
    private void initiateVariables() {
        //Retrieve information passed from previous Intent
        Intent previousIntent = this.getIntent();
        address = previousIntent.getExtras().getString("address");
        startTime = previousIntent.getExtras().getString("startTime");
        endTime = previousIntent.getExtras().getString("endTime");
        startDate = previousIntent.getExtras().getString("startDate");
        endDate = previousIntent.getExtras().getString("endDate");

        //Set up list view
        listView = (ListView) findViewById(R.id.list);
        Location source = new Location("");
        Locale current = getResources().getConfiguration().locale;
        Geocoder geocoder = new Geocoder(this,current);
        List<Address> codedAddress;
        try {
            System.out.println(address);
            codedAddress = geocoder.getFromLocationName(address,5);
            if(codedAddress == null) {
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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        parkingSpots = new ArrayList<ParkingSpot>();
        stringSpots = new ArrayList<String>();
        parkingSpotDistances = new ArrayList<ParkingSpotDistance>();
    }

    private void populateParkingSpots() {

        queryRef = mDatabase.child("ParkingSpots").orderByChild("longitude").startAt(longitude - 0.36).endAt(longitude + 0.36);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                parkingSpotDistances.clear();

                Location source = new Location("");
                source.setLongitude(longitude);
                source.setLatitude(latitude);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ParkingSpot spot = (snapshot.getValue(ParkingSpot.class));

                    //if the spot is within this range
                    if(spot.getLatitude() >= latitude-0.36 && spot.getLatitude() <= latitude + 0.36) {

                        //if the spot is within time, and unoccupied
                        /*if(time2Start.compareTo(new Date(spot.getTimeWindow().getStartDateTime())) >= 0
                                && time2Start.compareTo(new Date(spot.getTimeWindow().getEndDateTime())) < 0
                                && time2End.compareTo(new Date(spot.getTimeWindow().getEndDateTime())) < 0
                                && time2End.compareTo(new Date(spot.getTimeWindow().getStartTime())) > 0
                                && spot.getOccupantUID().equals("-1"))*/ {

                            Location spotloc = new Location("");
                            spotloc.setLongitude(spot.getLongitude());
                            spotloc.setLatitude(spot.getLatitude());
                            Double distance = (double) spotloc.distanceTo(source); // in meters
                            distance = toMiles(distance);

                            ParkingSpotDistance psd = new ParkingSpotDistance();
                            psd.parkingSpot = spot;
                            psd.distance = distance;
                            parkingSpotDistances.add(psd);

                            //parkingSpots.add(spot);
                            //stringSpots.add(new DecimalFormat("#.##").format(distance) + " miles " + spot.getName() + ": " + spot.getAddress());
                        }
                    }
                }

                ArrayAdapter<ParkingSpotDistance> arrayAdapter = new ParkingSpotAdapter(SearchResultActivity.this, parkingSpotDistances);
                //ArrayAdapter arrayAdapter = new ArrayAdapter<String>(SearchResultActivity.this,android.R.layout.simple_list_item_1,stringSpots);
                listView.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public static double toMiles(double meters)
    {
        return (meters / 1609.344);
    }


    //This is the void called on selection of a parking spot item
    private void CreateOnlickCallback() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                Intent myIntent = new Intent(SearchResultActivity.this, RentSpotActivity.class);
                String spotUID = parkingSpotDistances.get(position).parkingSpot.getUID();
                String distance = new DecimalFormat("#.#").format(parkingSpotDistances.get(position).distance);
                SearchResultActivity.this.startActivity(myIntent);
                myIntent.putExtra("spotUID", spotUID);
                myIntent.putExtra("distance", distance);
            }
        });


        /*
        DECIDE WHAT TO PASS TO THE RENT SPOT ACTIVITY

        myIntent.putExtra("address",p.getAddress());
        myIntent.putExtra("price",p.getPrice());
        myIntent.putExtra("description",p.getDescription());
        myIntent.putExtra("name",p.getName());
        myIntent.putExtra("uid",p.getUID());
        myIntent.putExtra("owneruid",p.getOwnerUID())
        */



    }
}
