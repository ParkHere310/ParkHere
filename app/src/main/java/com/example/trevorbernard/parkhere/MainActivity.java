package com.example.trevorbernard.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.trevorbernard.parkhere.Client.LoginActivity;
import com.example.trevorbernard.parkhere.Client.ViewCurrentReservationActivity;
import com.example.trevorbernard.parkhere.Client.ViewPastReservationActivity;
import com.example.trevorbernard.parkhere.Client.PostSpotActivity;
import com.example.trevorbernard.parkhere.Client.SearchActivity;
import com.example.trevorbernard.parkhere.Client.ViewListingsActivity;
import com.example.trevorbernard.parkhere.Client.ViewPaymentsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//Last Edited by: Hexi
//Last Edited about: Adding variables to the widgets

public class MainActivity extends AppCompatActivity {
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";
    private Button postPageButton;
    private Button viewPastReservationButton;
    private Button listedSpotPage;
    private Button currentReservationButton;
    private Button searchButton;
    private Button logoutButton;
    private Button policyButton;
    private Button paymentsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        setContentView(R.layout.activity_main);
        //FirebaseAuth.getInstance().signOut();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if ( user == null) { //not logged in
            startActivity(myIntent);
        }

        InitializeVariables();

        CreateOnclickCallback();

        /*mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    // TODO: Make changes to User (i.e. set username, firebase user, etc.)
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };*/
    }

    private void CreateOnclickCallback() {
        // Set listeners

        //Post Page Button
        postPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, PostSpotActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });


        //Past Reservation Button
        viewPastReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ViewPastReservationActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //Show my(owner's) Listed Spot Button
        listedSpotPage.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ViewListingsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        }));

        //Current Reservation Button
        currentReservationButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ViewCurrentReservationActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        }));

        //Search Button
        searchButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        }));

        logoutButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        }));

        policyButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "ParkHere's philosophy regarding cancelations is that \rthe owner must specify them in the description." +
                        " \rIf there is no cancellation policy mentioned in the description, \ryou may cancel at any time with no fee incurred." +
                        "\r Otherwise there will be a charge to your paypal account.",
                        Toast.LENGTH_LONG).show();
            }
        }));

        paymentsButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ViewPaymentsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }

        }));
    }

    private void InitializeVariables() {
        postPageButton = (Button) findViewById(R.id.PostPageButton);
        viewPastReservationButton = (Button) findViewById(R.id.ViewPastReservationButton);
        listedSpotPage = (Button) findViewById(R.id.ListedSpotPage);
        currentReservationButton = (Button) findViewById(R.id.CurrentReservationButton);
        searchButton = (Button) findViewById(R.id.SearchButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        policyButton = (Button) findViewById(R.id.policyButton);
        paymentsButton = (Button) findViewById(R.id.ViewPaymentsButton);

    }



    @Override
    public void onStart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        super.onStart();
        if ( user == null) { //not logged in
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(myIntent);
        }
        //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        /*if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }*/
    }

    /*private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            // Do stuff
                        }
                    }
                });
        // [END sign_in_with_email]
    }*/
}
