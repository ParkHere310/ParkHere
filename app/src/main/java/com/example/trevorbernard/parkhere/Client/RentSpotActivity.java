package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.TransactionRequest;
import com.braintreegateway.ValidationError;
import com.braintreepayments.api.PaymentRequest;
import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.Connectors.TransactionConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.ParkingSpot.TimeWindow;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.Reservation.Reservation;
import com.example.trevorbernard.parkhere.Reservation.Transaction;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hexi on 2016/10/23.
 */

public class RentSpotActivity extends Activity {

    BraintreeGateway gateway;
    String clientToken;
    Button rentButton;
    Button viewProfileButton;
    TextView descriptionTextview;
    TextView priceTextview;

    String distance;
    String spotUID;
    ParkingSpot parkingSpot;


    String seekerUID;
    String ownerUID;
    String parkingSpotUID;
    long startLongFromIntent;
    long endLongFromIntent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentspot);


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    gateway = new BraintreeGateway(
                            com.braintreegateway.Environment.SANDBOX,
                            "5jtxyw2hnwrfg6sp",
                            "rg5gxf7829xbsw6p",
                            "003fea70bbe0767277fcb49f3e8bc574"
                    );

                    clientToken = gateway.clientToken().generate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();



        initiateVariables();
        createOnclickCallback();

    }

    private void initiateVariables() {
        //Grab info from previous activity
        distance = RentSpotActivity.this.getIntent().getExtras().getString("distance");
        spotUID = RentSpotActivity.this.getIntent().getExtras().getString("spotUID");

        startLongFromIntent = RentSpotActivity.this.getIntent().getExtras().getLong("startDateLong");
        endLongFromIntent =   RentSpotActivity.this.getIntent().getExtras().getLong("endDateLong");


        //set up widgets
        rentButton = (Button) findViewById(R.id.RentButton);
        viewProfileButton = (Button) findViewById(R.id.ViewProfileButton);
        descriptionTextview = (TextView) findViewById(R.id.DescriptionTextView);
        priceTextview = (TextView) findViewById(R.id.PriceTextView);

        /*
        TO BE UPDATED
        */
        //input content into widgets
        populateParkingSpot();
    }

    private void createOnclickCallback() {
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentSpotFromGUI(spotUID);
            }
        });
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RentSpotActivity.this,ProfileActivity.class);
                myIntent.putExtra("ownerUID",ownerUID);
                RentSpotActivity.this.startActivity(myIntent);
            }
        });
    }

    private void populateParkingSpot() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
        final ArrayList<ParkingSpot> list = new ArrayList<ParkingSpot>();
        Query queryRef = mDatabase.orderByChild("uid").equalTo(spotUID);
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    parkingSpot = postSnapshot.getValue(ParkingSpot.class);

                    priceTextview.setText("$ " + new DecimalFormat("#.00").format(parkingSpot.getPrice()/100));
                    descriptionTextview.setText(parkingSpot.getAddress()+ " " + parkingSpot.getDescription());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //iman,
    private void rentSpotFromGUI( String parkingSpotUID) {


        seekerUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.parkingSpotUID = parkingSpotUID;

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        Query queryRef = mDatabase.child("ParkingSpots").orderByChild("uid").equalTo(spotUID); // limited to 10
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                    finalizeSpot(spot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void splitParkingSpot(ParkingSpot originalSpot) {

        // create new spot before, 10 minute buffer
        if(startLongFromIntent > originalSpot.getTimeWindow().getStartDateTime() + 600000L) {

            ParkingSpot beforeSpot = new ParkingSpot(originalSpot.getName(),originalSpot.getDescription(),
                    originalSpot.getPrice(),originalSpot.isSUV(),originalSpot.isCovered(),originalSpot.isHandicap(),
                    originalSpot.getAddress(), new Date(originalSpot.getTimeWindow().getStartDateTime()),
                    new Date(originalSpot.getTimeWindow().getEndDateTime()),originalSpot.getLatitude(),
                    originalSpot.getLongitude());
            beforeSpot.setImageURL(originalSpot.getImageURL());
            beforeSpot.setPhysicalSpotUID(originalSpot.getPhysicalSpotUID());
            beforeSpot.setOwnerUID(originalSpot.getOwnerUID());

            TimeWindow beforeTimeWindow = new TimeWindow();
            beforeTimeWindow.setStartDateTime(originalSpot.getTimeWindow().getStartDateTime());
            // 10 min buffer
            beforeTimeWindow.setEndDateTime(startLongFromIntent - 600000L);
            beforeSpot.setTimeWindow(beforeTimeWindow);

            //new price to be fraction of original price
            int newPrice = originalSpot.getPrice()/2;
            beforeSpot.setPrice(newPrice);
            SpotConnector.postSpot(beforeSpot);
        }



        // create new spot after, 10 minute buffer
        if(endLongFromIntent < originalSpot.getTimeWindow().getEndDateTime() - 600000L) {
            ParkingSpot afterSpot = new ParkingSpot(originalSpot.getName(),originalSpot.getDescription(),
                    originalSpot.getPrice(),originalSpot.isSUV(),originalSpot.isCovered(),originalSpot.isHandicap(),
                    originalSpot.getAddress(), new Date(originalSpot.getTimeWindow().getStartDateTime()),
                    new Date(originalSpot.getTimeWindow().getEndDateTime()),originalSpot.getLatitude(),
                    originalSpot.getLongitude());
            afterSpot.setImageURL(originalSpot.getImageURL());
            afterSpot.setPhysicalSpotUID(originalSpot.getPhysicalSpotUID());
            afterSpot.setOwnerUID(originalSpot.getOwnerUID());
            TimeWindow afterTimeWindow = new TimeWindow();
            afterTimeWindow.setEndDateTime(originalSpot.getTimeWindow().getEndDateTime());
            // 10 min buffer
            afterTimeWindow.setStartDateTime(endLongFromIntent + 600000L);
            afterSpot.setTimeWindow(afterTimeWindow);

            //new price to be fraction of original price
            int newPrice = originalSpot.getPrice()/2;
            afterSpot.setPrice(newPrice);

            SpotConnector.postSpot(afterSpot);

        }
        //shrink origional spot
        TimeWindow newTimeWindow = originalSpot.getTimeWindow();
        newTimeWindow.setStartDateTime(startLongFromIntent);
        newTimeWindow.setEndDateTime(endLongFromIntent);
        originalSpot.setTimeWindow(newTimeWindow);
        SpotConnector.editSpot(originalSpot);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if(!data.hasExtra("com.braintreepayments.api.dropin.EXTRA_PAYMENT_METHOD_NONCE"))
            { return;}
            Object ob = data.getParcelableExtra("com.braintreepayments.api.dropin.EXTRA_PAYMENT_METHOD_NONCE");
            String nonce;

            if(ob instanceof com.braintreepayments.api.models.CardNonce ) {

                com.braintreepayments.api.models.CardNonce paymentMethodNonce = (com.braintreepayments.api.models.CardNonce) ob;
                nonce = paymentMethodNonce.getNonce();
            } else {
                com.braintreepayments.api.models.PayPalAccountNonce paymentMethodNonce = (com.braintreepayments.api.models.PayPalAccountNonce) ob;
                nonce = paymentMethodNonce.getNonce();
            }

            double price = parkingSpot.getPrice();
            price = price / 100;

            final TransactionRequest request = new TransactionRequest()
                    .amount(new BigDecimal(price)).paymentMethodNonce(nonce)
                            .options()
                            .submitForSettlement(true)
                            .done();


            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {
                        Result<com.braintreegateway.Transaction> result = gateway.transaction().sale(request);

                        if (result.isSuccess()) {
                            com.braintreegateway.Transaction transaction = result.getTarget();
                            System.out.println("Success!: " + transaction.getId());

                            Transaction trans = null;

                            /*DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("ParkingSpots");
                            Query queryRef = mDatabase.orderByChild("UID").equalTo(parkingSpotUID); // limited to 10
                            queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                        ParkingSpot spot = postSnapshot.getValue(ParkingSpot.class);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });*/

                            splitParkingSpot(parkingSpot);

                            Reservation res = new Reservation(ownerUID, seekerUID,
                                    parkingSpotUID, null);
                            res.setPrice(parkingSpot.getPrice());
                            res.setDate(startLongFromIntent);
                            System.out.println(res);
                            System.out.println(res.getDate());
                            System.out.println(res.getPrice());
                            TransactionConnector.addReservation(res);


                        } else if (result.getTransaction() != null) {
                            com.braintreegateway.Transaction transaction = result.getTransaction();
                            System.out.println("Error processing transaction:");
                            System.out.println("  Status: " + transaction.getStatus());
                            System.out.println("  Code: " + transaction.getProcessorResponseCode());
                            System.out.println("  Text: " + transaction.getProcessorResponseText());
                        } else {
                            for (ValidationError error : result.getErrors().getAllDeepValidationErrors()) {
                                System.out.println("Attribute: " + error.getAttribute());
                                System.out.println("  Code: " + error.getCode());
                                System.out.println("  Message: " + error.getMessage());
                            }
                        }

                        Intent myIntent = new Intent (RentSpotActivity.this, MainActivity.class);
                        RentSpotActivity.this.startActivity(myIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

        }
    }

    private void finalizeSpot(ParkingSpot spot){
        this.ownerUID = spot.getOwnerUID();
        PaymentRequest paymentRequest = new PaymentRequest().clientToken(clientToken);
        startActivityForResult(paymentRequest.getIntent(this), 1);
    }

}
