package com.example.bookopoisk.serverApi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookopoiskApi {

    @POST("/api/searchBy")
    Call<ServerResponseModel> searchBook(@Body SearchBookModel serverBookData);

    @GET("/api/books/{id}")
    Call<ServerResponseModel> getBookInfo(@Path("id") int bookId);
}
