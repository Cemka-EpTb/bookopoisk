package com.example.bookopoisk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookopoisk.serverApi.MainApplication;
import com.example.bookopoisk.serverApi.SearchBookModel;
import com.example.bookopoisk.serverApi.ServerBookData;
import com.example.bookopoisk.serverApi.ServerResponseModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements MainActivity.SearchFragmentInterface {

    private RecyclerView recyclerView;
    private CaptionedImagesAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) context).searchFragmentInterface = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_search, container, false);

        return recyclerView;
    }

    @Override
    public RecyclerView onTextChange(String newText) {
        SearchBookModel searchBook = new SearchBookModel(newText, " ");
        MainApplication.apiManager.searchBook(searchBook, new Callback<ServerResponseModel>() {
            @Override
            public void onResponse(Call<ServerResponseModel> call, Response<ServerResponseModel> response) {
                ServerResponseModel responseBook = response.body();
                if (response.isSuccessful() && responseBook != null) {
                    if (!responseBook.getData().isEmpty()) {
                        ArrayList<ServerBookData> foundBooks = responseBook.getData();
                        int bookQuantity = foundBooks.size();
                        String[] bookNames = new String[bookQuantity];
                        String[] bookAuthors = new String[bookQuantity];
                        int[] bookImages = new int[bookQuantity];

                        for (int i = 0; i < bookQuantity; i++) {
                            bookAuthors[i] = foundBooks.get(i).getAuthorName();
                            bookNames[i] = foundBooks.get(i).getBookName();
                            bookImages[i] = R.drawable.image_not_found;
                        }

                        adapter = new CaptionedImagesAdapter(bookNames, bookAuthors, bookImages);
                        recyclerView.setAdapter(adapter);
                        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                        recyclerView.setLayoutManager(layoutManager);

                        adapter.setListener(new CaptionedImagesAdapter.Listener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                                intent.putExtra(BookDetailActivity.EXTRA_BOOK_ID, position);
                                getActivity().startActivity(intent);
                            }
                        });
                    } else {
                        recyclerView.setAdapter(null);
                    }
                } else {
                    Toast.makeText(getContext(),
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseModel> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

        return recyclerView;
    }
}
