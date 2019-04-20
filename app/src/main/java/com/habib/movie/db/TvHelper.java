package com.habib.movie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.habib.movie.db.DatabaseContract.MovieColumns.TABLE_MOVIE;
import static com.habib.movie.db.DatabaseContract.TvColumns.TABLE_TV;

public class TvHelper {
    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHelper helper;
    private static SQLiteDatabase database;
    private Context context;

    public TvHelper(Context context) {
        this.context = context;
    }
    public void open() throws SQLException{
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }
    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null
        );
    }
    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC"
        );
    }
    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }
    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE, _ID + " = ?",new String[]{id});
    }
}
