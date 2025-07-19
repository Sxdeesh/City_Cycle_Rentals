package com.example.bikerentsadeesh;

public class Shop {
    private  int ShopImage;
    private String ShopName;
    private String Address;
    private String NumOfBikes;

    public Shop(int shopImage, String shopName, String address, String numOfBikes) {
        ShopImage = shopImage;
        ShopName = shopName;
        Address = address;
        NumOfBikes = numOfBikes;
    }

    public int getShopImage() {
        return ShopImage;
    }
    public String getShopName(){
        return ShopName;
    }
    public String getAddress() {
        return Address;
    }
    public String getNumOfBikes() {
        return NumOfBikes;
    }
}
