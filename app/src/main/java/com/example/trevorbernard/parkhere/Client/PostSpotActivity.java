package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Hexi on 2016/10/20.
 */



public class PostSpotActivity extends Activity {
        private Button postButton;
        private Button uploadButton;

        private EditText title_field;
        //private EditText startTime_field;
        //private EditText endTime_field;

        private EditText price_field;
        private EditText address_field;
        private EditText description_field;
        private CheckBox suvCheckBox;
        private CheckBox coveredCheckBox;
        private CheckBox handicappedCheckBox;

        private DatePicker startDate_picker;
        private DatePicker endDate_picker;
        private TimePicker startTime_picker;
        private TimePicker endTime_picker;
        private String date;
        private String title;
        private String startTime;
        private String endTime;
        private Integer price;
        private String description;
        private String address;

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

        private boolean isSUV;
        private boolean isCovered;
        private boolean isHandicapped;

        private FirebaseAuth.AuthStateListener mAuthListener;
        private FirebaseAuth mAuth;
        private static final String TAG = "PostSpotActivity";
        private Bitmap spotPic = null;
        private UploadTask uploadTask;

    static final int REQUEST_IMAGE_SPOT = 1;

    public PostSpotActivity() {
    }



    void postSpotFromGUI(
            String name,
            String description,
            int price,
            boolean isSUV,
            boolean isCovered,
            boolean isHandicap,
            String address,
            Date startTime,
            Date endTime
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
        ParkingSpot spot = new ParkingSpot( name,
                description, price, isSUV, isCovered, isHandicap, address, startTime, endTime,latitude,longitude);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String uid = mDatabase.child("ParkingSpots").push().getKey(); //gets new unique id

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(spotPic != null) { spotPic.compress(Bitmap.CompressFormat.JPEG, 100, baos); }
        byte[] data = baos.toByteArray();
        final ArrayList<String> downloadUrls = new ArrayList<String>();
        // puts byte array in storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://parkhere-70b24.appspot.com");
        StorageReference imagesRef = storageRef.child("SpotPics").child(uid);
        uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                downloadUrls.add(downloadUrl.toString());
            }
        });

        if(downloadUrls.isEmpty()){
            downloadUrls.add("-1");
        }
        spot.setImageURL(downloadUrls.get(0));

        SpotConnector.postSpot(spot);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postspot);

        initiateVariable();

        createOnclickCallback();
    }

    private void initiateVariable() {
        postButton = (Button) findViewById(R.id.postButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        title_field = (EditText) findViewById(R.id.title_field);

        startDate_picker = (DatePicker) findViewById(R.id.startDatePicker);
        endDate_picker = (DatePicker) findViewById(R.id.endDatePicker);
        startTime_picker = (TimePicker) findViewById(R.id.startTime_picker);
        endTime_picker = (TimePicker) findViewById(R.id.endTime_picker);

        price_field = (EditText) findViewById(R.id.price_field);
        address_field = (EditText) findViewById(R.id.address_field);
        description_field = (EditText) findViewById(R.id.description_field);
        coveredCheckBox = (CheckBox) findViewById(R.id.coveredCheckBox);
        suvCheckBox = (CheckBox) findViewById(R.id.suvCheckBox);
        handicappedCheckBox = (CheckBox) findViewById(R.id.handicapCheckBox);

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

        price_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //iman
                //String priceStr = price_field.getText().toString();
                //price = Integer.getInteger(priceStr);
            }

            @Override
            public void afterTextChanged(Editable s) {

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

                title = title_field.getText().toString();
                address = address_field.getText().toString();

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


                String priceStr = price_field.getText().toString();
                Double priceDouble = Double.parseDouble(priceStr);
                priceDouble *= 100;
                price = priceDouble.intValue();



                //String startMinStr = endMin_field.getText().toString();
              //  startMin = Integer.parseInt(startMinStr);
                description = description_field.getText().toString();




                //Post Here
                Date start = new Date(startYear,startMonth,startDay,startHour,startMin);
                Date end = new Date(endYear,endMonth,endDay,endHour,endMin);
                /*
                System.out.println(PostSpotActivity.this.title + " " +
                        PostSpotActivity.this.description + " " +
                        PostSpotActivity.this.price + " " +
                        PostSpotActivity.this.isSUV + " " +
                        PostSpotActivity.this.isCovered + " " +
                        PostSpotActivity.this.isHandicapped + " " +
                        PostSpotActivity.this.address);
                */

                if((startHour == endHour && startMin > endMin) || (startHour > endHour) || startDay > endDay ) {
                    Toast.makeText(PostSpotActivity.this, "The end time must be after the start time!",
                            Toast.LENGTH_LONG).show();
                    //call pop up message telling them that the end time must be greater than start time
                } else {
                    postSpotFromGUI(
                            PostSpotActivity.this.title,
                            PostSpotActivity.this.description,
                            PostSpotActivity.this.price,
                            PostSpotActivity.this.isSUV,
                            PostSpotActivity.this.isCovered,
                            PostSpotActivity.this.isHandicapped,
                            PostSpotActivity.this.address,
                            start,
                            end
                    );


                    Intent myIntent = new Intent(PostSpotActivity.this, MainActivity.class);
                    PostSpotActivity.this.startActivity(myIntent);
                }
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
}

