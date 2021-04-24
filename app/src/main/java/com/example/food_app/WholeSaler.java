package com.example.food_app;

import android.content.Intent;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.Interface.ItemClickListener;
import com.example.food_app.Model.Category;
import com.example.food_app.Model.Products;
import com.example.food_app.Prevalent.Prevalent;
import com.example.food_app.ViewHolder.MenuViewHolder;
import com.example.food_app.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;


public class WholeSaler extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference category;
    RecyclerView.LayoutManager layoutManager;
    //    RecyclerView.Adapter adapter;
    FirebaseDatabase firebase;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    private ImageView fruits, vegetables, milk, bakeryDairyEggs, babyCare, personelCare, breakfastSnacks, beverages, essentials;


    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_saler2);


        category = FirebaseDatabase.getInstance().getReference().child("Category");

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        toolbar.setTitle("Add New Product");
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);

        userNameTextView.setText(Prevalent.currentOnlineUser.getName());



        fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WholeSaler.this, AddNewProduct.class);
                intent.putExtra("category", "Fruits");
                startActivity(intent);
            }
        });
        vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WholeSaler.this, AddNewProduct.class);
                intent.putExtra("category", "Vegetables");
                startActivity(intent);
            }
        });
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WholeSaler.this, AddNewProduct.class);
                intent.putExtra("category", "Milk");
                startActivity(intent);
            }
        });
        bakeryDairyEggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WholeSaler.this, AddNewProduct.class);
                intent.putExtra("category", "Dairy");
                startActivity(intent);
            }
        });
        babyCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WholeSaler.this, AddNewProduct.class);
                intent.putExtra("category", "BabyCare");
                startActivity(intent);
            }
        });
        beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WholeSaler.this, AddNewProduct.class);
                intent.putExtra("category", "Beverages");
                startActivity(intent);
            }
        });
        breakfastSnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WholeSaler.this, AddNewProduct.class);
                intent.putExtra("category", "Snacks");
                startActivity(intent);
            }
        });
        personelCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WholeSaler.this, AddNewProduct.class);
                intent.putExtra("category", "Hygiene");
                startActivity(intent);
            }
        });
        essentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WholeSaler.this, AddNewProduct.class);
                intent.putExtra("category", "Essentials");
                startActivity(intent);
            }
        });


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wholesaler, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

//        if (id == R.id.action_settings)
//        {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {
            Intent intent = new Intent(WholeSaler.this, DeleteActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_orders)
        {
            Intent intent = new Intent(WholeSaler.this, NewOrdersActivity.class);
            startActivity(intent);
        }
        else if (id ==R.id.nav_categories)
        {

        }

        else if (id == R.id.nav_logout)
        {
            Paper.book().destroy();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout1);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}