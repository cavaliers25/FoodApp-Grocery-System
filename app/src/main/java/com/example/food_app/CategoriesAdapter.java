package com.example.food_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.Interface.ItemClickListener;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    Context context;
    String[] categories_list;
    int[] categories_images;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView product_name1;
        ImageView image;
        public ItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            product_name1 = itemView.findViewById(R.id.product_name1);
            image = itemView.findViewById(R.id.image);
        }

        public void setItemClickListener(ItemClickListener listener){
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition(), false);
        }
    }

    public CategoriesAdapter(Context context, String[] categories_list, int[] categories_images){
        this.context = context;
        this.categories_images = categories_images;
        this.categories_list = categories_list;
    }

}
