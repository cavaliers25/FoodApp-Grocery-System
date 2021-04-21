package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class social_sitesLogin extends AppCompatActivity {

    private Button getLocation, join;
    private EditText username, number, password, retype_password;
    private ProgressDialog loadingBar;
    private Spinner select_user;


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
        select_user = findViewById(R.id.select_user);


        List<String> user = new ArrayList<>();
        user.add("Select the kind of user");
        user.add("Customer");
        user.add("Retailer");
        user.add("Wholesaler");

        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, user);
        userAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        select_user.setAdapter(userAdapter);

        select_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String user_select = select_user.getSelectedItem().toString();
                if(! user_select.equals("Select the kind of user")){
                    Toast.makeText(social_sitesLogin.this, "You are a "+ user_select, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        String user_select = select_user.getSelectedItem().toString();


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

            validatePhoneNumber(name, phone, pass, user_select);
        }
    }

    private void validatePhoneNumber(String name, String phone, String pass, String user_select) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("name", name);
                    userdataMap.put("phone", phone);
                    userdataMap.put("password", pass);
                    userdataMap.put("User", user_select);

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