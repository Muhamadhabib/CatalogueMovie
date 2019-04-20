package com.habib.movie.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.habib.movie";
    private static final String SCHEME = "content";

    public DatabaseContract() {
    }
    public static final class MovieColumns implements BaseColumns{
        public static String TABLE_MOVIE = "movie";
        //public static String IDM = "idm";
        public static String TITLE_M = "title";
        public static String RATE_M = "rate";
        public static String DATE_M = "date";
        public static String OVER_M = "over";
        public static String POSTER_M = "poster";

        public static Uri CONTENT_URI_MOVIE = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }
    public static final class TvColumns implements BaseColumns{
        public static String TABLE_TV = "tv";
        //public static String IDT = "idt";
        public static String NAME_T = "name";
        public static String RATE_T = "rate";
        public static String DATE_T = "date";
        public static String OVER_T = "over";
        public static String POSTER_T = "poster";

        public static Uri CONTENT_URI_TV = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build();
    }
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
