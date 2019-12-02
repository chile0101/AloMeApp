package com.thesis.alome.model;

public class Provider {
    private Integer providerId;
    private String name;
    private String serviceName;
    private Float numOfStars;
    private Integer numOfRatings;
    private String avatar;
    //private String price;

    public Provider(Integer providerId, String name, String serviceName, Float numOfStars, Integer numOfRatings, String avatar) {
        this.providerId = providerId;
        this.name = name;
        this.serviceName = serviceName;
        this.numOfStars = numOfStars;
        this.numOfRatings = numOfRatings;
        this.avatar = avatar;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Float getNumOfStars() {
        return numOfStars;
    }

    public void setNumOfStars(Float numOfStars) {
        this.numOfStars = numOfStars;
    }

    public Integer getNumOfRatings() {
        return numOfRatings;
    }

    public void setNumOfRatings(Integer numOfRatings) {
        this.numOfRatings = numOfRatings;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
