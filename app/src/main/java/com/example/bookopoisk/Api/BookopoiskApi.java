package com.example.bookopoisk.Api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookopoiskApi {

    @POST("/api/search")
    Call<ResponseList> searchBook(@Body ServerBookData serverBookData);

    @GET("/api/books/{id}")
    Call<ResponseObject> getBookInfo(@Path("id") int bookId);

    @GET("api/books/all")
    Call<ResponseList> getAllBooks();
}
