package com.thesis.alome.model;

import java.util.List;

public class JobDetails {
    private Long id;
    private int status;
    private String createdAt;

    private String serviceName;
    private String time;
    private String date;
    private String address;
    private List<String> images;
    private String description;

    public JobDetails(int status, String createdAt, String time, String date, String address, List<String> imgUrls,String description) {
        this.status = status;
        this.createdAt = createdAt;
        this.time = time;
        this.date = date;
        this.address = address;
        this.images = imgUrls;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
