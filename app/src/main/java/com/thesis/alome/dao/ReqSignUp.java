package com.thesis.alome.dao;

public class ReqSignUp{
    private String username;
    private String password;
    private String fullName;

    public ReqSignUp(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }
}