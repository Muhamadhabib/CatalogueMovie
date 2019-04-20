package com.habib.movie.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.habib.movie.BuildConfig;
import com.habib.movie.R;
import com.habib.movie.db.Movies;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static android.provider.BaseColumns._ID;
import static com.habib.movie.db.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;
import static com.habib.movie.db.DatabaseContract.MovieColumns.DATE_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.OVER_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.POSTER_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.RATE_M;
import static com.habib.movie.db.DatabaseContract.MovieColumns.TITLE_M;

public class StackRemoteViews implements RemoteViewsService.RemoteViewsFactory {
    private List<Movies> movies = new ArrayList<>();
    private Context context;
    private Cursor cursor;

    public StackRemoteViews(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(CONTENT_URI_MOVIE,null,null,null,null);
    }

    @Override
    public void onDataSetChanged() {
        movies.addAll(mapCursorTOArray(cursor)) ;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        try {
            Bitmap a = Picasso.with(context).load(BuildConfig.IMG + movies.get(i).getPosters()).get();
            views.setImageViewBitmap(R.id.img_widget, a);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putInt(ImageWidget.EXTRA_ITEM, i);
        Intent fill = new Intent();
        fill.putExtras(bundle);
        views.setOnClickFillInIntent(R.id.img_widget, fill);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
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
