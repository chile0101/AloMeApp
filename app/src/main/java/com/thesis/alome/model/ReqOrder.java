package com.thesis.alome.model;

public class ReqOrder {
    private String date;
    private String time;
    private String phone;
    private String address;

    public ReqOrder(String date, String time, String phone, String address) {
        this.date = date;
        this.time = time;
        this.phone = phone;
        this.address = address;
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
}
