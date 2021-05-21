package com.example.food_app;

import android.content.Intent;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
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


public class Customer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private DatabaseReference category;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    RecyclerView.Adapter adapter;
    FirebaseDatabase firebase;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    String username = "";



    DatabaseReference reff;


    String[] categories_list = {"Fruits", "Vegetables", "Milk", "Essentials", "Beverages", "Dairy", "Snacks", "Hygiene", "BabyCare"};

    int[] categories_images = {R.drawable.fruits, R.drawable.vegetables, R.drawable.milk, R.drawable.oils, R.drawable.beverages, R.drawable.bakerydairyeggs, R.drawable.snacks, R.drawable.personalcare, R.drawable.babycare};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);


        category = FirebaseDatabase.getInstance().getReference().child("Category");

        if(getIntent() != null){
            username = getIntent().getStringExtra("username");
        }




        Paper.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Customer.this, CartActivity.class);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);



        if(username != null && !(username.isEmpty()) && (username.compareTo("user")==0)){
            userNameTextView.setText("Facebook User");
        }
        else if (username != null && !(username.isEmpty()) && (username.compareTo("login")==0)){
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);
        }
        else {
            userNameTextView.setText(username);
        }

        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadMenu();
    }



    private void loadMenu() {

        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(category, Category.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Category model) {

                holder.txtMenuName.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                final Category clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//                        Toast.makeText(Customer.this, clickItem.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Customer.this, CategoriesActivity.class);
                        intent.putExtra("CategoryID", clickItem.getName().toString());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                View view = inflater.inflate(R.layout.items_layout, parent, false);
                MenuViewHolder viewHolder = new MenuViewHolder(view);
                return viewHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
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
        getMenuInflater().inflate(R.menu.customer, menu);
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
            Intent intent = new Intent(Customer.this, CartActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_orders)
        {
            Intent intent = new Intent(Customer.this, OrderStatusActivity.class);
            startActivity(intent);
        }
        else if (id ==R.id.nav_categories)
        {

        }
        else if (id ==R.id.nav_search)
        {
            Intent intent = new Intent(Customer.this, SearchProductActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_settings)
        {
            Intent intent = new Intent(this, SettinsActivity.class);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}