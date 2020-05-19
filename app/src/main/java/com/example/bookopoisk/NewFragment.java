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
public class NewFragment extends Fragment {

    public NewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_new, container, false);

        String[] bookNames = new String[Book.books.length];
        for (int i = 0; i < bookNames.length; i++) {
            bookNames[i] = Book.books[i].getName();
        }

        String[] bookAuthors = new String[Book.books.length];
        for (int i = 0; i < bookAuthors.length; i++) {
            bookAuthors[i] = Book.books[i].getAuthor();
        }

        int[] bookImages = new int[Book.books.length];
        for (int i = 0; i < bookImages.length; i++) {
            bookImages[i] = Book.books[i].getImageResourceId();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(bookNames, bookAuthors, bookImages);
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

        return recyclerView;
    }
}
