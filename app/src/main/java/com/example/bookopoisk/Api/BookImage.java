package com.example.bookopoisk.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookImage {

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    public String getUrl() {
        return imageUrl;
    }

    public void setUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
