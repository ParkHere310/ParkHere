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
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by metzm on 10/16/2016.
 */

public class SearchConnector {

    static SortedMap<Double,ParkingSpot> parkingSpots_distances = new TreeMap<Double,ParkingSpot>();


    public static SortedMap<Double,ParkingSpot> search(final double longitude, final double latitude,
                                                final long time) {
        parkingSpots_distances.clear();


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.child("ParkingSpot").orderByChild("longitude").startAt(longitude - 0.36).endAt(longitude + 0.36);


        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Location source = new Location("");
                source.setLongitude(longitude);
                source.setLatitude(latitude);

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ParkingSpot ps = snapshot.getValue(ParkingSpot.class);
                    if(ps.getTimeWindow().startDateTime < time && ps.getTimeWindow().endDateTime > time
                            && ps.getLatitude() > latitude - 0.36 && ps.getLatitude() < latitude + 0.36) {
                        Location tmp = new Location("");
                        tmp.setLatitude(ps.getLatitude());
                        tmp.setLongitude(ps.getLongitude());
                        double distance = source.distanceTo(tmp);
                        parkingSpots_distances.put(distance,snapshot.getValue(ParkingSpot.class));
                        System.out.println(snapshot.getValue(ParkingSpot.class) + " " + distance);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return parkingSpots_distances;
    }


}
