package com.habib.movie.movie;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.habib.movie.BuildConfig;
import com.habib.movie.R;
import com.habib.movie.beranda.HomeFragment;
import com.habib.movie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.habib.movie.beranda.HomeFragment.KEY_MOVIES;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    //private List<Movie> movies;
    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private ProgressBar bar;
    public final static String KEY_LIST = "movies";
    Parcelable parcelable;
    RecyclerView.LayoutManager manager;
    private SearchView searchView;
    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        recyclerView = view.findViewById(R.id.rv_movie);
        bar = view.findViewById(R.id.prog);
        setHasOptionsMenu(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movies = new ArrayList<>();
        //parcelable = manager.onSaveInstanceState();
        //AndroidNetworking.initialize(getContext());
        if(savedInstanceState == null){
            getMovie();
        }else{
            movies = (ArrayList<Movie>) savedInstanceState.getSerializable(KEY_LIST);
            MovieAdapter movieAdapter = new MovieAdapter(getContext(),movies);
            movieAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(movieAdapter);
            bar.setVisibility(View.INVISIBLE);
        }
        //getMovie();
        return view;
    }

    private void CariMovie(String s) {
        AndroidNetworking.get(BuildConfig.CARI_M+BuildConfig.API_KEY+BuildConfig.LANG_URL+BuildConfig.QM+s)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        movies.clear();
                        bar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray array = response.optJSONArray("results");
                            if(array.length() == 0){
                                Toast.makeText(requireContext(),R.string.empty,Toast.LENGTH_SHORT).show();
                            }
                            for(int i = 0 ;i<array.length();i++){
                                JSONObject object = array.optJSONObject(i);
                                movies.add(new Movie(
                                        object.getString("id"),
                                        object.getString("poster_path"),
                                        object.getString("title"),
                                        object.getString("vote_average"),
                                        object.getString("release_date"),
                                        object.getString("overview")
                                ));
                            }
                            MovieAdapter movieAdapter = new MovieAdapter(getContext(),movies);
                            movieAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(movieAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(requireContext(),R.string.con,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getMovie() {
        AndroidNetworking.get(BuildConfig.MOVIE_URL+BuildConfig.API_KEY+BuildConfig.LANG_URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        movies.clear();
                        bar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray array = response.optJSONArray("results");
                            if(array.length() == 0){
                                Toast.makeText(requireContext(),R.string.empty,Toast.LENGTH_SHORT).show();
                            }
                            for(int i = 0 ;i<array.length();i++){
                                JSONObject object = array.optJSONObject(i);
                                movies.add(new Movie(
                                    object.getString("id"),
                                    object.getString("poster_path"),
                                    object.getString("title"),
                                    object.getString("vote_average"),
                                    object.getString("release_date"),
                                    object.getString("overview")
                                ));
                            }
                            MovieAdapter movieAdapter = new MovieAdapter(getContext(),movies);
                            movieAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(movieAdapter);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(requireContext(),R.string.con,Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint(getString(R.string.cari_movie));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    getMovie();
                }else{
                    CariMovie(s);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(KEY_LIST,movies);
        super.onSaveInstanceState(outState);
    }

}
