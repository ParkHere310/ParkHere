package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.trevorbernard.parkhere.R;

/**
 * Created by Hexi on 2016/10/20.
 */



public class RegisterActivity extends Activity{

    private Button registerButton;
    private Button loginButton;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    String username;
    String password;
    String confirmPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initiateVariable();
    }

    @Override
    protected void onStart() {

    }
    @Override
    protected void onStop(){

    }

    private void initiateVariable() {
        loginButton = (Button) findViewById(R.id.LoginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        editTextUsername = (EditText)findViewById(R.id.EditTextUsername);
        editTextPassword = (EditText)findViewById(R.id.EditTextPassword);
        editTextConfirmPassword = (EditText)findViewById(R.id.EditTextConfirmPassword);
        username = editTextUsername.toString();
        password = editTextPassword.toString();
        confirmPassword = editTextConfirmPassword.toString();

        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                username = editTextUsername.toString();
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
                password = editTextPassword.toString();
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
                confirmPassword = editTextConfirmPassword.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO BE FILLED
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TO BE FILLED
            }
        });
    }

}
