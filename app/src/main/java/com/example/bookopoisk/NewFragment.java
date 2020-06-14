package com.example.bookopoisk;

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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewFragment extends Fragment {

    public NewFragment() {
        // Required empty public constructor
    }

    private CaptionedImagesAdapter adapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_new, container, false);

        MainApplication.apiManager.getAllBooks(new Callback<ResponseList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseList> call, @NonNull Response<ResponseList> response) {
                ResponseList responseBook = response.body();
                if (response.isSuccessful() && responseBook != null) {
                    ArrayList<ServerBookData> allBooks = responseBook.getData();
                    int bookQuantity = allBooks.size();
                    String[] bookNames = new String[bookQuantity];
                    String[] bookAuthors = new String[bookQuantity];
                    String[] bookImagesUrl = new String[bookQuantity];
                    int[] bookId = new int[bookQuantity];

                    for (int i = 0; i < bookQuantity; i++) {
                        bookAuthors[i] = allBooks.get(i).getAuthor().getName();
                        bookNames[i] = allBooks.get(i).getName();
                        bookId[i] = allBooks.get(i).getId();
                        bookImagesUrl[i] = allBooks.get(i).getImage().getUrl().concat("/120/170");
                    }

                    adapter = new CaptionedImagesAdapter(bookNames, bookAuthors, bookImagesUrl, bookId);
                    recyclerView.setAdapter(adapter);
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
                    recyclerView.setLayoutManager(layoutManager);

                    adapter.setListener(new CaptionedImagesAdapter.Listener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                            intent.putExtra(BookDetailActivity.EXTRA_BOOK_ID, position);
                            Objects.requireNonNull(getActivity()).startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(getContext(),
                            String.format("Response is %s", String.valueOf(response.code()))
                            , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseList> call, @NonNull Throwable t) {
                Toast.makeText(container.getContext(),
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

        return recyclerView;
    }
}
