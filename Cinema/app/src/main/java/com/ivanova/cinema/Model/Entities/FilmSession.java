package com.ivanova.cinema.Model.Entities;

import java.util.ArrayList;

public class FilmSession {
    private Integer filmId;
    private String filmName;
    private Integer filmApiId;
    private ArrayList<Session> sessions;

    public FilmSession(Integer filmId, String filmName, Integer filmApiId, ArrayList<Session> sessions) {
        this.filmId = filmId;
        this.filmName = filmName;
        this.filmApiId = filmApiId;
        this.sessions = sessions;
    }

    public void addSession(Session session) {
        sessions.add(session);
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

    public Integer getFilmApiId() {
        return filmApiId;
    }

    public void setFilmApiId(Integer filmApiId) {
        this.filmApiId = filmApiId;
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }
}
