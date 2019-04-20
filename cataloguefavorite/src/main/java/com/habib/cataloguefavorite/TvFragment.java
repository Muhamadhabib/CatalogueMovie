package com.habib.cataloguefavorite;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.habib.cataloguefavorite.adapter.TvAdapter;
import com.habib.cataloguefavorite.entity.Tvs;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.habib.cataloguefavorite.db.DatabaseContract.TvColumns.CONTENT_URI_TV;
import static com.habib.cataloguefavorite.db.DatabaseContract.TvColumns.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {
    private RecyclerView recyclerView;
    private TvAdapter adapter;
    private ArrayList<Tvs> tvs = new ArrayList<>();
    private TextView em;

    public TvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        recyclerView = view.findViewById(R.id.rv_tv);
        em = view.findViewById(R.id.empty);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Cursor cursor = getContext().getContentResolver().query(CONTENT_URI_TV,null,null,null,null);
        adapter = new TvAdapter(getContext());
        tvs = mapCursorTOArray(cursor);
        if(tvs.isEmpty()){
            em.setVisibility(View.VISIBLE);
        }
        adapter.setTvs(tvs);
        recyclerView.setAdapter(adapter);
        return view;
    }
    public static ArrayList<Tvs> mapCursorTOArray(Cursor cursor) {
        ArrayList<Tvs> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndexOrThrow(_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(NAME_T));
            String rate = cursor.getString(cursor.getColumnIndexOrThrow(RATE_T));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DATE_T));
            String over = cursor.getString(cursor.getColumnIndexOrThrow(OVER_T));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER_T));
            list.add(new Tvs(id,name,rate,date,over,poster));
        }
        return list;
    }
}
