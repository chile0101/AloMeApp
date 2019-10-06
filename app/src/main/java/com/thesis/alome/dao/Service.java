package com.thesis.alome.dao;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("fullPrice")
    @Expose
    private double fullPrice;
    @SerializedName("salePrice")
    @Expose
    private double salePrice;

    public Service() {
    }

    public Service(long id, String imageUrl, String serviceName, double fullPrice, double salePrice) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.serviceName = serviceName;
        this.fullPrice = fullPrice;
        this.salePrice = salePrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(double fullPrice) {
        this.fullPrice = fullPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

}