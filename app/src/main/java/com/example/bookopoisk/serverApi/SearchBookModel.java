package com.example.bookopoisk.serverApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchBookModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("book_id")
    @Expose
    private Integer bookId;

    @SerializedName("author_id")
    @Expose
    private Integer authorId;

    @SerializedName("author_name")
    @Expose
    private String authorName;

    @SerializedName("book_name")
    @Expose
    private String bookName;

    @SerializedName("published_at")
    @Expose
    private String publishedAt;

    public SearchBookModel(String bookName, String authorName) {
        this.bookName = bookName;
        this.authorName = authorName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}