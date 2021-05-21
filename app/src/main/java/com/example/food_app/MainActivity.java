package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.food_app.Model.Users;
import com.example.food_app.Prevalent.Prevalent;
//import com.example.food_app.Retailer.Retailer;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button signUp;
    private Button login;
    private SignInButton google_signin;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 1;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private LoginButton loginButton;
    private static final String TAG1 = "FacebookAuthentication";
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    DatabaseReference reff;
    public TextView user;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp = findViewById(R.id.sign_up);
        login = findViewById(R.id.main_id_btn);
        user = findViewById(R.id.kindOfUser);
        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, login.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, social_sitesLogin.class);
                startActivity(intent);
            }
        });

        google_signin = (SignInButton) findViewById(R.id.google_Signup);
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        google_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


        String UserPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "")
        {
            if (!TextUtils.isEmpty(UserPhoneKey)  &&  !TextUtils.isEmpty(UserPasswordKey))
            {
                AllowAccess(UserPhoneKey, UserPasswordKey);

                loadingBar.setMessage("Please wait.....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }



        mFirebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG1, "onSuccess"+ loginResult);
                handleFacebookToken(loginResult.getAccessToken());
                Intent intent = new Intent(MainActivity.this, Customer.class);
                intent.putExtra("username", "user");
                startActivity(intent);
                String fb_id = loginResult.getAccessToken().getUserId();
//                FirebaseDatabase.getInstance().getReference("Facebook SignUp")
//                        .setValue(loginResult.getAccessToken().getUserId()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            Toast.makeText( MainActivity.this, "Your account is not created due to some error", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

                DatabaseReference rootRef;
                rootRef = FirebaseDatabase.getInstance().getReference();

                rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!(dataSnapshot.child("Facebook_Users").child(fb_id).exists())){
                            HashMap<String, Object> userdataMap = new HashMap<>();
                            userdataMap.put("Id", fb_id);

                            rootRef.child("Facebook_Users").child(fb_id).updateChildren(userdataMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                mFirebaseAuth.signOut();

            }

            @Override
            public void onCancel() {
                Log.d(TAG1, "onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG1, "onError"+ error);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    updateUI_fb(user);
                }
                else {
                    updateUI_fb(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null){
                    mFirebaseAuth.signOut();

                }
            }
        };

    }

    private void AllowAccess(final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users usersData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "You are already logged in...", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            if(usersData.getUser().compareTo("Customer")==0){
                                Intent intent = new Intent(MainActivity.this, Customer.class);
                                intent.putExtra("username", "login");
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                            else if(usersData.getUser().compareTo("Wholesaler")==0){
                                Intent intent = new Intent(MainActivity.this, WholeSaler.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }
                            else if(usersData.getUser().compareTo("Retailer")==0){
                                Intent intent = new Intent(MainActivity.this, Retailer.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }


                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this " + phone + " number do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void handleFacebookToken(AccessToken token) {
        Log.d(TAG1, "handleFacebookToken"+token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG1, "sign in with credentials: successful");
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    updateUI_fb(user);
                }
                else {
                    Log.d(TAG1, "sign in with credentials: failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    updateUI_fb(null);
                }
            }
        });
    }

    private void updateUI_fb(FirebaseUser user) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            mFirebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        mCallbackManager.onActivityResult(requestCode,resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedtask) {
        try{
            GoogleSignInAccount acc = completedtask.getResult(ApiException.class);
            Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e){
            Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            FirebaseGoogleAuth(null);


        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user1 = mAuth.getCurrentUser();
                    updateUI(user1);

                }
                else {
                    Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser fuser) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

//            FirebaseDatabase.getInstance().getReference("Gmail SignUp")
//                    .setValue(personEmail + " " + personName).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        Toast.makeText( MainActivity.this, "Your account is not created due to some error", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

            DatabaseReference rootRef;
            rootRef = FirebaseDatabase.getInstance().getReference();

            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!(dataSnapshot.child("Gmail_Users").child(personName).exists())){
                        HashMap<String, Object> userdataMap = new HashMap<>();
                        userdataMap.put("Name", personName);
                        userdataMap.put("Gmail id", personEmail);


                        rootRef.child("Gmail_Users").child(personName).updateChildren(userdataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            reff = FirebaseDatabase.getInstance().getReference().child("Gmail_Users").child(personName);
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                    String user_kind = datasnapshot.child("Name").getValue().toString();
                    user.setText(user_kind);
                    Intent intent = new Intent(MainActivity.this, Customer.class);
                    intent.putExtra("username", user.getText().toString());
                    startActivity(intent);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Toast.makeText(MainActivity.this, personName + " "+ personEmail, Toast.LENGTH_SHORT).show();
            mGoogleSignInClient.signOut();
        }
    }

}