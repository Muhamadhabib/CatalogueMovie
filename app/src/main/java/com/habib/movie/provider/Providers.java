package com.habib.movie.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.habib.movie.db.MovieHelper;
import com.habib.movie.db.TvHelper;

import static com.habib.movie.db.DatabaseContract.AUTHORITY;
import static com.habib.movie.db.DatabaseContract.MovieColumns.CONTENT_URI_MOVIE;
import static com.habib.movie.db.DatabaseContract.MovieColumns.TABLE_MOVIE;
import static com.habib.movie.db.DatabaseContract.TvColumns.CONTENT_URI_TV;
import static com.habib.movie.db.DatabaseContract.TvColumns.TABLE_TV;

public class Providers extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV = 3;
    private static final int TV_ID = 4;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private MovieHelper movieHelper;
    private TvHelper tvHelper;
    static {
        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE,MOVIE);
        sUriMatcher.addURI(AUTHORITY,TABLE_MOVIE+"/#",MOVIE_ID);
    }
    static {
        sUriMatcher.addURI(AUTHORITY,TABLE_TV,TV);
        sUriMatcher.addURI(AUTHORITY,TABLE_TV+"/#",TV_ID);
    }
    @Override
    public boolean onCreate() {
        movieHelper = new MovieHelper(getContext());
        tvHelper = new TvHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query( @NonNull Uri uri,  @Nullable String[] strings, @Nullable String s,@Nullable String[] strings1, @Nullable String s1) {
        movieHelper.open();
        tvHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvHelper.queryProvider();
                break;
            case TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType( @NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert( @NonNull Uri uri,  @Nullable ContentValues contentValues) {
        movieHelper.open();
        tvHelper.open();
        long added;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insertProvider(contentValues);
                if(added>0){
                    uri = ContentUris.withAppendedId(CONTENT_URI_MOVIE,added);
                }
                break;
            case TV:
                added = tvHelper.insertProvider(contentValues);
                if(added>0){
                    uri = ContentUris.withAppendedId(CONTENT_URI_TV,added);
                }
                break;
            default:
                added = 0;
                break;
        }
        return uri;
    }

    @Override
    public int delete( @NonNull Uri uri,  @Nullable String s, @Nullable String[] strings) {
        movieHelper.open();
        tvHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case TV_ID:
                deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri,  @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
