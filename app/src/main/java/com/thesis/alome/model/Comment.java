package com.thesis.alome.model;

import android.text.Editable;

public class Comment {
    private String comment;
    private String name;
    private Long providerId;
    private float rating;

    public Comment(String comment, String name, Long providerId, float rating) {
        this.comment = comment;
        this.name = name;
        this.providerId = providerId;
        this.rating = rating;
    }

}
