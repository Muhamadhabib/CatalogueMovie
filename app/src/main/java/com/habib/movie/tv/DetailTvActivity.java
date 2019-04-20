package com.habib.movie.tv;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.habib.movie.BuildConfig;
import com.habib.movie.R;
import com.habib.movie.db.Tvs;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static android.provider.BaseColumns._ID;
import static com.habib.movie.db.DatabaseContract.TvColumns.*;

public class DetailTvActivity extends AppCompatActivity {
    private TextView name,ave,date,over,last,epis,seas;
    private ImageView poster,back;
    private ConstraintLayout layout;
    private ProgressBar bar;
    private String id,nm,nama,aver,first,las,epi,sea,ove,poste,bac;
    private ArrayList<Tvs> favTvs;
    private Boolean tombol = false;
    private Cursor cursor;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nm = intent.getStringExtra("nama");
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(nm);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        name = findViewById(R.id.tv_title_t_d);
        ave = findViewById(R.id.tv_ave_t_d);
        date = findViewById(R.id.tv_date_t_d);
        over = findViewById(R.id.tv_over_t_d);
        poster = findViewById(R.id.iv_poster_t_d);
        back = findViewById(R.id.iv_back_tv);
        layout = findViewById(R.id.detail_tv);
        bar = findViewById(R.id.prog_t_d);
        last = findViewById(R.id.tv_last_t_d);
        epis = findViewById(R.id.tv_epi_t_d);
        seas = findViewById(R.id.tv_sea_t_d);
        getDetailTv(id);
        uri = getIntent().getData();
        cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor!=null){
            favTvs = mapCursorTOArray(cursor);
        }
        if(!favTvs.isEmpty()){
            tombol = true;
        }
        AndroidNetworking.initialize(getApplicationContext());
        final FloatingActionButton fab = findViewById(R.id.fab);
        setFav(fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues data = new ContentValues();
                data.put(_ID,id);
                data.put(NAME_T,name.getText().toString());
                data.put(RATE_T,ave.getText().toString());
                data.put(DATE_T,date.getText().toString());
                data.put(OVER_T,over.getText().toString());
                data.put(POSTER_T,poste);
                if(tombol){
                    getContentResolver().delete(uri,null,null);
                    tombol = false;
                    Snackbar.make(view,R.string.removed, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    getContentResolver().insert(CONTENT_URI_TV,data);
                    tombol = true;
                    Snackbar.make(view,R.string.added, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                setFav(fab);
            }
        });
    }
    public static ArrayList<Tvs> mapCursorTOArray(Cursor cursor) {
        ArrayList<Tvs> list = new ArrayList<>();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndexOrThrow(_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NAME_T));
            String rate = cursor.getString(cursor.getColumnIndexOrThrow(RATE_T));
            String date = cursor.getString(cursor.getColumnIndexOrThrow(DATE_T));
            String over = cursor.getString(cursor.getColumnIndexOrThrow(OVER_T));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER_T));
            list.add(new Tvs(id,title,rate,date,over,poster));
        }
        return list;
    }
    private void setFav(FloatingActionButton fab){
        if(tombol){
            fab.setImageResource(R.drawable.ic_star_black_24dp);
        }else{
            fab.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }
    private void getDetailTv(String id) {
        AndroidNetworking.get(BuildConfig.TV_D+id+"?api_key="+BuildConfig.API_KEY+BuildConfig.LANG_URL)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        bar.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        try {
                            nama = response.getString("name");
                            aver = response.getString("vote_average");
                            first = response.getString("first_air_date");
                            las = response.getString("last_air_date");
                            epi = response.getString("number_of_episodes");
                            sea = response.getString("number_of_seasons");
                            ove = response.getString("overview");
                            poste = response.getString("poster_path");
                            bac = response.getString("backdrop_path");
                            name.setText(nama);
                            ave.setText(aver);
                            date.setText(first);
                            over.setText(ove);
                            last.setText(las);
                            epis.setText(epi);
                            seas.setText(sea);
                            Picasso.with(getApplicationContext()).load(BuildConfig.IMG+poste).into(poster);
                            Picasso.with(getApplicationContext()).load(BuildConfig.IMG_BK+bac).into(back);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(),R.string.con,Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
