package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.trevorbernard.parkhere.MainActivity;
import com.example.trevorbernard.parkhere.R;

/**
 * Created by Hexi on 2016/10/23.
 */

public class SubmitRatingAndReviewActivity extends Activity{

    Button submitButton;
    EditText EditTextComment;
    EditText EditTextRating;
    String comment;
    int rating;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitratingandreview);

        initiateVariable();
        createClickCallback();
    }

    private void createClickCallback() {
        EditTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                comment = EditTextComment.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        EditTextRating.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                rating = Integer.parseInt(EditTextRating.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SubmitRatingAndReviewActivity.this, MainActivity.class);
                SubmitRatingAndReviewActivity.this.startActivity(myIntent);
            }
        });
    }

    private void initiateVariable() {
        submitButton = (Button) findViewById(R.id.SubmitButton);
        EditTextComment = (EditText) findViewById(R.id.CommentText);
        EditTextRating = (EditText) findViewById(R.id.RatingText);
    }
}
