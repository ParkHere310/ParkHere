package com.example.trevorbernard.parkhere.Client;

<<<<<<< HEAD
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.trevorbernard.parkhere.Connectors.AuthenticationConnector;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.User.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
=======
import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
>>>>>>> 4a32c388beb378c4303c60a6909d16bc7ce936fa

/**
 * Created by Hexi on 2016/10/20.
 */

<<<<<<< HEAD
public class PostSpotActivity extends Activity {
    private Button postButton;
    private Button uploadButton;
    private EditText date_field;
    private EditText title_field;
    private EditText startTime_field;
    private EditText endTime_field;
    private EditText price_field;
    private EditText description_field;
    private CheckBox suvCheckBox;
    private CheckBox coveredCheckBox;
    private CheckBox handicappedCheckBox;

    private String date;
    private String title;
    private String startTime;
    private String endTime;
    private String price;
    private String description;

    private boolean isSUV;
    private boolean isCovered;
    private boolean isHandicapped;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private static final String TAG = "PostSpotActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    User newUser = AuthenticationConnector.register(user);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        initiateVariable();
    }

    private void initiateVariable() {
        postButton = (Button) findViewById(R.id.postButton);
        uploadButton = (Button) findViewById(R.id.uploadButton);
        date_field = (EditText)findViewById(R.id.date_field);
        title_field = (EditText)findViewById(R.id.title_field);
        startTime_field = (EditText)findViewById(R.id.startTime_field);
        endTime_field = (EditText)findViewById(R.id.endTime_field);
        price_field = (EditText)findViewById(R.id.price_field);
        description_field = (EditText)findViewById(R.id.description_field);

        coveredCheckBox = (CheckBox)findViewById(R.id.coveredCheckBox);
        suvCheckBox = (CheckBox)findViewById(R.id.suvCheckBox);
        handicappedCheckBox = (CheckBox)findViewById(R.id.handicapCheckBox);
        //endTime_field = editTextUsername.getText().toString();
        //password = editTextPassword.getText().toString();
        //confirmPassword = editTextConfirmPassword.getText().toString();

   //     coveredCheckBox.add

        date_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                date = date_field.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        title_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                title = title_field.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        startTime_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                startTime = startTime_field.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        endTime_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                endTime = endTime_field.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        price_field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                price = price_field.getText().toString();
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
                description = description_field.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Post Here
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Upload Here
            }
        });

        coveredCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

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
                // TODO Auto-generated method stub

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
                // TODO Auto-generated method stub

                if (suvCheckBox.isChecked()) {
                    isSUV = true;
                }
                else {
                    isSUV = false;
                }

            }
        });


    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.suvCheckBox:
                if (checked)
                    isSUV = true;
                else
                    isSUV = false;
                break;
            case R.id.coveredCheckBox:
                if (checked)
                    isCovered = true;
                else
                    isCovered = false;
                break;
            case R.id.handicapCheckBox:
                if (checked)
                    isHandicapped = true;
                else
                    isHandicapped = false;
            // TODO: Veggie sandwich
        }
        
        
         void postSpotFromGUI(
            String name,
            String description,
            int price,
            boolean isSUV,
            boolean isCovered,
            boolean isHandicap,
            String address
            ) {

        //make new spot
        //the parking spot itself will add the seller user to the class
        ParkingSpot spot = new ParkingSpot( name,
                description, price, isSUV, isCovered, isHandicap, address);

        SpotConnector.postSpot(spot);
    }

        
        
        
    }
    /*
    void postSpotByNewUser(String title, ){
=======
public class PostSpotActivity {
>>>>>>> 4a32c388beb378c4303c60a6909d16bc7ce936fa


    void postSpotFromGUI(
            String name,
            String description,
            int price,
            boolean isSUV,
            boolean isCovered,
            boolean isHandicap,
            String address
            ) {

        //make new spot
        //the parking spot itself will add the seller user to the class
        ParkingSpot spot = new ParkingSpot( name,
                description, price, isSUV, isCovered, isHandicap, address);

        SpotConnector.postSpot(spot);
    }

    private Button registerButton;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private String username;
    private String password;
    private String confirmPassword;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private static final String TAG = "RegisterActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    User newUser = AuthenticationConnector.register(user);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        initiateVariable();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    protected void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void initiateVariable() {
        registerButton = (Button) findViewById(R.id.RegisterButton);
        editTextUsername = (EditText)findViewById(R.id.EditTextEmail);
        editTextPassword = (EditText)findViewById(R.id.EditTextPassword);
        editTextConfirmPassword = (EditText)findViewById(R.id.EditTextConfirmPassword);
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();
        confirmPassword = editTextConfirmPassword.getText().toString();

        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = editTextUsername.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password = editTextPassword.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirmPassword = editTextConfirmPassword.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(username, password);
            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        // [START create_user_with_email]

        // [END create_user_with_email]
    }
    */

}
