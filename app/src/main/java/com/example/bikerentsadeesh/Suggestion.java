package com.example.bikerentsadeesh;

public class Suggestion {
    private int BikeImage;
    private String SuggestionName;

    public Suggestion(int imageId, String suggestionName) {
        this.BikeImage = imageId;
        this.SuggestionName = suggestionName;
    }
    public int getImageId() {
        return BikeImage;
    }
    public String getSuggestionName() {
        return SuggestionName;
    }
}
