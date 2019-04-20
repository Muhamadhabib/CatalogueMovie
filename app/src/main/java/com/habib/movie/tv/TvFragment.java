package com.habib.movie.tv;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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
import com.habib.movie.model.Tv;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    public static final String KEY_TV = "tv";
    //private List<Tv> tvs;
    private ArrayList<Tv> tvs;
    private RecyclerView recyclerView;
    private ProgressBar bar;
    private SearchView searchView;
    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        recyclerView = view.findViewById(R.id.rv_tv);
        setHasOptionsMenu(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tvs = new ArrayList<>();
        bar = view.findViewById(R.id.prog_tv);
        //AndroidNetworking.initialize(getContext());

        if (savedInstanceState == null){
            getTv();
        }else {
            tvs = (ArrayList<Tv>) savedInstanceState.getSerializable(KEY_TV);
            TvAdapter tvAdapter = new TvAdapter(getContext(),tvs);
            tvAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(tvAdapter);
            bar.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private void getTv() {
        AndroidNetworking.get(BuildConfig.TV_URL+BuildConfig.API_KEY+BuildConfig.LANG_URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        tvs.clear();
                        bar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray array = response.optJSONArray("results");
                            if(array.length() == 0){
                                Toast.makeText(requireContext(),R.string.empty,Toast.LENGTH_SHORT).show();
                            }
                            for(int i = 0 ;i<array.length();i++){
                                JSONObject object = array.optJSONObject(i);
                                tvs.add(new Tv(
                                        object.getString("id"),
                                        object.getString("poster_path"),
                                        object.getString("name"),
                                        object.getString("vote_average"),
                                        object.getString("first_air_date"),
                                        object.getString("overview")
                                ));
                            }
                            TvAdapter tvAdapter = new TvAdapter(getContext(),tvs);
                            tvAdapter.notifyDataSetChanged();
                            recyclerView.setAdapter(tvAdapter);
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
    private void CariTv(String s) {
        AndroidNetworking.get(BuildConfig.CARI_T+BuildConfig.API_KEY+BuildConfig.LANG_URL+BuildConfig.QM+s)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    tvs.clear();
                    bar.setVisibility(View.INVISIBLE);
                    try {
                        JSONArray array = response.optJSONArray("results");
                        if(array.length() == 0){
                            Toast.makeText(requireContext(),R.string.empty,Toast.LENGTH_SHORT).show();
                        }
                        for(int i = 0 ;i<array.length();i++){
                            JSONObject object = array.optJSONObject(i);
                            tvs.add(new Tv(
                                    object.getString("id"),
                                    object.getString("poster_path"),
                                    object.getString("name"),
                                    object.getString("vote_average"),
                                    object.getString("first_air_date"),
                                    object.getString("overview")
                            ));
                        }
                        TvAdapter tvAdapter = new TvAdapter(getContext(),tvs);
                        tvAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(tvAdapter);
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
        searchView.setQueryHint(getString(R.string.cari_Tv));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(TextUtils.isEmpty(s)){
                    getTv();
                }else{
                    CariTv(s);
                }
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putSerializable(KEY_TV,tvs);
        super.onSaveInstanceState(outState);
    }
}
