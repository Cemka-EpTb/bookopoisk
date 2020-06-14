package com.example.bookopoisk.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static BookopoiskApi service;
    private static ApiManager apiManager;

    private final String BASE_URL = "http://192.168.12.12/";

    private ApiManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(BookopoiskApi.class);
    }

    static ApiManager getInstance() {
        if (apiManager == null) {
            apiManager = new ApiManager();
        }
        return apiManager;
    }

    public void searchBook(ServerBookData book, Callback<ResponseList> callback) {
        Call<ResponseList> bookCall = service.searchBook(book);
        bookCall.enqueue(callback);
    }

    public void getBookInfo(int bookId, Callback<ResponseObject> callback) {
        Call<ResponseObject> bookInfo = service.getBookInfo(bookId);
        bookInfo.enqueue(callback);
    }

    public void getAllBooks(Callback<ResponseList> callback) {
        Call<ResponseList> allBooks = service.getAllBooks();
        allBooks.enqueue(callback);
    }
}
