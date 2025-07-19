package com.example.bikerentsadeesh;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private List<Bikes> bikesList;
    private RecyclerView rvBikes;
    private BikesAdapter bikesAdapter;
    private EditText etSearch;
    private Button btnSearch, btnBikeRent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rvBikes = view.findViewById(R.id.rvBikes);
        rvBikes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        bikesList = new ArrayList<>();
        bikesList.add(new Bikes(R.drawable.mountain_bike, "Mountain Bike", "Rs.1500 per day"));
        bikesList.add(new Bikes(R.drawable.road_bike, "Road Bike", "Rs.1000 per day"));
        bikesList.add(new Bikes(R.drawable.ebike, "Electric Bike", "Rs.500 per day"));
        bikesList.add(new Bikes(R.drawable.city_bike, "City Bike", "Rs.1200 per day"));
        bikesList.add(new Bikes(R.drawable.hybrid_bike, "Hybrid Bike", "Rs.1800 per day"));
        bikesList.add(new Bikes(R.drawable.touring_bike, "Touring Bike", "Rs.2000 per day"));
        bikesList.add(new Bikes(R.drawable.folding_bike, "Folding Bike", "Rs.1500 per day"));
        bikesList.add(new Bikes(R.drawable.cargo_bike, "Cargo Bike", "Rs.1200 per day"));
        bikesList.add(new Bikes(R.drawable.bmx, "BMX Bike", "Rs.1000 per day"));
        bikesList.add(new Bikes(R.drawable.track_bike, "Track Bike", "Rs.1500 per day"));
        bikesList.add(new Bikes(R.drawable.road_bike, "Road Bike", "Rs.1000 per day"));
        bikesList.add(new Bikes(R.drawable.ebike, "Electric Bike", "Rs. 500 per day"));

        bikesAdapter = new BikesAdapter(bikesList);
        rvBikes.setAdapter(bikesAdapter);
        rvBikes.setVisibility(View.VISIBLE);

        etSearch = view.findViewById(R.id.etBikeSearch);
        btnSearch = view.findViewById(R.id.btnBikesSearch);

        btnSearch.setOnClickListener(v -> {
            String searchText = etSearch.getText().toString().trim();
            filterList(searchText);
        });

        return view;
    }

    private void filterList(String text) {
        List<Bikes> searchlist = new ArrayList<>();
        for (Bikes item : bikesList) {
            if (item.getBikeName().toLowerCase().contains(text.toLowerCase())) {
                searchlist.add(item);
            }
        }
        if (searchlist.isEmpty()) {
            Toast.makeText(getContext(), "No bicycles found", Toast.LENGTH_SHORT).show();
        } else {
            bikesAdapter.setsearchList(searchlist);
        }
    }
}
