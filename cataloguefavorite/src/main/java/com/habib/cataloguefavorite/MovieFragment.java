package com.habib.cataloguefavorite;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.habib.cataloguefavorite.adapter.MovieAdapter;
import com.habib.cataloguefavorite.entity.Movies;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.habib.cataloguefavorite.db.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;
import static com.habib.cataloguefavorite.db.DatabaseContract.MovieColumns.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Movies>movies = new ArrayList<>();
    private MovieAdapter adapter;
    private TextView em;
    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = view.findViewById(R.id.rv_movie);
        em = view.findViewById(R.id.empty);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI_MOVIE,null,null,null,null);
        adapter = new MovieAdapter(getContext());
        movies = mapCursorTOArray(cursor);
        if(movies.isEmpty()){
            em.setVisibility(View.VISIBLE);
        }
        adapter.setMovies(movies);
        recyclerView.setAdapter(adapter);
        return view;
    }
    public static ArrayList<Movies> mapCursorTOArray(Cursor cursor) {
        ArrayList<Movies> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndexOrThrow(_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE_M));
            String rate = cursor.getString(cursor.getColumnIndexOrThrow(RATE_M));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DATE_M));
            String over = cursor.getString(cursor.getColumnIndexOrThrow(OVER_M));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER_M));
            list.add(new Movies(id,title,rate,date,over,poster));
        }
        return list;
    }
}
