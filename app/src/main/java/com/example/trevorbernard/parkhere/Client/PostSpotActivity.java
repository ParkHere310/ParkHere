package com.example.trevorbernard.parkhere.Client;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;

/**
 * Created by Hexi on 2016/10/20.
 */

public class PostSpotActivity {


    void postSpotFromGUI(
            String name,
            String description,
            int price,
            boolean isSUV,
            boolean isCovered,
            boolean isHandicap,
            String address
            ) {

        //make new spot
        //the parking spot itself will add the seller user to the class
        ParkingSpot spot = new ParkingSpot( name,
                description, price, isSUV, isCovered, isHandicap, address);

        SpotConnector.postSpot(spot);
    }

}
