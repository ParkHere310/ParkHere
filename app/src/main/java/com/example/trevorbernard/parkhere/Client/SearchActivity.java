package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.trevorbernard.parkhere.R;

import java.util.Date;

/**
 * Created by Hexi on 2016/10/20.
 */

public class SearchActivity extends Activity { //extends FragmentActivity implements  OnMapReadyCallback{

    //private GoogleMap mMap;

    EditText ETaddress;

    private DatePicker startDate_picker;
    private DatePicker endDate_picker;
    private TimePicker startTime_picker;
    private TimePicker endTime_picker;
    String address;

    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;
    private int startHour;
    private int startMin;
    private int endHour;
    private int endMin;

    Button searchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initiateVariable();

        CreateOnclickCallback();

        //Google Map API
        /*
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        */
    }

    //Google Map API
    /*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    */

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop(){
        super.onStop();
    }

    private void initiateVariable() {
        searchButton = (Button) findViewById(R.id.searchButton);
        ETaddress = (EditText) findViewById(R.id.address_field);

        startDate_picker = (DatePicker) findViewById(R.id.startDatePicker);
        endDate_picker = (DatePicker) findViewById(R.id.endDatePicker);
        startTime_picker = (TimePicker) findViewById(R.id.startTime_picker);
        endTime_picker = (TimePicker) findViewById(R.id.endTime_picker);

    }

    private void CreateOnclickCallback() {
        //When search button is clicked
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startYear = startDate_picker.getYear();
                startMonth = startDate_picker.getMonth();
                startDay = startDate_picker.getDayOfMonth();

                endYear = endDate_picker.getYear();
                endMonth = endDate_picker.getMonth();
                endDay = endDate_picker.getDayOfMonth();

                startHour = startTime_picker.getHour();
                startMin = startTime_picker.getMinute();

                endHour = endTime_picker.getHour();
                endMin = endTime_picker.getMinute();

                Date start = new Date(startYear,startMonth,startDay,startHour,startMin);
                Date end = new Date(endYear,endMonth,endDay,endHour,endMin);



                if( ETaddress.getText().toString().length() > 0 &&  ETaddress.getText() != null &&  ETaddress.getText().toString() != "" &&  ETaddress.getText().toString() != " "){
                    Intent myIntent = new Intent(SearchActivity.this, SearchResultActivity.class);

                    myIntent.putExtra("startDateLong", start.getTime());
                    myIntent.putExtra("endDateLong", end.getTime());
                    myIntent.putExtra("address", address);

                    SearchActivity.this.startActivity(myIntent);
                }
                else {
                    Toast.makeText( SearchActivity.this, "Address Field must have input!",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

        //when etaddress is edited
        ETaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                address = ETaddress.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
}
