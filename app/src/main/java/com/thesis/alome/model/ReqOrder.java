package com.thesis.alome.model;

import android.net.Uri;

import java.util.List;

public class ReqOrder {
    private int serviceId;
    private String date;
    private String time;
    private String phone;
    private String address;
    private String description;
    private List<Uri> images;

    public ReqOrder(int serviceId, String date, String time, String phone, String address, String decription, List<Uri> images) {
        this.serviceId = serviceId;
        this.date = date;
        this.time = time;
        this.phone = phone;
        this.address = address;
        this.description = decription;
        this.images = images;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDecription() {
        return description;
    }

    public void setDecription(String decription) {
        this.description = decription;
    }

    public List<Uri> getImages() {
        return images;
    }

    public void setImages(List<Uri> images) {
        this.images = images;
    }
}
