package com.thesis.alome.model;

import android.text.Editable;

public class Comment {
    private String comment;
    private String name;
    private Long providerId;
    private Long customerRequestId;
    private Integer rating;

    public Comment(String comment, String name, Long providerId, Long customerRequestId ,Integer rating) {
        this.comment = comment;
        this.name = name;
        this.providerId = providerId;
        this.customerRequestId = customerRequestId;
        this.rating = rating;
    }

}
