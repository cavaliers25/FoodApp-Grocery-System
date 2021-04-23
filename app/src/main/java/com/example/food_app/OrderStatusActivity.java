package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.food_app.Model.AdminOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatusActivity extends AppCompatActivity {

    RecyclerView orderslist;
    private DatabaseReference ordersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        orderslist = findViewById(R.id.order_recycler);
        orderslist.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef, AdminOrders.class)
                        .build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int position, @NonNull AdminOrders model) {
                        holder.userPhoneNumber.setText(model.getPhone());
                        holder.userTotalPrice.setText("Total Amount: ₹" + model.getTotalAmount());
                        holder.userShippingAddress.setText(model.getAddress() + ", " + model.getCity() + ", " + model.getPincode());

                        holder.trackOrder.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(OrderStatusActivity.this, trackOrderActivity.class);
                                intent.putExtra("phone", model.getPhone());
                                startActivity(intent);
                            }
                        });

                        holder.feedback.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(OrderStatusActivity.this, feedbackActivity.class);
                                intent.putExtra("phone", model.getPhone());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };


        orderslist.setAdapter(adapter);
        adapter.startListening();

    }

//    private void RemoveOrder(String uID) {
//
//        ordersRef.child(uID).removeValue();
//
//    }

    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder {

        public TextView userPhoneNumber, userTotalPrice, userShippingAddress;
        public Button trackOrder, feedback;


        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userPhoneNumber = itemView.findViewById(R.id.order_phone_status);
            userTotalPrice = itemView.findViewById(R.id.order_price_status);
            userShippingAddress = itemView.findViewById(R.id.order_address_status);
            trackOrder = itemView.findViewById(R.id.track_order);
            feedback = itemView.findViewById(R.id.feedback_order);


        }
    }

    ;

}