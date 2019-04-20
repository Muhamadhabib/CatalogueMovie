package com.habib.movie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import static android.provider.BaseColumns._ID;
import static com.habib.movie.db.DatabaseContract.MovieColumns.*;
import static com.habib.movie.db.DatabaseContract.TvColumns.*;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            TABLE_MOVIE,
            _ID,
            TITLE_M,
            RATE_M,
            DATE_M,
            OVER_M,
            POSTER_M
    );
    private static final String CREATE_TABLE_TV = String.format("CREATE TABLE %s"
            + " (%s INTEGER PRIMARY KEY," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL," +
            " %s TEXT NOT NULL)",
            TABLE_TV,
            _ID,
            NAME_T,
            RATE_T,
            DATE_T,
            OVER_T,
            POSTER_T
    );
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_TV);
        onCreate(sqLiteDatabase);
    }
}
