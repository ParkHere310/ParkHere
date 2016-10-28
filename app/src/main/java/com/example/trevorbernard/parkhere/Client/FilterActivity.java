package com.example.trevorbernard.parkhere.Client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trevorbernard.parkhere.R;

/**
 * Created by Hexi on 2016/10/23.
 */

public class FilterActivity extends Activity {

    Button FilterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initiateVariables();
        createOnclickCallback();
    }

    private void createOnclickCallback() {
        FilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(FilterActivity.this, SearchResultActivity.class);
                FilterActivity.this.startActivity(myIntent);

            }
        });
    }

    private void initiateVariables() {
        FilterButton = (Button) findViewById(R.id.FilterButton);
    }
}
