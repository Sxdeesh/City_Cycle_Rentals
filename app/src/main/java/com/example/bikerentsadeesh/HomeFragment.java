package com.example.bikerentsadeesh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Suggestion> suggestions;
    private List<Services> services;
    private RecyclerView rvSuggestions, rvServices;
    private SuggestionsAdapter suggestionsAdapter;
    private ServicesAdapter servicesAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvSuggestions=view.findViewById(R.id.rvSuggestions);
        rvServices=view.findViewById(R.id.rvServices);
        suggestions = new ArrayList<>();
        services = new ArrayList<>();
        for (int i =0; i<1; i++){
            services.add(new Services(R.drawable.img1, "City rides", "Explore your city and enjoy your world"));
            services.add(new Services(R.drawable.img2, "Go for a hike", "Explore the serene beauty of nature"));
        }
        for (int i = 0; i < 1; i++) {
            suggestions.add(new Suggestion(R.drawable.mountain_bike, "Mountain Bike"));
            suggestions.add(new Suggestion(R.drawable.road_bike, "Road bike"));
            suggestions.add(new Suggestion(R.drawable.ebike, "Electric bike"));
        }
        suggestionsAdapter = new SuggestionsAdapter(suggestions);
        servicesAdapter = new ServicesAdapter(services);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvSuggestions.setLayoutManager(linearLayoutManager);
        rvSuggestions.setAdapter(suggestionsAdapter);
        LinearLayoutManager linearLayoutManager1=new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvServices.setLayoutManager(linearLayoutManager1);
        rvServices.setAdapter(servicesAdapter);
        return view;

    }
}