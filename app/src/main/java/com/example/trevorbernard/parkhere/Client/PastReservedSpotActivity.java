package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.R;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastreservedspot);

        initiateVariable();

        CreateOnclickCallback();
    }

    private void initiateVariable() {
        ReviewButton = (Button)findViewById(R.id.reviewButton);
        title = (TextView)findViewById(R.id.title);
        date = (TextView)findViewById(R.id.actual_date);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        type = (TextView) findViewById(R.id.isType);
        handicapped = (TextView) findViewById(R.id.isHandicapped);
        covered = (TextView) findViewById(R.id.isCovered);

        /*
        do a bunch of setTexts with the textviews below using the info in the pertinent
        object
         */
    }

    private void CreateOnclickCallback() {


        //When uploadVerificationButton is clicked
        ReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO BE FILLED
            }
        });
    }
}
