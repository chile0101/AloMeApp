package com.thesis.alome.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("imgUrls")
    @Expose
    private ImgUrl imgUrls;

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

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("term")
    @Expose
    private String term;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ImgUrl getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(ImgUrl imgUrls) {
        this.imgUrls = imgUrls;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}