package com.habib.cataloguefavorite.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.habib.cataloguefavorite.db.DatabaseContract.TvColumns.*;
import static com.habib.cataloguefavorite.db.DatabaseContract.getColumnString;

public class Tvs implements Parcelable {
    String id;
    String name;
    String average;
    String release;
    String overview;
    String poster;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Tvs() {
    }

    public Tvs(String id, String name, String average, String release, String overview, String poster) {
        this.id = id;
        this.name = name;
        this.average = average;
        this.release = release;
        this.overview = overview;
        this.poster = poster;
    }
    public Tvs(Cursor cursor){
        this.id = getColumnString(cursor,_ID);
        this.name = getColumnString(cursor,NAME_T);
        this.average = getColumnString(cursor,RATE_T);
        this.release = getColumnString(cursor,DATE_T);
        this.overview = getColumnString(cursor,OVER_T);
        this.poster = getColumnString(cursor,POSTER_T);
    }
    protected Tvs(Parcel in) {
        id = in.readString();
        name = in.readString();
        average = in.readString();
        release = in.readString();
        overview = in.readString();
        poster = in.readString();
    }

    public static final Creator<Tvs> CREATOR = new Creator<Tvs>() {
        @Override
        public Tvs createFromParcel(Parcel in) {
            return new Tvs(in);
        }

        @Override
        public Tvs[] newArray(int size) {
            return new Tvs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(average);
        parcel.writeString(release);
        parcel.writeString(overview);
        parcel.writeString(poster);
    }
}
