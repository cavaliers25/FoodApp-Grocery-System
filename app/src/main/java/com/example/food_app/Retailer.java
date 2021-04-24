package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import io.paperdb.Paper;

public class Retailer extends AppCompatActivity {
    private ImageView fruits, vegetables, milk, bakeryDairyEggs, babyCare, personelCare, breakfastSnacks, beverages, essentials;
    private Button logoutBtn, checkOrersBtn, delete_product_btn;

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
        logoutBtn = (Button) findViewById(R.id.admin_logout_btn);
        checkOrersBtn = (Button) findViewById(R.id.check_orders_btn);
        delete_product_btn = (Button) findViewById(R.id.delete_product_btn);

        delete_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Retailer.this, DeleteActivity.class);
                startActivity(intent);

            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().destroy();
                Intent intent = new Intent(Retailer.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        checkOrersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Retailer.this, NewOrdersActivity.class);
                startActivity(intent);
            }
        });

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
}