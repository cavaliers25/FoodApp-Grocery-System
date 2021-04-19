package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class WholeSaler extends AppCompatActivity {
    private ImageView fruits, vegetables, milk, bakeryDairyEggs, babyCare, personelCare, breakfastSnacks, beverages, essentials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_saler);

        fruits = (ImageView) findViewById(R.id.fruits);
        vegetables = (ImageView) findViewById(R.id.vegetables);
        milk = (ImageView) findViewById(R.id.milk);
        bakeryDairyEggs = (ImageView) findViewById(R.id.bakeryDairyEggs);
        babyCare = (ImageView) findViewById(R.id.babyCare);
        personelCare = (ImageView) findViewById(R.id.personelCare);
        breakfastSnacks = (ImageView) findViewById(R.id.breakfastSnacks);
        beverages = (ImageView) findViewById(R.id.beverages);
        essentials = (ImageView) findViewById(R.id.essentials);

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
}