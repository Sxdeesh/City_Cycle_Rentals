package com.example.bikerentsadeesh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
    private List<Services> servicesList;

    public ServicesAdapter(List<Services> servicesList){
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_services, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {
        Services services = servicesList.get(position);
        holder.ivServicesImage.setImageResource(services.getServicesImage());
        holder.tvService.setText(services.getService());
        holder.tvDescription.setText(services.getDescription());
    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivServicesImage;
        TextView tvService;
        TextView tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivServicesImage = itemView.findViewById(R.id.ivServicesImage);
            tvService =itemView.findViewById(R.id.tvService);
            tvDescription=itemView.findViewById(R.id.tvDescription);
        }
    }
}
