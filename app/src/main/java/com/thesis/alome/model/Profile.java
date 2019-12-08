package com.thesis.alome.model;

public class Profile {
    private String dateOfbirth;
    private String fullName;
    private Boolean gender;
    private String phone;

    public Profile(String dateOfbirth, String fullName, Boolean gender, String phone) {
        this.dateOfbirth = dateOfbirth;
        this.fullName = fullName;
        this.gender = gender;
        this.phone = phone;
    }

    public String getDateOfbirth() {
        return dateOfbirth;
    }

    public void setDateOfbirth(String dateOfbirth) {
        this.dateOfbirth = dateOfbirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
