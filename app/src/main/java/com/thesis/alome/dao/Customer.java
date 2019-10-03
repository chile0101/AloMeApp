package com.thesis.alome.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("genderText")
    @Expose
    private String genderText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGenderText() {
        return genderText;
    }

    public void setGenderText(String genderText) {
        this.genderText = genderText;
    }
}
