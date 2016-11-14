package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by zilongxiao on 10/30/16.
 */

public class PastReservedSpotActivity extends Activity {
    private Button ReviewButton;
    private TextView title;
    private TextView date;
    private TextView startTime;
    private TextView endTime;
    private TextView address;
    private TextView type;
    private TextView covered;
    private TextView handicapped;
    private TextView description;
    private TextView price;
    private ImageView image;
    String reservationUID;

    Query queryRef;
    Query queryRef2;


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
        address = (TextView) findViewById(R.id.address_label);
        description = (TextView) findViewById(R.id.actual_description);

        price = (TextView) findViewById(R.id.actual_price);

        image = (ImageView) findViewById(R.id.imageView);

        Intent myIntent = this.getIntent();
        reservationUID = myIntent.getExtras().getString("reservationID");

        //Get RESERVATION from specific RESERVATION ID
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        queryRef = mDatabase.child("PastReservations").orderByChild("uid").equalTo(reservationUID);
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
        ReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(PastReservedSpotActivity.this, SubmitRatingAndReviewActivity.class);
                myIntent.putExtra("reservationUID",reservationUID);
                PastReservedSpotActivity.this.startActivity(myIntent);
            }
        });
    }

    private void setText(Reservation mReservation) {
        //ParkingSpot mSpot = SpotConnector.getParkingSpotFromUID(mReservation.getParkingSpotUID());


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
        title.setText(mSpot.getName());
        address.setText(mSpot.getAddress());
        Date start = new Date(mSpot.getTimeWindow().getStartDateTime());
        Date end = new Date(mSpot.getTimeWindow().getEndDateTime());
        date.setText(Integer.toString(start.getDate()));
        startTime.setText(Integer.toString(start.getHours()) + ":" + Integer.toString(start.getMinutes()));
        endTime.setText(Integer.toString(end.getHours()) + ":" + Integer.toString(end.getMinutes()));
        description.setText(mSpot.getDescription());

        price.setText(String.valueOf( (mSpot.getPrice()/100.0) ) );


        /*StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(mSpot.getImageURL());
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
        });*/

    }

    private void setBmp(Bitmap bmp) {
        image.setImageBitmap(bmp);

    }

}
