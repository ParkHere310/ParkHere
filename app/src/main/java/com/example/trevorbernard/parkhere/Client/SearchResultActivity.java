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
import android.widget.Button;
import android.widget.ListView;

import com.example.trevorbernard.parkhere.Connectors.UserConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotAdapter;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotDistance;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Date;
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
    String filterType = "-1";

    Long startLongFromIntent;
    Long endLongFromIntent;

    Date startDateFromIntent;
    Date endDateFromIntent;


    Button filterButton;

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

        startLongFromIntent = previousIntent.getExtras().getLong("startDateLong");
        endLongFromIntent = previousIntent.getExtras().getLong("endDateLong");

        Date startDateFromIntent = new Date(startLongFromIntent);
        Date endDateFromIntent = new Date(endLongFromIntent);

        /*
        startTime = previousIntent.getExtras().getString("startTime");
        endTime = previousIntent.getExtras().getString("endTime");
        startDate = previousIntent.getExtras().getString("startDate");
        endDate = previousIntent.getExtras().getString("endDate");
        */


        if(previousIntent.getExtras().containsKey("filter")) {
            filterType = previousIntent.getExtras().getString("filter");
        }

        filterButton = (Button) findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SearchResultActivity.this, FilterActivity.class);
                myIntent.putExtra("address", address);
                myIntent.putExtra("startTime", startTime);
                myIntent.putExtra("endTime", endTime);
                myIntent.putExtra("startDate", startDate);
                myIntent.putExtra("endDate", endDate);
                SearchResultActivity.this.startActivity(myIntent);
            }
        });


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
                        if(startDateFromIntent.compareTo(new Date(spot.getTimeWindow().getStartDateTime())) >= 0
                                && startDateFromIntent.compareTo(new Date(spot.getTimeWindow().getEndDateTime())) < 0
                                && endDateFromIntent.compareTo(new Date(spot.getTimeWindow().getEndDateTime())) < 0
                                && endDateFromIntent.compareTo(new Date(spot.getTimeWindow().getStartDateTime())) > 0
                                && spot.getOccupantUID().equals("-1")) {

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

                // Sort here
                if(filterType.equals("sortByPrice")) {

                } else if(filterType.equals("sortBySpotRating")) {

                } else if(filterType.equals("sortByOwnerRating")) {
                    
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
                myIntent.putExtra("spotUID", spotUID);
                myIntent.putExtra("distance", distance);
                SearchResultActivity.this.startActivity(myIntent);
            }
        });



    }

    private ArrayList<ParkingSpot> sortByPrice(ArrayList<ParkingSpot> spotList) {
        Collections.sort(spotList, new Comparator<ParkingSpot>() {
            @Override
            public int compare(ParkingSpot o1, ParkingSpot o2) {
                return new Integer(o1.getPrice()).compareTo(o2.getPrice());
            }
        });
        return spotList;
    }

    private ArrayList<ParkingSpot> sortBySpotRating(ArrayList<ParkingSpot> spotList) {
        Collections.sort(spotList, new Comparator<ParkingSpot>() {
            @Override
            public int compare(ParkingSpot o1, ParkingSpot o2) {
                return new Double(o1.getRating().calculateRating()).compareTo(o2.getRating().calculateRating());
            }
        });
        return spotList;
    }

    private ArrayList<ParkingSpot> sortByOwnerRating(ArrayList<ParkingSpot> spotList) {

        Collections.sort(spotList, new Comparator<ParkingSpot>() {
            @Override
            public int compare(ParkingSpot o1, ParkingSpot o2) {
                User u1 = UserConnector.getUserFromUID(o1.getOwnerUID());
                User u2 = UserConnector.getUserFromUID(o2.getOwnerUID());
                return new Double(u1.getRating().calculateRating()).compareTo(u2.getRating().calculateRating());
            }
        });
        return spotList;
    }

    public User getUserFromUID(String uID) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        final ArrayList<User> list = new ArrayList<User>();
        Query queryRef = mDatabase.orderByChild("UID").equalTo(uID); //test email address
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    User user = postSnapshot.getValue(User.class);
                    list.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // This is really shitty code and I'm sorry i wrote it.
        while(list.isEmpty()) {
            try {
                Thread.sleep(5);
            } catch(Exception e) {

            }
        }
        return list.get(0);
    }

}
