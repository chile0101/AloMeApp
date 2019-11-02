package com.thesis.alome.model;

public class ReqSignUp{
    private String username;
    private String password;
    private String fullName;
    private String phone;

    public ReqSignUp(String username, String password, String fullName, String phone) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
    }
}