package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class dashboard extends AppCompatActivity {
    private Button customer;
    private Button retailer;
    private Button wholesaler;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        customer = (Button) findViewById(R.id.customer);
        retailer = (Button) findViewById(R.id.retailer);
        wholesaler = (Button) findViewById(R.id.wholesaler);
        txt = (TextView) findViewById(R.id.txtview);

        wholesaler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, Retailer.class);
                startActivity(intent);
            }
        });
    }
}