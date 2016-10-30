package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.R;

/**
 * Created by junseob on 10/29/16.
 */

public class ReservedSpotActivity extends Activity {
    private Button cancelButton;
    private Button checkinButton;
    private TextView title;
    private TextView date;
    private TextView startTime;
    private TextView endTime;
    private TextView type;
    private TextView covered;
    private TextView handicapped;



    private static final String TAG = "ReservedSpotActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcurrentreservation);




        initiateVariable();

        CreateOnclickCallback();
    }


    private void initiateVariable() {
        cancelButton = (Button)findViewById(R.id.reviewButton);
        checkinButton = (Button) findViewById(R.id.checkinButton);

        /*
        do a bunch of setTexts with the textviews below using the info in the pertinent
        object
         */

        title = (TextView)findViewById(R.id.title);
        date = (TextView)findViewById(R.id.actual_date);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        type = (TextView) findViewById(R.id.isType);
        handicapped = (TextView) findViewById(R.id.isHandicapped);
        covered = (TextView) findViewById(R.id.isCovered);

    }

    private void CreateOnclickCallback() {


        //When uploadVerificationButton is clicked
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO BE FILLED
            }
        });

        checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO BE FILLED
            }
        });
    }
}
