package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;

/**
 * Created by zilongxiao on 10/30/16.
 */

public class PastReservedSpotActivity extends Activity {
    private Button ReviewButton;
    private TextView title;
    private TextView date;
    private TextView startTime;
    private TextView endTime;
    private TextView type;
    private TextView covered;
    private TextView handicapped;
    private TextView description;
    String reservationUID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastreservedspot);

        initiateVariable();

        CreateOnclickCallback();
    }

    private void initiateVariable() {
        ReviewButton = (Button)findViewById(R.id.UploadReviewButton);
        title = (TextView)findViewById(R.id.title);
        date = (TextView)findViewById(R.id.actual_date);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        type = (TextView) findViewById(R.id.isType);
        handicapped = (TextView) findViewById(R.id.isHandicapped);
        covered = (TextView) findViewById(R.id.isCovered);
        description = (TextView) findViewById(R.id.actual_description);

        Intent myIntent = this.getIntent();
        reservationUID = myIntent.getStringExtra("reservationID");

        //Get RESERVATION from specific RESERVATION ID
        Reservation mReservation = null;
        ParkingSpot mSpot = SpotConnector.getParkingSpotFromUID(mReservation.getParkingSpotUID());
        title.setText(mSpot.getAddress());
        date.setText("Testing Date");
        startTime.setText("Testing Start Time");
        endTime.setText("Testing end time");
        description.setText(mSpot.getDescription());
    }

    private void CreateOnclickCallback() {
        //When uploadVerificationButton is clicked
        ReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PastReservedSpotActivity.this, SubmitRatingAndReviewActivity.class);
                myIntent.putExtra("reservationUID",reservationUID);
                PastReservedSpotActivity.this.startActivity(myIntent);
            }
        });
    }
}
