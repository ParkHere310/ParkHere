package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trevorbernard.parkhere.Connectors.UserConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.User.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Hexi on 2016/10/23.
 */

public class FilterActivity extends Activity {

    Button FilterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initiateVariables();
        createOnclickCallback();
    }

    private void createOnclickCallback() {
        FilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(FilterActivity.this, SearchResultActivity.class);
                FilterActivity.this.startActivity(myIntent);

            }
        });
    }

    private void initiateVariables() {
        FilterButton = (Button) findViewById(R.id.FilterButton);
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

    private ArrayList<ParkingSpot> sortByDistance(ArrayList<ParkingSpot> spotList) {
        // TODO: this
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
}
