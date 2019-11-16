package com.thesis.alome.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "address")
public class Address {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "type_of_area")
    private String typeOfArea;

    @ColumnInfo(name = "address_str")
    private String addressStr;

    @ColumnInfo(name = "lat")
    private Double lat;

    @ColumnInfo(name = "long")
    private Double lng;

    public Address(String typeOfArea, String addressStr, Double lat, Double lng) {
        this.typeOfArea = typeOfArea;
        this.addressStr = addressStr;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
