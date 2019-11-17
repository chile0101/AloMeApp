package com.thesis.alome.model;

public class Job {
    private long id;
    private String urlImg;
    private String serviceName;
    private String date;
    private int status;

    public Job(long id, String urlImg, String serviceName, String date, int status) {
        this.id = id;
        this.urlImg = urlImg;
        this.serviceName = serviceName;
        this.date = date;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
