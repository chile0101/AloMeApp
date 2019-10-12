package com.thesis.alome.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImgUrl {

    @SerializedName("imgUrl1")
    @Expose
    private String imgUrl1;
    @SerializedName("imgUrl2")
    @Expose
    private String imgUrl2;
    @SerializedName("imgUrl3")
    @Expose
    private String imgUrl3;

    public String getImgUrl1() {
        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    public String getImgUrl3() {
        return imgUrl3;
    }

    public void setImgUrl3(String imgUrl3) {
        this.imgUrl3 = imgUrl3;
    }

}