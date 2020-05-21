package com.example.bookopoisk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.fragment.app.ListFragment;

public class GenresFragment extends ListFragment {

    public GenresFragment() {
        // Required empty public constructor
    }

    private ArrayAdapter<String> adapter;
    final String[] genresArray = new String[]{"Детективы", "Фантастика",
            "Фэнтези", "Ужасы", "Приключения", "Романы", "Боевики"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ArrayAdapter<String>(container.getContext(), android.R.layout.simple_list_item_1, genresArray);
        setListAdapter(adapter);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genres, container, false);
    }
}
