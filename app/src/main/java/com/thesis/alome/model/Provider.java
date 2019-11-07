package com.thesis.alome.model;

public class Provider {
    private String nameProvider;
    private String imageUrl;
    private int numOfStars;
    private int numOfRatings;

    public String getNameProvider() {
        return nameProvider;
    }

    public void setNameProvider(String nameProvider) {
        this.nameProvider = nameProvider;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNumOfStars() {
        return numOfStars;
    }

    public void setNumOfStars(int numOfStars) {
        this.numOfStars = numOfStars;
    }

    public int getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(int numOfRatings) {
        this.numOfRatings = numOfRatings;
    }

    public Provider(String nameProvider, String imageUrl, int numOfStars, int numOfRatings) {
        this.nameProvider = nameProvider;
        this.imageUrl = imageUrl;
        this.numOfStars = numOfStars;
        this.numOfRatings = numOfRatings;
    }
}
