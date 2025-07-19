package com.example.bikerentsadeesh;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SuggestionsAdapter extends RecyclerView.Adapter<SuggestionsAdapter.ViewHolder> {
    private List<Suggestion> suggestions;
    public SuggestionsAdapter(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    @NonNull
    @Override
    public SuggestionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionsAdapter.ViewHolder holder, int position) {
        Suggestion suggestion = suggestions.get(position);
        holder.BikeImage.setImageResource(suggestion.getImageId());
        holder.SuggestionName.setText(suggestion.getSuggestionName());
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView BikeImage;
        TextView SuggestionName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            BikeImage = itemView.findViewById(R.id.ivBikeImage);
            SuggestionName = itemView.findViewById(R.id.tvSuggestionName);
        }

    }
}