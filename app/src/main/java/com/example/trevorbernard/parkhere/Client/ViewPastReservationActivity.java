package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.Connectors.UserConnector;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * Created by Hexi on 2016/10/22.
 */

public class ViewPastReservationActivity extends Activity {

    ListView list;
    String[] mReservations = {"Reservation ID 1","Reservation ID 2",
            "Reservation ID 3"}; // FOR TESTING PURPOSE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpastreservation);
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
                Intent myIntent =  new Intent(ViewPastReservationActivity.this, PastReservedSpotActivity.class);
                myIntent.putExtra("reservationID",reservationID);
                ViewPastReservationActivity.this.startActivity(myIntent);
            }
        });
    }

    private List<Reservation> getPastResercationList() {
        return UserConnector.getPastReservationsforUserUID(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());
    }

    // This function return the current reservation list. Use it in the current reservation list activity
    private List<Reservation> getResercationList() {
        return UserConnector.getReservationsforUserUID(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());
    }

}