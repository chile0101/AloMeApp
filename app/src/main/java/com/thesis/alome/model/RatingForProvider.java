package com.thesis.alome.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingForProvider {

    @SerializedName("name")
    @Expose
    private String nameAssessor;

    @SerializedName("rating")
    @Expose
    private Float numOfStars;

    @SerializedName("comment")
    @Expose
    private String textComment;

    @SerializedName("createAt")
    @Expose
    private String createAt;

    public RatingForProvider(String nameAssessor, Float numOfStars, String textComment, String createAt) {
        this.nameAssessor = nameAssessor;
        this.numOfStars = numOfStars;
        this.textComment = textComment;
        this.createAt = createAt;
    }

    public String getNameAssessor() {
        return nameAssessor;
    }

    public void setNameAssessor(String nameAssessor) {
        this.nameAssessor = nameAssessor;
    }

    public Float getNumOfStars() {
        return numOfStars;
    }

    public void setNumOfStars(Float numOfStars) {
        this.numOfStars = numOfStars;
    }

    public String getTextComment() {
        return textComment;
    }

    public void setTextComment(String textComment) {
        this.textComment = textComment;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
