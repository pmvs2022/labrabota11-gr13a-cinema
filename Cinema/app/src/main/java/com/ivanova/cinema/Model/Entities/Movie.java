package com.ivanova.cinema.Model.Entities;

public class Movie {
    private String id;
    private String name;
    private String date;
    private String country;
    private String genres;
    private String director;
    private String poster_path;
    private String overview;

    public Movie(String id) {
        this.id = id;
    }

    public Movie(String id, String name, String date, String country, String genres, String director, String poster_path, String overview) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.country = country;
        this.genres = genres;
        this.director = director;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getCountry() {
        return country;
    }

    public String getGenres() {
        return genres;
    }

    public String getDirector() {
        return director;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
