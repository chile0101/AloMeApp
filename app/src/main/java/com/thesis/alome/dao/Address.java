package com.thesis.alome.dao;

public class Address {
    private String typeOfArea;
    private String addressStr;

    public Address(String typeOfArea, String addressStr) {
        this.typeOfArea = typeOfArea;
        this.addressStr = addressStr;
    }

    public String getTypeOfArea() {
        return typeOfArea;
    }

    public void setTypeOfArea(String typeOfArea) {
        this.typeOfArea = typeOfArea;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }
}
