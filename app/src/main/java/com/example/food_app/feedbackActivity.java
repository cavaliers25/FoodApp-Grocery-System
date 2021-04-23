package com.example.food_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.google.firebase.database.FirebaseDatabase;

public class feedbackActivity extends AppCompatActivity {

    EditText namedata, emaildata, messagedata;
    Button send, details;
    Firebase firebase;
    RatingBar rb;
    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        namedata = findViewById(R.id.namedata);
        emaildata = findViewById(R.id.emaildata);
        messagedata = findViewById(R.id.messagedata);
        rb = (RatingBar) findViewById(R.id.ratingBar2);
        send = findViewById(R.id.btn_send);
        details = findViewById(R.id.btn_details);
        phone = getIntent().getStringExtra("phone");
        Firebase.setAndroidContext(this);
        String UniqueID =
                Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
        firebase = new Firebase("https://feedsample-8e330-default-rtdb.firebaseio.com?Users" + UniqueID);

//        firebase = new Firebase("Feedback");

        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
        {

        }
        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                details.setEnabled(true);
                final String name = namedata.getText().toString();
                final String email = emaildata.getText().toString();
                final String message = messagedata.getText().toString();

                Firebase child_name = firebase.child("Name");
                child_name.setValue(name);
                if(name.isEmpty())
                {
                    namedata.setError("This is a mandatory field");
                    send.setEnabled(false);
                }
                else
                {
                    namedata.setError(null);
                    send.setEnabled(true);
                }

                Firebase child_email = firebase.child("Email");
                child_email.setValue(email);
                if(email.isEmpty())
                {
                    emaildata.setError("This is a mandatory field");
                    send.setEnabled(false);
                }
                else
                {
                    emaildata.setError(null);
                    send.setEnabled(true);
                }

                Firebase child_message = firebase.child("Message");
                child_message.setValue(message);
                if(message.isEmpty())
                {
                    messagedata.setError("This is a mandatory field");
                    send.setEnabled(false);
                }
                else
                {
                    messagedata.setError(null);
                    send.setEnabled(true);
                }
                sendEmail();
                Toast.makeText(feedbackActivity.this,"Thank your for your feedback.",Toast.LENGTH_SHORT).show();
                Toast.makeText(feedbackActivity.this,"Your feedback is very valuable to us.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(feedbackActivity.this, Customer.class);
                startActivity(intent);
                sendEmail();
                details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder( feedbackActivity.this)
                                .setTitle("Sended Details")
                                .setMessage("Name - " + name + "\n\n Email - " +email + "\n\nMessage - "+message)
                                .show();
                    }
                });
            }
        });
    }

    private void sendEmail() {
        String name = namedata.getText().toString().trim();
        String email = emaildata.getText().toString().trim();
        String subject = "Feedback Received";
        String message = ("Dear "+ name + ", Thank you for your valuable feedback");
        SendEmail sm = new SendEmail(this, email, subject, message);
        sm.execute();
    }

}