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

import com.example.bookopoisk.Api.MainApplication;
import com.example.bookopoisk.Api.ServerBookData;
import com.example.bookopoisk.Api.ResponseList;

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
    public void onTextChange(@NonNull String newText) {
        
        ServerBookData searchBook = new ServerBookData(newText, 1800, 2000);
        MainApplication.apiManager.searchBook(searchBook, new Callback<ResponseList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseList> call, @NonNull Response<ResponseList> response) {
                ResponseList responseBook = response.body();
                if (response.isSuccessful() && responseBook != null) {
                    if (!responseBook.getData().isEmpty()) {
                        ArrayList<ServerBookData> foundBooks = responseBook.getData();
                        int bookQuantity = foundBooks.size();
                        String[] bookNames = new String[bookQuantity];
                        String[] bookAuthors = new String[bookQuantity];
                        String[] bookImageUrls = new String[bookQuantity];
                        int[] bookId = new int[bookQuantity];

                        for (int i = 0; i < bookQuantity; i++) {
                            bookAuthors[i] = foundBooks.get(i).getAuthor().getName();
                            bookNames[i] = foundBooks.get(i).getName();
                            try {
                                bookImageUrls[i] = foundBooks.get(i).getImage().getUrl().concat("/125/175");
                            } catch (Exception e) {
                                bookImageUrls[i] = null;
                            }

                            bookId[i] = foundBooks.get(i).getId();
                        }

                        adapter = new CaptionedImagesAdapter(bookNames, bookAuthors, bookImageUrls, bookId);
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
            public void onFailure(Call<ResponseList> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });
    }
}
