package com.thesis.alome.model;

public class ReqSignUp{
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private String longitude;
    private String latitude;

    public ReqSignUp(String username, String password, String fullName, String phone, String address, String longitude, String latitude
    ) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}