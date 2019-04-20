package com.habib.movie.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private String id;
    private String poster;
    private String title;
    private String average;
    private String release;
    private String overview;

    public Movie(String id,String poster,String title,String average,String release,String overview){
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.average = average;
        this.release = release;
        this.overview = overview;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
