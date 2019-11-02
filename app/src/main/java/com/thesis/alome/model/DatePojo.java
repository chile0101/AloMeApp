package com.thesis.alome.model;

public class DatePojo {
    private String dayofweek;
    private String dateFormat;

    public DatePojo(String dayofweek, String dateFormat) {
        this.dayofweek = dayofweek;
        this.dateFormat = dateFormat;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
