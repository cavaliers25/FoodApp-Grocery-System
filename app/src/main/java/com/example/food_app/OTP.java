package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.food_app.Retailer.Retailer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    private EditText inputnum1;
    private EditText inputnum2;
    private EditText inputnum3;
    private EditText inputnum4;
    private EditText inputnum5;
    private EditText inputnum6;
    private Button submit;
    String getOtpBackend;
    private ProgressBar progressBar1;
    public TextView enternumber;
    public TextView user;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        inputnum1 = findViewById(R.id.inputotp1);
        inputnum2 = findViewById(R.id.inputotp2);
        inputnum3 = findViewById(R.id.inputotp3);
        inputnum4 = findViewById(R.id.inputotp4);
        inputnum5 = findViewById(R.id.inputotp5);
        inputnum6 = findViewById(R.id.inputotp6);
        enternumber = findViewById(R.id.enternumber);
        user = findViewById(R.id.user);


        submit = findViewById(R.id.submit);
        getOtpBackend = getIntent().getStringExtra("backendotp");

        progressBar1 = findViewById(R.id.progressbar);
        enternumber.setText(String.format(
                "%s", getIntent().getStringExtra("mobile")
        ));
        user.setText(String.format(
                "%s", getIntent().getStringExtra("user_kind")
        ));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputnum1.getText().toString().trim().isEmpty() && !inputnum2.getText().toString().trim().isEmpty() && !inputnum3.getText().toString().trim().isEmpty() && !inputnum4.getText().toString().trim().isEmpty() && !inputnum5.getText().toString().trim().isEmpty() && !inputnum6.getText().toString().trim().isEmpty()){
                    String enterCodeOtp = inputnum1.getText().toString() +
                            inputnum2.getText().toString() +
                            inputnum3.getText().toString() +
                            inputnum4.getText().toString() +
                            inputnum5.getText().toString() +
                            inputnum6.getText().toString();
                    if(getOtpBackend!= null){
                        progressBar1.setVisibility(View.VISIBLE);
                        submit.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                          getOtpBackend, enterCodeOtp
                        );
                        signInWithPhoneAuthCredential(phoneAuthCredential);

                    }
                    else {
                        Toast.makeText(OTP.this, "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(OTP.this, "Please enter all number", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numberotpmove();

        findViewById(R.id.resendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + enternumber.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        OTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getOtpBackend = newbackendotp;
                                Toast.makeText(OTP.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });


    }
    private void numberotpmove(){
        inputnum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    inputnum2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    inputnum3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    inputnum4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    inputnum5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputnum5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().trim().isEmpty()){
                    inputnum6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential){
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String user_kind = user.getText().toString();
                        if(task.isSuccessful()){

                            if (user_kind.compareTo("Customer")==0){
                                Intent intent = new Intent(getApplicationContext(), Customer.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("username", "login");
                                startActivity(intent);
                            }
                            else if (user_kind.compareTo("Retailer")==0){
                                Intent intent = new Intent(getApplicationContext(), Retailer.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else if (user_kind.compareTo("Wholesaler")==0){
                                Intent intent = new Intent(getApplicationContext(), WholeSaler.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }


                        }
                        else{
                            Toast.makeText(OTP.this, "Enter the correct otp", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}