package com.habib.cataloguefavorite.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.habib.cataloguefavorite.db.DatabaseContract.MovieColumns.DATE_M;
import static com.habib.cataloguefavorite.db.DatabaseContract.MovieColumns.OVER_M;
import static com.habib.cataloguefavorite.db.DatabaseContract.MovieColumns.POSTER_M;
import static com.habib.cataloguefavorite.db.DatabaseContract.MovieColumns.RATE_M;
import static com.habib.cataloguefavorite.db.DatabaseContract.MovieColumns.TITLE_M;
import static com.habib.cataloguefavorite.db.DatabaseContract.getColumnString;

public class Movies implements Parcelable {
    String ids;
    String titles;
    String rate;
    String date;
    String over;
    String posters;

    public Movies() {
    }

    public Movies(String ids, String titles, String rate, String date, String over, String posters) {
        this.ids = ids;
        this.titles = titles;
        this.rate = rate;
        this.date = date;
        this.over = over;
        this.posters = posters;
    }

    public Movies(Cursor cursor){
        this.ids = getColumnString(cursor,_ID);
        this.titles = getColumnString(cursor,TITLE_M);
        this.rate = getColumnString(cursor,RATE_M);
        this.date = getColumnString(cursor,DATE_M);
        this.over = getColumnString(cursor,OVER_M);
        this.posters = getColumnString(cursor,POSTER_M);
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public String getPosters() {
        return posters;
    }

    public void setPosters(String posters) {
        this.posters = posters;
    }

    protected Movies(Parcel in) {
        ids = in.readString();
        titles = in.readString();
        rate = in.readString();
        date = in.readString();
        over = in.readString();
        posters = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ids);
        parcel.writeString(titles);
        parcel.writeString(rate);
        parcel.writeString(date);
        parcel.writeString(over);
        parcel.writeString(posters);
    }
}
