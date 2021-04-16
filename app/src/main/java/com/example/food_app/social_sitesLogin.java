package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class social_sitesLogin extends AppCompatActivity {

    private Button getLocation, join;
    private EditText username, number, password, retype_password;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_sites_login);

        join = (Button) findViewById(R.id.join);
        getLocation = (Button) findViewById(R.id.getLocation);
        username = (EditText) findViewById(R.id.username);
        number = (EditText) findViewById(R.id.number);
        password = (EditText) findViewById(R.id.password);
        retype_password = (EditText) findViewById(R.id.retype_password);
        loadingBar = new ProgressDialog(this);
        GoogleMap locate = new GoogleMap();
        String current_Location = locate.current_location;


        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(social_sitesLogin.this, GoogleMap.class);
                startActivity(intent);
            }
        });
        
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join();
            }
        });
    }

    private void join(){
        String name = username.getText().toString();
        String phone = number.getText().toString();
        String pass = password.getText().toString();
        String re_pass = retype_password.getText().toString();


        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter your number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(re_pass)){
            Toast.makeText(this, "Please re-enter your password", Toast.LENGTH_SHORT).show();
        }
        else if(!pass.equals(re_pass)){
            Toast.makeText(this, "Please re-enter the same password", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Join");
            loadingBar.setMessage("Please wait, while we are checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            validatePhoneNumber(name, phone, pass);
        }
    }

    private void validatePhoneNumber(String name, String phone, String pass) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Username", name);
                    userdataMap.put("Phone Number", phone);
                    userdataMap.put("Password", pass);

                    rootRef.child("Users").child(phone).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(social_sitesLogin.this, "Congratulations, your account has been created", Toast.LENGTH_LONG).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(social_sitesLogin.this, login.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(social_sitesLogin.this, "Network Error: Please try again after some time", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(social_sitesLogin.this, "The number "+ phone+" already exists", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    Toast.makeText(social_sitesLogin.this, "Please try again using another number", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(social_sitesLogin.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}