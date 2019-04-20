package com.habib.movie.favorite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.habib.movie.R;
import com.habib.movie.db.Movies;
import com.habib.movie.db.MoviesAdapter;
import java.util.ArrayList;
import static android.provider.BaseColumns._ID;
import static com.habib.movie.db.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;
import static com.habib.movie.db.DatabaseContract.MovieColumns.DATE_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.OVER_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.POSTER_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.RATE_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.TITLE_M;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView em;
    private MoviesAdapter adapter;
    private ArrayList<Movies>movies = new ArrayList<>();
    public FavMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_movie, container, false);
        recyclerView = view.findViewById(R.id.rv_movie);
        em = view.findViewById(R.id.empty);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI_MOVIE,null,null,null,null);
        adapter = new MoviesAdapter(getContext());
        movies = mapCursorTOArray(cursor);
        if(movies.isEmpty()){
            em.setVisibility(View.VISIBLE);
        }
        adapter.setFavMovies(movies);
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
