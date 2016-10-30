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

import com.example.trevorbernard.parkhere.Connectors.SpotConnector;
import com.example.trevorbernard.parkhere.ParkingSpot.ParkingSpot;
import com.example.trevorbernard.parkhere.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

/**
 * Created by Hexi on 2016/10/20.
 */



public class PostSpotActivity extends Activity {
        private Button postButton;
        private Button uploadButton;
        private EditText date_field;
        private EditText title_field;
        //private EditText startTime_field;
        //private EditText endTime_field;
        private EditText year_field;
        private EditText month_field;
        private EditText day_field;
        private EditText startHour_field;
        private EditText startMin_field;
        private EditText endHour_field;
        private EditText endMin_field;
        private EditText price_field;
        private EditText address_field;
        private EditText description_field;
        private CheckBox suvCheckBox;
        private CheckBox coveredCheckBox;
        private CheckBox handicappedCheckBox;
        private String date;
        private String title;
        private String startTime;
        private String endTime;
        private int price;
        private String description;
        private String address;

        private int year;
        private int month;
        private int day;
        private int startHour;
        private int startMin;
        private int endHour;
        private int endMin;

        private boolean isSUV;
        private boolean isCovered;
        private boolean isHandicapped;

        private FirebaseAuth.AuthStateListener mAuthListener;
        private FirebaseAuth mAuth;
        private static final String TAG = "PostSpotActivity";

    public PostSpotActivity() {
    }


    void postSpotFromGUI(
            String name,
            String description,
            int price,
            boolean isSUV,
            boolean isCovered,
            boolean isHandicap,
            String address,
            Date startTime,
            Date endTime
    ) {

        //make new spot
        //the parking spot itself will add the seller user to the class
        ParkingSpot spot = new ParkingSpot( name,
                description, price, isSUV, isCovered, isHandicap, address, startTime, endTime);

        SpotConnector.postSpot(spot);
    }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_post);


                        initiateVariable();
            }

                private void initiateVariable() {
                postButton = (Button) findViewById(R.id.postButton);
                uploadButton = (Button) findViewById(R.id.uploadButton);
                date_field = (EditText)findViewById(R.id.date_field);
                title_field = (EditText)findViewById(R.id.title_field);
                startHour_field = (EditText)findViewById(R.id.startHour_field);
                endHour_field = (EditText)findViewById(R.id.endHour_field);
                    startMin_field = (EditText)findViewById(R.id.startMin_field);
                    endMin_field = (EditText)findViewById(R.id.endMin_field);
                    year_field = (EditText)findViewById(R.id.year_field);
                    month_field = (EditText)findViewById(R.id.month_field);
                    day_field = (EditText)findViewById(R.id.day_field);
                price_field = (EditText)findViewById(R.id.price_field);
                    address_field = (EditText)findViewById(R.id.address_field);
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
                    address_field.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            address = address_field.getText().toString();
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    year_field.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String yearStr = endMin_field.getText().toString();
                            year = Integer.parseInt(yearStr);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    month_field.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String monthStr = month_field.getText().toString();
                            month = Integer.parseInt(monthStr);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    day_field.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String dayStr = endMin_field.getText().toString();
                            day = Integer.parseInt(dayStr);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                endHour_field.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                     }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String endHourStr = endHour_field.getText().toString();
                        endHour = Integer.parseInt(endHourStr);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                     }
                });
                endMin_field.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                     }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String endMinStr = endMin_field.getText().toString();
                        endMin = Integer.parseInt(endMinStr);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                     }
                });
                    startHour_field.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String startHourStr = endHour_field.getText().toString();
                            startHour = Integer.parseInt(startHourStr);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    startMin_field.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String startMinStr = endMin_field.getText().toString();
                            startMin = Integer.parseInt(startMinStr);
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
                               String priceStr = price_field.getText().toString();
                                    price = Integer.getInteger(priceStr);
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



                                    
                                    Date startTime = new Date(year,month,day,startHour,startMin);
                                    Date endTime = new Date(year,month,day,endHour,endMin);

                                    //Jun, print the title, description, price,
                                    //all bools, address, and start/end time to log.
                                    //There are 2 date variables above, make them using
                                    // info from date and time picker
/*
                                    System.out.println(PostSpotActivity.this.title + " " +
                                            PostSpotActivity.this.description + " " +
                                            PostSpotActivity.this.price + " " +

                                            PostSpotActivity.this.isSUV + " " +
                                            PostSpotActivity.this.isCovered + " " +
                                            PostSpotActivity.this.isHandicapped + " " +
                                            PostSpotActivity.this.address);
                                            */
                                    //,startTime,endTime)

                                    //this will post to database.

                                    postSpotFromGUI(
                                            PostSpotActivity.this.title,
                                            PostSpotActivity.this.description,
                                    PostSpotActivity.this.price, PostSpotActivity.this.isSUV,
                                    PostSpotActivity.this.isCovered,
                                    PostSpotActivity.this.isHandicapped,
                                    PostSpotActivity.this.address,startTime,endTime
                                    );



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

