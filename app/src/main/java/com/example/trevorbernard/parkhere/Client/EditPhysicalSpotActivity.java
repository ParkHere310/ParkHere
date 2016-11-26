package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;


/**
 * Created by junseob on 11/26/16.
 */

public class EditPhysicalSpotActivity extends Activity {
    private Button postButton;
    private Button uploadButton;

    private EditText title_field;



    private EditText address_field;
    private EditText description_field;
    private CheckBox suvCheckBox;
    private CheckBox coveredCheckBox;
    private CheckBox handicappedCheckBox;




    private boolean isSUV;
    private boolean isCovered;
    private boolean isHandicapped;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private static final String TAG = "EditPhysicalSpotActivity";
    private Bitmap spotPic = null;

    static final int REQUEST_IMAGE_SPOT = 1;

    String spotUID;
    ParkingSpot mSpot;

    public EditPhysicalSpotActivity() {
    }



    void editReservationFromGUI(
            String name,
            String description,

            boolean isSUV,
            boolean isCovered,
            boolean isHandicap,
            String address

    ) {
        double latitude = -1;
        double longitude = -1;
        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> codedAddress = geocoder.getFromLocationName(address,5);
            latitude = codedAddress.get(0).getLatitude();
            longitude = codedAddress.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //make new spot
        //the parking spot itself will add the seller user to the class
        ParkingSpot spot = mSpot;

        spot.setName(name);
        spot.setDescription(description);
        spot.setSUV(isSUV);
        spot.setCovered(isCovered);
        spot.setHandicap(isHandicap);
        spot.setAddress(address);
        spot.setLatitude(latitude);
        spot.setLongitude(longitude);

        SpotConnector.editSpot(spot);

        //   SpotConnector.EditReservation(spot);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editphysicalspot);

        initiateVariable();

        createOnclickCallback();
    }

    private void initiateVariable() {
        postButton = (Button) findViewById(R.id.postButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        title_field = (EditText) findViewById(R.id.title_field);

        address_field = (EditText) findViewById(R.id.address_field);
        description_field = (EditText) findViewById(R.id.description_field);
        coveredCheckBox = (CheckBox) findViewById(R.id.coveredCheckBox);
        suvCheckBox = (CheckBox) findViewById(R.id.suvCheckBox);
        handicappedCheckBox = (CheckBox) findViewById(R.id.handicapCheckBox);

        Intent previousIntent = this.getIntent();
        spotUID = previousIntent.getExtras().getString("spot");

        isSUV = false;
        isCovered = false;
        isHandicapped = false;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        Query queryRef = mDatabase.orderByChild("uid").equalTo(spotUID);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    makeGui(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void createOnclickCallback() {
        title_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //   title = title_field.getText().toString();
                //iman
            }



            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        address_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //iman
                // address = address_field.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        description_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //iman
                // description = description_field.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                String title = title_field.getText().toString();
                String address = address_field.getText().toString();









                //String startMinStr = endMin_field.getText().toString();
                //  startMin = Integer.parseInt(startMinStr);
                String description = description_field.getText().toString();





                /*
                System.out.println(PostSpotActivity.this.title + " " +
                        PostSpotActivity.this.description + " " +
                        PostSpotActivity.this.price + " " +
                        PostSpotActivity.this.isSUV + " " +
                        PostSpotActivity.this.isCovered + " " +
                        PostSpotActivity.this.isHandicapped + " " +
                        PostSpotActivity.this.address);
                */


/*
                    editReservationFromGUI(
                            EditPhysicalSpotActivity.this.title,
                            EditPhysicalSpotActivity.this.description,
                            EditPhysicalSpotActivity.this.isSUV,
                            EditPhysicalSpotActivity.this.isCovered,
                            EditPhysicalSpotActivity.this.isHandicapped,
                            EditPhysicalSpotActivity.this.address

                    );

*/
                    Intent myIntent = new Intent(EditPhysicalSpotActivity.this, MainActivity.class);
                    EditPhysicalSpotActivity.this.startActivity(myIntent);

            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(REQUEST_IMAGE_SPOT);
            }
        });

        coveredCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (coveredCheckBox.isChecked()) {
                    isCovered = true;
                }
                else {
                    isCovered = false;
                }
            }
        });

        handicappedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (handicappedCheckBox.isChecked()) {
                    isHandicapped = true;
                }
                else {
                    isHandicapped = false;
                }
            }
        });

        suvCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (suvCheckBox.isChecked()) {
                    isSUV = true;
                }
                else {
                    isSUV = false;
                }
            }
        });

    }

    private void dispatchTakePictureIntent(int code) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, code);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_SPOT && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            spotPic = (Bitmap) extras.get("data");
        }
    }

    private void makeGui(ParkingSpot spot) {
        title_field.setText(spot.getName());
        address_field.setText(spot.getAddress());
        description_field.setText(spot.getDescription());
        mSpot = spot;
    }
}
