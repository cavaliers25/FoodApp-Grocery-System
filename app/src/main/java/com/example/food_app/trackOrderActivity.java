package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class trackOrderActivity extends AppCompatActivity {

    private String trackOrder = "";
    private ImageView go_back;
    private TextView ordernumber, cancelorder;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        trackOrder = getIntent().getStringExtra("phone");

        ordernumber = findViewById(R.id.textOrder);
        go_back = findViewById(R.id.go_back);
        button = findViewById(R.id.track);

        ordernumber.setText(trackOrder);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(trackOrderActivity.this, Customer.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(trackOrderActivity.this,OrderTracking.class);
                startActivity(intent);
            }
        });


    }
}