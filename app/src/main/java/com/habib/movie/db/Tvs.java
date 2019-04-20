package com.habib.movie.db;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import static android.provider.BaseColumns._ID;
import static com.habib.movie.db.DatabaseContract.TvColumns.*;
import static com.habib.movie.db.DatabaseContract.getColumnString;

public class Tvs implements Parcelable {
    String idt;
    String namet;
    String averaget;
    String releaset;
    String overviewt;
    String postert;

    public Tvs() {
    }

    public Tvs(String id, String name, String average, String release, String overview, String poster) {
        this.idt = id;
        this.namet = name;
        this.averaget = average;
        this.releaset = release;
        this.overviewt = overview;
        this.postert = poster;
    }
    public Tvs(Cursor cursor){
        this.idt = getColumnString(cursor,_ID);
        this.namet = getColumnString(cursor,NAME_T);
        this.averaget = getColumnString(cursor,RATE_T);
        this.releaset = getColumnString(cursor,DATE_T);
        this.overviewt = getColumnString(cursor,OVER_T);
        this.postert = getColumnString(cursor,POSTER_T);
    }
    public String getIdt() {
        return idt;
    }

    public void setIdt(String id) {
        this.idt = id;
    }

    public String getNamet() {
        return namet;
    }

    public void setNamet(String name) {
        this.namet = name;
    }

    public String getAveraget() {
        return averaget;
    }

    public void setAveraget(String average) {
        this.averaget = average;
    }

    public String getReleaset() {
        return releaset;
    }

    public void setReleaset(String release) {
        this.releaset = release;
    }

    public String getOverviewt() {
        return overviewt;
    }

    public void setOverviewt(String overview) {
        this.overviewt = overview;
    }

    public String getPostert() {
        return postert;
    }

    public void setPostert(String poster) {
        this.postert = poster;
    }

    protected Tvs(Parcel in) {
        idt = in.readString();
        namet = in.readString();
        averaget = in.readString();
        releaset = in.readString();
        overviewt = in.readString();
        postert = in.readString();
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
        parcel.writeString(idt);
        parcel.writeString(namet);
        parcel.writeString(averaget);
        parcel.writeString(releaset);
        parcel.writeString(overviewt);
        parcel.writeString(postert);
    }
}
