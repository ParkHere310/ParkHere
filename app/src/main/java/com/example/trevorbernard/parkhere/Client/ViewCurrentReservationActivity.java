package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotReservation;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpotReservationAdapter;
import com.example.trevorbernard.parkhere.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junseob on 10/29/16.
 */

public class ViewCurrentReservationActivity extends Activity {

    ListView listView;


    private static final String TAG = "ViewCurrentReservationActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcurrentreservation);

        initiateVariable();
        CreateOnclickCallback();
    }

    private void initiateVariable() {
        listView = (ListView) findViewById(R.id.listView);
    }

    private void CreateOnclickCallback() {
    }
}
