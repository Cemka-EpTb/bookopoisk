package com.example.bookopoisk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendedFragment extends Fragment {

    public RecommendedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_recommended, container, false
        );

        String[] bookNames = new String[3];
        for (int i = 0; i < bookNames.length; i++) {
            bookNames[i] = Book.books[i * 3].getName();
        }

        String[] bookAuthor = new String[3];
        for (int i = 0; i < bookAuthor.length; i++) {
            bookAuthor[i] = Book.books[i * 3].getAuthor();
        }

        int[] bookImages = new int[3];
        for (int i = 0; i < bookImages.length; i++) {
            bookImages[i] = Book.books[i * 3].getImageResourceId();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(bookNames, bookAuthor, bookImages);
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra(BookDetailActivity.EXTRA_BOOK_ID, position * 3);
                getActivity().startActivity(intent);
            }
        });

        return recyclerView;
    }
}
