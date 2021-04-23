package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.food_app.Retailer.Retailer;

import java.util.ArrayList;
import java.util.List;

public class popwindow extends AppCompatActivity {
    private Spinner select_user;
    private Button join;
    public TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width), (int)(height*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        select_user = findViewById(R.id.select_user);
        username = findViewById(R.id.username);
        username.setText(String.format(
                "%s", getIntent().getStringExtra("username")
        ));


        List<String> user = new ArrayList<>();
        user.add("Select the kind of user");
        user.add("Customer");
        user.add("Retailer");
        user.add("WholeSaler");

        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, user);
        userAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        select_user.setAdapter(userAdapter);

        select_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String user_select = select_user.getSelectedItem().toString();
                if(! user_select.equals("Select the kind of user")){
                    join.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (user_select.compareTo("Customer")==0){
                                Intent intent = new Intent(popwindow.this, Customer.class);
                                intent.putExtra("username", username.getText().toString());
                                startActivity(intent);
                            }
                            else if (user_select.compareTo("Retailer")==0){
                                Intent intent = new Intent(popwindow.this, Retailer.class);
                                startActivity(intent);
                            }
                            else if (user_select.compareTo("WholeSaler")==0){
                                Intent intent = new Intent(popwindow.this, WholeSaler.class);
                                startActivity(intent);
                            }
                        }
                    });
                    Toast.makeText(popwindow.this, "You are a "+ user_select, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        join = (Button) findViewById(R.id.join);

    }
}