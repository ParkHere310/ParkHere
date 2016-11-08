package com.example.trevorbernard.parkhere;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;

import com.example.trevorbernard.parkhere.Client.FilterActivity;
import com.example.trevorbernard.parkhere.Client.PostSpotActivity;
import com.example.trevorbernard.parkhere.Client.SearchResultActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.Object;

/**
 * Created by imanz on 11/6/16.
 */

public class allLocationTests {

    double latitude;
    double longitude;
    String address = "1256 West Adams Boulevard, Los Angeles, CA";


    private Query queryRef, queryRef2, queryRef3, queryRef4, queryRef5;

    @Before
    public void setUp() {
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword("test44@test.com", "test1!").addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull final Task<AuthResult> task) {
                        }
                    }
            );
        }

        latitude = -1;
        longitude = -1;
    }



    @Test
    public void testUSLocations() throws Exception {
        double testLatitude = 0;
        double testLongitude = 0;
        ParkingSpot spot;

            latitude = 34.0224;
            longitude = 118.2851;

            spot = new ParkingSpot( "name",
                    "fake Description", 20, true, true, true, "1256 West Adams Boulevard, Los Angeles, CA", new Date(2016,10,31,00,00), new Date(2016,10,31,20,00),latitude,longitude);

        assert(address == spot.getAddress());
    }

    @Test
    public void testLongLat() throws Exception {
        double testLatitude = 34.0224;
        double testLongitude = 118.2851;

        ParkingSpot spotToadd = new ParkingSpot( "name",
                "fake Description", 20, true, true, true, "1256 West Adams Boulevard, Los Angeles, CA", new Date(2016,10,31,00,00), new Date(2016,10,31,20,00),latitude,longitude);



        assert(testLatitude == spotToadd.getLatitude() && spotToadd.getLongitude() == testLongitude);

    }

    @Test
    public void testDistance3Miles() throws Exception {
        SortedMap<Double, ParkingSpot> ParkingSpotDistances=  search(34.0224, 118.2851, new Date(2016,10,31,00,00));

        boolean allLessThan3 = true;

        for (SortedMap.Entry<Double, ParkingSpot> entry : ParkingSpotDistances.entrySet())
        {
            if(entry.getKey() > 3) {
                allLessThan3 = false;
            }
        }

        assertTrue(allLessThan3);
    }


    @Test
    public void testEnterAddress() throws Exception {
        double testLatitude = 0;
        double testLongitude = 0;
        ParkingSpot spot;

        latitude = 34.0224;
        longitude = 118.2851;

        spot = new ParkingSpot( "name",
                "fake Description", 20, true, true, true, "1256 West Adams Boulevard, Los Angeles, CA", new Date(2016,10,31,00,00), new Date(2016,10,31,20,00),latitude,longitude);

        assert(address == spot.getAddress());
    }

    //function from parking spot connector util
    SortedMap<Double, ParkingSpot> search(final double longitude, final double latitude, Date time) {

        final SortedMap<Double,ParkingSpot> parkingSpotsByDistance = new TreeMap<Double,ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.child("ParkingSpots").orderByChild("longitude").startAt(longitude - 0.36).endAt(longitude + 0.36);
        final Date time2 = new Date(time.getTime());
        final Location source = new Location("");
        source.setLatitude(latitude);
        source.setLongitude(longitude);


        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Location source = new Location("");
                source.setLongitude(longitude);
                source.setLatitude(latitude);

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    ParkingSpot spot = (snapshot.getValue(ParkingSpot.class));
                    if(spot.getLatitude() >= latitude-0.36 && spot.getLatitude() <= latitude + 0.36
                            && time2.compareTo(new Date(spot.getTimeWindow().getStartDateTime())) >= 0
                            && time2.compareTo(new Date(spot.getTimeWindow().getEndDateTime()))<=0
                            && spot.getOccupantUID().equals("-1")) {

                        Location spotloc = new Location("");
                        spotloc.setLongitude(spot.getLongitude());
                        spotloc.setLatitude(spot.getLatitude());
                        double distance = spotloc.distanceTo(source);

                        parkingSpotsByDistance.put(distance, spot);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return parkingSpotsByDistance;
    }

}
