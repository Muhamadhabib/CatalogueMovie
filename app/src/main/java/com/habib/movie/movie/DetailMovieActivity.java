package com.habib.movie.movie;

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
import com.habib.movie.db.MovieHelper;
import com.habib.movie.db.Movies;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static android.provider.BaseColumns._ID;
import static com.habib.movie.db.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;
import static com.habib.movie.db.DatabaseContract.MovieColumns.DATE_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.OVER_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.POSTER_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.RATE_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.TITLE_M;


public class DetailMovieActivity extends AppCompatActivity {
    private TextView title,date,ave,over;
    private ImageView poster,back;
    private String id,tl,ag,dt,ov,po,nm,bk;
    private ProgressBar bar;
    private ConstraintLayout layout;
    private Boolean tombol = false;
    private ArrayList<Movies> movies;
    private Uri uri;
    private MovieHelper helper;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nm = intent.getStringExtra("nama");
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(nm);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        title = findViewById(R.id.tv_title_m_d);
        ave = findViewById(R.id.tv_ave_m_d);
        date = findViewById(R.id.tv_date_m_d);
        over = findViewById(R.id.tv_over_m_d);
        poster = findViewById(R.id.iv_poster_m_d);
        back = findViewById(R.id.iv_back);
        bar = findViewById(R.id.prog_m_d);
        layout = findViewById(R.id.detail_movie);
        getDetailMovie(id);
        uri = getIntent().getData();
        cursor = getContentResolver().query(uri,null,null,null,null);
        if (cursor != null) {
            movies = mapCursorTOArray(cursor);
        }
        if (!movies.isEmpty()){
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
                data.put(TITLE_M,title.getText().toString());
                data.put(RATE_M,ave.getText().toString());
                data.put(DATE_M,date.getText().toString());
                data.put(OVER_M,over.getText().toString());
                data.put(POSTER_M,po);
                if(tombol){
                    //deleteMovie(data);
                    getContentResolver().delete(uri,null,null);
                    tombol = false;
                    Snackbar.make(view,R.string.removed, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    //insertMovie(data);
                    getContentResolver().insert(CONTENT_URI_MOVIE,data);
                    tombol = true;
                    Snackbar.make(view, R.string.added, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                setFav(fab);
            }
        });
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
    private void setFav(FloatingActionButton fab){
        if(tombol){
            fab.setImageResource(R.drawable.ic_star_black_24dp);
        }else{
            fab.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }
    private void getDetailMovie(String id) {
        AndroidNetworking.get(BuildConfig.MOVIE_D + id + "?api_key=" + BuildConfig.API_KEY + BuildConfig.LANG_URL)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            bar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.VISIBLE);
                            tl = response.getString("title");
                            ag = response.getString("vote_average");
                            dt = response.getString("release_date");
                            ov = response.getString("overview");
                            po = response.getString("poster_path");
                            bk = response.getString("backdrop_path");
                            title.setText(tl);
                            ave.setText(ag);
                            date.setText(dt);
                            over.setText(ov);
                            Picasso.with(getApplicationContext()).load(BuildConfig.IMG+po).into(poster);
                            Picasso.with(getApplicationContext()).load(BuildConfig.IMG_BK+bk).into(back);
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
