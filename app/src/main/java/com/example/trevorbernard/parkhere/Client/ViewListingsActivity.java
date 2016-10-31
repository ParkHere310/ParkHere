package com.example.trevorbernard.parkhere.Client;

/**
 * Created by junseob on 10/27/16.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trevorbernard.parkhere.R;


public class ViewListingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewlistings);
        initiateVariables();

        populateListView();

        createClickCallback();
    }

    private void createClickCallback() {


    }

    //ONLY FOR TESTING PURPOSES
    private void populateListView() {
        String[] mReservations = {"Reservation Address 1","Reservation Address 2",
                "Reservation Address3 "};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.layout_reservationitem, mReservations);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

    private void initiateVariables() {
        ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView clickedTextView = (TextView) viewClicked;
                Toast.makeText(ViewListingsActivity.this,
                        "I just clicked on: " + clickedTextView.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
