package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.trevorbernard.parkhere.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Hexi on 2016/10/20.
 */


/*
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
*/
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


                    date_field.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
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
/*
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

*/


}

