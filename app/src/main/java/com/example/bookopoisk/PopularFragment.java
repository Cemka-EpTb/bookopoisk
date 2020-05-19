package com.example.bookopoisk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_popular, container, false
        );

        String[] bookNames = new String[5];
        for (int i = 0; i < bookNames.length; i++) {
            bookNames[i] = Book.books[i * 2].getName();
        }

        String[] bookAuthor = new String[5];
        for (int i = 0; i < bookAuthor.length; i++) {
            bookAuthor[i] = Book.books[i * 2].getAuthor();
        }

        int[] bookImages = new int[5];
        for (int i = 0; i < bookImages.length; i++) {
            bookImages[i] = Book.books[i * 2].getImageResourceId();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(bookNames, bookAuthor, bookImages);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra(BookDetailActivity.EXTRA_BOOK_ID, position * 2);
                getActivity().startActivity(intent);
            }
        });

        return recyclerView;
    }
}
