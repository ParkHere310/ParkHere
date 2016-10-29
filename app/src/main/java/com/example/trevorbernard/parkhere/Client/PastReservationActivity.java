package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trevorbernard.parkhere.R;

import java.util.List;

/**
 * Created by Hexi on 2016/10/22.
 */

public class PastReservationActivity extends Activity {

    ListView list;
    String[] mReservations = {"Reservation Address 1","Reservation Address 2",
            "Reservation Address3 "}; // FOR TESTING PURPOSE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastreservation);
        initiateVariables();
        populateListView();
        CreateOnclickCallback();
    }

    private void initiateVariables() {
        list = (ListView) findViewById(R.id.listView);

    }

    //ONLY FOR TESTING PURPOSES
    private void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.layout_reservationitem, mReservations);
        list.setAdapter(adapter);

     }

    private void CreateOnclickCallback() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView clickedTextView = (TextView) viewClicked;
                Toast.makeText(PastReservationActivity.this,
                        "I just clicked on: " + clickedTextView.toString(),Toast.LENGTH_LONG).show();
            }
        });


    }

}
