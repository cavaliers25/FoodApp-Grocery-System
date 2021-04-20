package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class login extends AppCompatActivity {
    EditText enternumber;
    private Button getOtp;
    private ProgressBar progressBar;
    DatabaseReference reff;
    public TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        enternumber = findViewById(R.id.phone_number);
        getOtp = findViewById(R.id.getotp);
        progressBar = findViewById(R.id.progressbar);
        user = findViewById(R.id.kindOfUser);

        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!enternumber.getText().toString().trim().isEmpty()){
                    if((enternumber.getText().toString().trim()).length() ==10){
                        progressBar.setVisibility(View.VISIBLE);
                        getOtp.setVisibility(View.INVISIBLE);
                        String number = enternumber.getText().toString();

                        final DatabaseReference rootRef;
                        rootRef = FirebaseDatabase.getInstance().getReference();

                        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(!(dataSnapshot.child("Users").child(number).exists())){
                                    Toast.makeText(login.this, "Entered number is not available on our database", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(login.this, "Please create an account first", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    sendVerificationCode(enternumber);
                                    reff = FirebaseDatabase.getInstance().getReference().child("Users").child(number);
                                    reff.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                            String user_kind = datasnapshot.child("User").getValue().toString();
                                            user.setText(user_kind);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else{
                        Toast.makeText(login.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(login.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendVerificationCode(EditText enternumber){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber("+91" + enternumber.getText().toString())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    progressBar.setVisibility(View.GONE);
                    getOtp.setVisibility(View.VISIBLE);
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                    progressBar.setVisibility(View.GONE);
                    getOtp.setVisibility(View.VISIBLE);
                    Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    progressBar.setVisibility(View.GONE);
                    getOtp.setVisibility(View.VISIBLE);
                    Intent intent = new Intent(login.this,OTP.class);
                    intent.putExtra("backendotp", backendotp);
                    intent.putExtra("mobile", enternumber.getText().toString());
                    intent.putExtra("user_kind", user.getText().toString());
                    startActivity(intent);
                }
            };
}