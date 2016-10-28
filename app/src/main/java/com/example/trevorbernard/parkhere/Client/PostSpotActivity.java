package com.example.trevorbernard.parkhere.Client;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;

/**
 * Created by Hexi on 2016/10/20.
 */

public class PostSpotActivity {
    void postSpotFromGUI(String title, ){
        ParkingSpot spot = new ParkingSpot();
        SpotConnector.postSpot(spot);
    }

}
