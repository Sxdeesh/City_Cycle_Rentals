package com.example.bikerentsadeesh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<RentedBike> rentedBikeList;
    private Context context;

    public CartAdapter(Context context, List<RentedBike> rentedBikeList) {
        this.context = context;
        this.rentedBikeList = rentedBikeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RentedBike bike = rentedBikeList.get(position);
        holder.tvBikeType.setText(bike.getBikeType());
        holder.tvRentalDate.setText(bike.getRentalDate());
        holder.tvReturnDate.setText(bike.getReturnDate());
    }

    @Override
    public int getItemCount() {
        return rentedBikeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBikeType, tvRentalDate, tvReturnDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBikeType = itemView.findViewById(R.id.tvBikeType);
            tvRentalDate = itemView.findViewById(R.id.tvRentalDate);
            tvReturnDate = itemView.findViewById(R.id.tvReturnDate);
        }
    }
}

