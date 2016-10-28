package com.example.trevorbernard.parkhere;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.trevorbernard.parkhere.Client.LoginActivity;
import com.example.trevorbernard.parkhere.Client.PastReservationActivity;
import com.example.trevorbernard.parkhere.Client.PostSpotActivity;
import com.example.trevorbernard.parkhere.Client.SearchActivity;
import com.example.trevorbernard.parkhere.Client.ViewListingsActivity;
import com.google.firebase.auth.FirebaseAuth;

//Last Edited by: Hexi
//Last Edited about: Adding variables to the widgets

public class MainActivity extends AppCompatActivity {
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MainActivity";
    private Button postPageButton;
    private Button viewReservationButton;
    private Button listedSpotPage;
    private Button reservationButton;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseAuth.getInstance().getCurrentUser().getUid() == null) {         //not logged in
            Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(myIntent);
        }
        InitializeVariables();

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

    private void InitializeVariables() {
        postPageButton = (Button) findViewById(R.id.PostPageButton);
        viewReservationButton = (Button) findViewById(R.id.ViewReservationButton);
        listedSpotPage = (Button) findViewById(R.id.ListedSpotPage);
        reservationButton = (Button) findViewById(R.id.ReservationButton);
        searchButton = (Button) findViewById(R.id.SearchButton);

        // Set listeners
        postPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, PostSpotActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
        viewReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent myIntent = new Intent(MainActivity.this, PostSpotActivity.class);
                //MainActivity.this.startActivity(myIntent);
            }
        });
        listedSpotPage.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ViewListingsActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        }));

        reservationButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, PastReservationActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        }));

        searchButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, SearchActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        }));
    }

    @Override
    public void onStart() {
        super.onStart();
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
