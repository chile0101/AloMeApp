package com.thesis.alome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provider {
    private Long providerId;
    private String name;
    private String serviceName;
    private Float numOfStars;
    private Integer numOfRatings;
    private String avatar;
    private Float price;
    private Boolean rated;

    @SerializedName("completedTime")
    @Expose
    private String promiseOfProvider;

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getRated() {
        return rated;
    }

    public void setRated(Boolean rated) {
        this.rated = rated;
    }

    public String getPromiseOfProvider() {
        return promiseOfProvider;
    }

    public void setPromiseOfProvider(String promiseOfProvider) {
        this.promiseOfProvider = promiseOfProvider;
    }
}
