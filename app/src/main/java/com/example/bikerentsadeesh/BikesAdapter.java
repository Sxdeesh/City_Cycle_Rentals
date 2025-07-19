package com.example.bikerentsadeesh;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class BikesAdapter extends RecyclerView.Adapter<BikesAdapter.ViewHolder> {
    private List<Bikes> bikesList = new ArrayList<>();
    private Context context;

    public BikesAdapter(List<Bikes> bikeList) {
        this.context = context;
        if (bikeList != null) {
            this.bikesList = bikeList;
        }
    }

    public void setsearchList(List<Bikes> filteredList) {
        this.bikesList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BikesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bike, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BikesAdapter.ViewHolder holder, int position) {
        Bikes bikes = bikesList.get(position);
        holder.BikesImage.setImageResource(bikes.getBikesImage());
        holder.BikeName.setText(bikes.getBikeName());
        holder.BikePrice.setText(bikes.getBikePrice());
        holder.btnBikeRent.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), Rent_Activity.class);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bikesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView BikesImage;
        TextView BikeName, BikePrice;
        Button btnBikeRent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BikesImage = itemView.findViewById(R.id.ivBikeImage);
            BikeName = itemView.findViewById(R.id.tvBikeName);
            BikePrice = itemView.findViewById(R.id.tvPrice);
            btnBikeRent =itemView.findViewById(R.id.btnBikeRent);
        }
    }
}
