package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.Connectors.TransactionConnector;
import com.example.trevorbernard.parkhere.Connectors.UserConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

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
    private TextView description;
    private TextView address;
    private TextView price;
    private TextView type; // TODO: set these
    private TextView covered;
    private TextView handicapped;
    private ImageView image;

    Query queryRef;
    Query queryRef2;
    String reservationUID;
    Reservation mReservation;
    ParkingSpot mParkingSpot;



    private static final String TAG = "ReservedSpotActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.trevorbernard.parkhere.R.layout.activity_reservedspot);




        initiateVariable();

        CreateOnclickCallback();
    }


    private void initiateVariable() {
        cancelButton = (Button)findViewById(com.example.trevorbernard.parkhere.R.id.UploadReviewButton);
        checkinButton = (Button) findViewById(com.example.trevorbernard.parkhere.R.id.checkinButton);

        /*
        do a bunch of setTexts with the textviews below using the info in the pertinent
        object
         */

        title = (TextView)findViewById(com.example.trevorbernard.parkhere.R.id.title);
        date = (TextView)findViewById(R.id.actual_date);
        startTime = (TextView) findViewById(com.example.trevorbernard.parkhere.R.id.startTime);
        endTime = (TextView) findViewById(com.example.trevorbernard.parkhere.R.id.endTime);
        type = (TextView) findViewById(com.example.trevorbernard.parkhere.R.id.isType);
        price = (TextView) findViewById(com.example.trevorbernard.parkhere.R.id.actual_price);
        handicapped = (TextView) findViewById(com.example.trevorbernard.parkhere.R.id.isHandicapped);
        covered = (TextView) findViewById(com.example.trevorbernard.parkhere.R.id.isCovered);
        description = (TextView) findViewById(com.example.trevorbernard.parkhere.R.id.actual_description);
        address = (TextView) findViewById(com.example.trevorbernard.parkhere.R.id.address_label);
        image = (ImageView) findViewById(R.id.imageView);

        Intent myIntent = this.getIntent();
        reservationUID = myIntent.getExtras().getString("reservationID");
        System.out.println("~~~~~~~~~");
        System.out.println(reservationUID);
        System.out.println("~~~~~~~~~~");

        //Get RESERVATION from specific RESERVATION ID
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        queryRef = mDatabase.child("Reservations").orderByChild("uid").equalTo(reservationUID);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    Reservation res = postSnapshot.getValue(Reservation.class);
                    setText(res);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void CreateOnclickCallback() {


        //When uploadVerificationButton is clicked
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionConnector.cancelReservation(reservationUID,mParkingSpot);
                Intent myIntent = new Intent(ReservedSpotActivity.this, MainActivity.class);
                ReservedSpotActivity.this.startActivity(myIntent);
                // Put code to return occupantID to "-1" here

            }
        });

        checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO:If Statement here to ensure current time is after the reservation's end time
                // Will System.currentTimeMillis() work???
                UserConnector.checkIn(mReservation);
                Intent myIntent = new Intent(ReservedSpotActivity.this, MainActivity.class);
                ReservedSpotActivity.this.startActivity(myIntent);
            }
        });
    }

    public void setText(Reservation mReservation) {
        System.out.println("found reservation**********");
        this.mReservation = mReservation;
        //Get ParkingSpot from specific ParkingSpot ID
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        queryRef2 = mDatabase.child("ParkingSpots").orderByChild("uid").equalTo(mReservation.getParkingSpotUID());
        queryRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    setText2(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setText2(ParkingSpot mSpot) {
        System.out.println("found parking spot**********");
        mParkingSpot = mSpot;
        title.setText(mSpot.getName());
        Date start = new Date(mSpot.getTimeWindow().getStartDateTime());
        Date end = new Date(mSpot.getTimeWindow().getEndDateTime());
        date.setText(Integer.toString(start.getDate()));
        startTime.setText(Integer.toString(start.getHours()) + ":" + Integer.toString(start.getMinutes()));
        endTime.setText(Integer.toString(end.getHours()) + ":" + Integer.toString(end.getMinutes()));
        price.setText( String.valueOf( (mSpot.getPrice()/100.0) ) );
        description.setText(mSpot.getDescription());
        address.setText(mSpot.getAddress());

        if(mSpot.getImageURL().equals("-1")) return;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference(mSpot.getImageURL());
        final long ONE_MEGABYTE = 1024 * 1024;
        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
                setBmp(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });


    }

    private void setBmp(Bitmap bmp) {
        image.setImageBitmap(bmp);

    }
}
