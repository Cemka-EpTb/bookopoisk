package com.example.bookopoisk.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerBookData {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("synopsis")
    @Expose
    private String synopsis;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("image")
    @Expose
    private BookImage image;

    @SerializedName("author")
    @Expose
    private Author author;

    @SerializedName("year_from")
    @Expose
    private int yearFrom;

    @SerializedName("year_to")
    @Expose
    private int yearTo;


    public ServerBookData(String name, int yearFrom, int yearTo) {
        this.name = name;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
    }

    public ServerBookData(int id) {
        this.id = id;
    }

    public Integer getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(Integer yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Integer getYearTo() {
        return yearTo;
    }

    public void setYearTo(Integer yearTo) {
        this.yearTo = yearTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public BookImage getImage() {
        return image;
    }

    public void setImage(BookImage image) {
        this.image = image;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}

