package com.example.bikerentsadeesh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private List<Shop> shopList;
    private RecyclerView rvShop;
    private ShopAdapter shopAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        rvShop = view.findViewById(R.id.rvShop);
        shopList = new ArrayList<>();
        for (int i = 0; i<1;i++){
            shopList.add(new Shop(R.drawable.colombo, "Colombo Branch", "125 Galle Road, Colombo 03, Sri Lanka","10"));
            shopList.add(new Shop(R.drawable.kandy, "Kandy", "42 Peradeniya Road, Kandy 20000, Sri Lanka","20"));
            shopList.add(new Shop(R.drawable.galle, "Galle", "58 Wakwella Road, Galle 80000, Sri Lanka", "15"));
        }
        shopAdapter = new ShopAdapter(shopList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvShop.setLayoutManager(linearLayoutManager);
        rvShop.setAdapter(shopAdapter);
        return view;

    }
}