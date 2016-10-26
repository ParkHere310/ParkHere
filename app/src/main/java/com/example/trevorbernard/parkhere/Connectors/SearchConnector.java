package com.example.trevorbernard.parkhere.Connectors;

import android.location.Location;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Date;

/**
 * Created by metzm on 10/16/2016.
 */

public class SearchConnector {

    public static SortedMap<Double, ParkingSpot> search(final double longitude, final double latitude, Date time) {

        final SortedMap<Double,ParkingSpot> parkingSpotsByDistance = new TreeMap<Double,ParkingSpot>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.child("ParkingSpot").orderByChild("longitude").startAt(longitude - 0.36).endAt(longitude + 0.36);
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
