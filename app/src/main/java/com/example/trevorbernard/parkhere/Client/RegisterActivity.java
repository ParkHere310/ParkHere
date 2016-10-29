package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.trevorbernard.parkhere.Connectors.AuthenticationConnector;
import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.R;
import com.example.trevorbernard.parkhere.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Hexi on 2016/10/20.
 */



public class RegisterActivity extends Activity{

    private Button registerButton;
    private Button uploadProfileButton;
    private Button uploadVerificationButton;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    static final int REQUEST_IMAGE_PROFILE = 1;
    static final int REQUEST_IMAGE_VERIFY = 2;
    private Bitmap profilePic = null;
    private Bitmap verifyPic = null;

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
                    User newUser = AuthenticationConnector.register(user, firstName, lastName, phoneNumber, profilePic);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        initiateVariable();

        CreateOnclickCallback();
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
        uploadProfileButton = (Button) findViewById(R.id.UploadProfileButton);
        uploadVerificationButton = (Button) findViewById(R.id.UploadVerificationButton);

        editTextUsername = (EditText)findViewById(R.id.EditTextEmail);
        editTextPassword = (EditText)findViewById(R.id.EditTextPassword);
        editTextConfirmPassword = (EditText)findViewById(R.id.EditTextConfirmPassword);
        editTextFirstName = (EditText) findViewById(R.id.EditTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.EditTextLastName);
        editTextPhoneNumber = (EditText) findViewById(R.id.EditTextPhoneNumber);

        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();
        confirmPassword = editTextConfirmPassword.getText().toString();
    }

    private void CreateOnclickCallback() {
        //When username is edited
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

        //When password is edited
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

        //When confirm password is edited
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

        //When first name is edited
        editTextFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstName = editTextFirstName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //When last name is edited
        editTextLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastName = editTextLastName.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //When phone number is edited
        editTextPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phoneNumber = editTextPhoneNumber.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //When register button is clicked
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(username, password);
            }
        });

        //When uploadProfileButton is clicked
        uploadProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(REQUEST_IMAGE_PROFILE);
            }
        });

        //When uploadVerificationButton is clicked
        uploadVerificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(REQUEST_IMAGE_VERIFY);
            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //TODO: Register failed
                        } else if (task.isSuccessful()) {
                            Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);
                            RegisterActivity.this.startActivity(myIntent);
                        }
                    }
                });
        // [END create_user_with_email]
    }
    private void dispatchTakePictureIntent(int code) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, code);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_PROFILE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            profilePic = (Bitmap) extras.get("data");

        } else if (requestCode == REQUEST_IMAGE_VERIFY && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            verifyPic = (Bitmap) extras.get("data");

        }
    }

}
