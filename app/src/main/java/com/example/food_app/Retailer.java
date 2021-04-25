package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.food_app.Prevalent.Prevalent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Retailer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageView fruits, vegetables, milk, bakeryDairyEggs, babyCare, personelCare, breakfastSnacks, beverages, essentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer);

        fruits = (ImageView) findViewById(R.id.fruits);
        vegetables = (ImageView) findViewById(R.id.vegetables);
        milk = (ImageView) findViewById(R.id.milk);
        bakeryDairyEggs = (ImageView) findViewById(R.id.bakeryDairyEggs);
        babyCare = (ImageView) findViewById(R.id.babyCare);
        personelCare = (ImageView) findViewById(R.id.personelCare);
        breakfastSnacks = (ImageView) findViewById(R.id.breakfastSnacks);
        beverages = (ImageView) findViewById(R.id.beverages);
        essentials = (ImageView) findViewById(R.id.essentials);


        Paper.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        toolbar.setTitle("Add New Product");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Retailer.this, CartActivity.class);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(Prevalent.currentOnlineUser.getName());
//        Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);



        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, AddNewProduct.class);
                intent.putExtra("category", "Fruits");
                startActivity(intent);
            }
        });
        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, AddNewProduct.class);
                intent.putExtra("category", "Vegetables");
                startActivity(intent);
            }
        });
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, AddNewProduct.class);
                intent.putExtra("category", "Milk");
                startActivity(intent);
            }
        });
        bakeryDairyEggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, AddNewProduct.class);
                intent.putExtra("category", "Dairy");
                startActivity(intent);
            }
        });
        babyCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, AddNewProduct.class);
                intent.putExtra("category", "BabyCare");
                startActivity(intent);
            }
        });
        beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, AddNewProduct.class);
                intent.putExtra("category", "Beverages");
                startActivity(intent);
            }
        });
        breakfastSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, AddNewProduct.class);
                intent.putExtra("category", "Snacks");
                startActivity(intent);
            }
        });
        personelCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, AddNewProduct.class);
                intent.putExtra("category", "Hygiene");
                startActivity(intent);
            }
        });
        essentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, AddNewProduct.class);
                intent.putExtra("category", "Essentials");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {
            Intent intent = new Intent(Retailer.this, DeleteActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_orders)
        {
            Intent intent = new Intent(Retailer.this, NewOrdersActivity.class);
            startActivity(intent);
        }
        else if (id ==R.id.nav_categories)
        {

        }
        else if (id ==R.id.nav_cart_retailers)
        {
            Intent intent = new Intent(Retailer.this, CartActivity.class);
            startActivity(intent);
        }
        else if (id ==R.id.nav_orders_retailers)
        {
            Intent intent = new Intent(Retailer.this, OrderStatusActivity.class);
            startActivity(intent);
        }
        else if (id ==R.id.nav_buy)
        {
            Intent intent = new Intent(Retailer.this, Order_Retailer_buy.class);
            startActivity(intent);

        }

        else if (id == R.id.nav_logout)
        {
            Paper.book().destroy();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}