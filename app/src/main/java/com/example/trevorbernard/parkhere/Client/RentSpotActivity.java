package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.Connectors.TransactionConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.example.trevorbernard.parkhere.Reservation.Transaction;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Hexi on 2016/10/23.
 */

public class RentSpotActivity extends Activity {

    Button rentButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentspot);
        initiateVariables();
        createOnclickCallback();
    }

    private void createOnclickCallback() {
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent (RentSpotActivity.this, MainActivity.class);
                RentSpotActivity.this.startActivity(myIntent);
            }
        });
    }

    //iman,
    private void rentSpotFromGUI( String parkingSpotUID) {

        String seekerUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String ownerUID = SpotConnector.getParkingSpotFromUID(parkingSpotUID).getOwnerUID();

                Transaction transaction = null;


        Reservation res = new Reservation(ownerUID, seekerUID,
                parkingSpotUID, transaction);

        TransactionConnector.addReservation(res);
    }

    private void initiateVariables() {
        rentButton = (Button) findViewById(R.id.RentButton);
    }
}
