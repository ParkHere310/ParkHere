package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
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
    String[] mReservations = {"Reservation ID 1","Reservation ID 2",
            "Reservation ID 3"}; // FOR TESTING PURPOSE

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
                this, android.R.layout.simple_list_item_1, mReservations);
        list.setAdapter(adapter);
     }

    private void CreateOnclickCallback() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView clickedTextView = (TextView) viewClicked;
                String reservationID = clickedTextView.getText().toString();
                Intent myIntent =  new Intent(PastReservationActivity.this, SubmitRatingAndReviewActivity.class);
                myIntent.putExtra("reservationID",reservationID);
                PastReservationActivity.this.startActivity(myIntent);
            }
        });
    }

}
