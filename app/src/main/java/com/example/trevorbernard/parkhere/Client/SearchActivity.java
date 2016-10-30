package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.trevorbernard.parkhere.R;

/**
 * Created by Hexi on 2016/10/20.
 */

public class SearchActivity extends Activity { //extends FragmentActivity implements  OnMapReadyCallback{

    //private GoogleMap mMap;

    EditText ETaddress;
    EditText ETstartTime;
    EditText ETendTime;
    EditText ETstartDate;
    EditText ETendDate;
    String address;
    String startTime;
    String endTime;
    String startDate;
    String endDate;
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
        ETaddress = (EditText) findViewById(R.id.addressField);
        ETstartDate = (EditText) findViewById(R.id.startDateField);
        ETendDate = (EditText) findViewById(R.id.endDateField);
        ETstartTime = (EditText) findViewById(R.id.startTimeField);
        ETendTime = (EditText) findViewById(R.id.endTimeField);
    }

    private void CreateOnclickCallback() {
        //When search button is clicked
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SearchActivity.this, SearchResultActivity.class);
                myIntent.putExtra("address",address);
                myIntent.putExtra("startTime",startTime);
                myIntent.putExtra("endTime",endTime);
                myIntent.putExtra("startDate",startDate);
                myIntent.putExtra("endDate",endDate);
                SearchActivity.this.startActivity(myIntent);
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

        //when start date is edited
        ETstartDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startDate = ETstartDate.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //when end date is edited
        ETendDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                endDate = ETendDate.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //when start time is edited
        ETstartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startTime = ETstartTime.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //when end time is edited
        ETendTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                endTime = ETendTime.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
