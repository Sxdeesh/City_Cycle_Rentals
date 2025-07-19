package com.example.bikerentsadeesh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<Shop> shopList;

    public ShopAdapter(List<Shop>shopList) {
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
        Shop shop = shopList.get(position);
        holder.ivShopImage.setImageResource(shop.getShopImage());
        holder.tvShopName.setText(shop.getShopName());
        holder.tvAddress.setText(shop.getAddress());
        holder.tvNumOfBikes.setText(shop.getNumOfBikes());

    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivShopImage;
        TextView tvShopName;
        TextView tvAddress;
        TextView tvNumOfBikes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivShopImage = itemView.findViewById(R.id.ivShopImage);
            tvShopName = itemView.findViewById(R.id.tvShopName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvNumOfBikes = itemView.findViewById(R.id.tvNumOfBikes);

        }
    }
}
