package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.food_app.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText, emailEditText, pincodeEditText;
    private Button confirmOrderBtn, getLocation1;

    private String totalAmount = "";
    private RadioButton rdiPayNow;
    private int check = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);


        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = ₹" + totalAmount, Toast.LENGTH_SHORT).show();


        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.shipment_name);
        phoneEditText = (EditText) findViewById(R.id.shipment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shipment_address);
        cityEditText = (EditText) findViewById(R.id.shipment_city);
        emailEditText = (EditText) findViewById(R.id.shipment_email);
        pincodeEditText = (EditText) findViewById(R.id.shipment_pincode);
        getLocation1 = (Button) findViewById(R.id.getLocation1);
        rdiPayNow = (RadioButton) findViewById(R.id.paynow);

        rdiPayNow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    check = 1;
                }

            }
        });


        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Check();
            }
        });

        getLocation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmFinalOrderActivity.this, GoogleMap.class);
                startActivity(intent);
            }
        });

    }

    private void sendEmail() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String subject = "Order Placed";
        String message = "Congratulations "+ name + "\n\nYour order is placed successfully, and will soon be at your doorstep." + "\n\nTotal Price you have to pay is ₹" + totalAmount + ".";
        SendEmail sm = new SendEmail(this, email, subject, message);
        sm.execute();
    }

    private void Check()
    {
        if (TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your full name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(emailEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your email", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your phone number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your city name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pincodeEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your pincode", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ConfirmOrder();
        }
    }


    private void ConfirmOrder()
    {
        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("email", emailEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("pincode", pincodeEditText.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state", "not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        if(check == 1){
                                            Intent intent = new Intent(ConfirmFinalOrderActivity.this, PaymentGateway.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.putExtra("name", nameEditText.getText().toString());
                                            intent.putExtra("phone", phoneEditText.getText().toString());
                                            intent.putExtra("amount", totalAmount);
                                            startActivity(intent);
                                            finish();

                                        }
                                        else{
                                            Toast.makeText(ConfirmFinalOrderActivity.this, "Order placed successfully.", Toast.LENGTH_LONG).show();
                                            sendEmail();
                                            Intent intent = new Intent(ConfirmFinalOrderActivity.this, Customer.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }

                                    }
                                }
                            });
                }
            }
        });
    }
}