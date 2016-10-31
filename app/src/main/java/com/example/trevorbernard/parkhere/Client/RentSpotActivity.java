package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.braintreepayments.api.PaymentButton;
import com.braintreepayments.api.PaymentRequest;

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.Connectors.TransactionConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
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
import com.braintreegateway.*;
import com.braintreepayments.*;
import java.math.BigDecimal;

import java.util.ArrayList;

/**
 * Created by Hexi on 2016/10/23.
 */

public class RentSpotActivity extends Activity {

    BraintreeGateway gateway;
    String clientToken;
    Button rentButton;
    TextView descriptionTextview;
    TextView priceTextview;

    String distance;
    String spotUID;
    ParkingSpot parkingSpot;

    String seekerUID;
    String ownerUID;
    String parkingSpotUID;

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


        //set up widgets
        rentButton = (Button) findViewById(R.id.RentButton);
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
                    priceTextview.setText("$ " + parkingSpot.getPrice());
                    descriptionTextview.setText(parkingSpot.getName()+ "\n" + parkingSpot.getAddress()
                            + "\n" + parkingSpot.getDescription());
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
        ownerUID = SpotConnector.getParkingSpotFromUID(parkingSpotUID).getOwnerUID();
        this.parkingSpotUID = parkingSpotUID;




        PaymentRequest paymentRequest = new PaymentRequest().clientToken(clientToken);
        startActivityForResult(paymentRequest.getIntent(this), 1);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

                com.braintreepayments.api.models.CardNonce paymentMethodNonce = data.getParcelableExtra(
                        "com.braintreepayments.api.dropin.EXTRA_PAYMENT_METHOD_NONCE"
                );
                String nonce = paymentMethodNonce.getNonce();
                // Send the nonce to your server.


            final TransactionRequest request = new TransactionRequest()
                    .amount(new BigDecimal(parkingSpot.getPrice())).paymentMethodNonce(nonce)
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


                            Reservation res = new Reservation(ownerUID, seekerUID,
                                    parkingSpotUID, trans);


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

}
