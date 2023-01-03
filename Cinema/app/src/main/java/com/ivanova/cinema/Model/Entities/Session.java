package com.ivanova.cinema.Model.Entities;

public class Session {
    private Integer id;
    private String date;
    private String time;
    private Integer hallId;
    private Integer filmId;
    private String filmName;

    public Session(Integer id, String date, String time, Integer hallId, Integer filmId, String filmName) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.hallId = hallId;
        this.filmId = filmId;
        this.filmName = filmName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }
}
