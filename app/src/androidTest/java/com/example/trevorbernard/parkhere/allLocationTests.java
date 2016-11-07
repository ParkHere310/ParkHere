package com.example.trevorbernard.parkhere;

import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by imanz on 11/6/16.
 */

public class allLocationTests {

    double latitude;
    double longitude;

    public void setUp() {
        latitude = -1;
        longitude = -1;
    }


    //this is testing if we can enter an address
    //this is also testing if we can enter a us address
    //in this test, we enter one of our group members home address which is
    //in los angeles, ca; specifically at 1256 West Adams Boulevard, Los Angeles, CA.
    //we initially set the latitude and longitude to be equal to -1, then check if it matches
    //the expected output.

    @Test
    public void testLocations() throws Exception {
        double testLatitude = 0;
        double testLongitude = 0;
        try {
            String address = "1256 West Adams Boulevard, Los Angeles, CA";
            Geocoder geocoder = new Geocoder(null);
            List<Address> codedAddress = geocoder.getFromLocationName(address,5);
            latitude = codedAddress.get(0).getLatitude();
            longitude = codedAddress.get(0).getLongitude();

            testLatitude = codedAddress.get(0).getLatitude();
            testLongitude = codedAddress.get(0).getLongitude();

        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(testLatitude, latitude);

    }


    //this is testing that we can enter longitude and latitude
    //in this we set the address equal to the latitude and longitude,
    //then it gets converted to a street address
    //then we pull back the longitude and latitude.
    @Test
    public void testLongLat() throws Exception {
        double testLatitude = 34.0224;
        double testLongitude = 118.2851;
        try {
            String address = "34.0224° N, 118.2851° W";
            Geocoder geocoder = new Geocoder(null);
            List<Address> codedAddress = geocoder.getFromLocationName(address,5);
            latitude = codedAddress.get(0).getLatitude();
            longitude = codedAddress.get(0).getLongitude();


        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(testLatitude, latitude);

    }

}
