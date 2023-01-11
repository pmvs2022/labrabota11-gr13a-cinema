package com.ivanova.cinema.Model.Entities;

import java.util.ArrayList;

public class FilmSession {
    private Integer filmId;
    private String filmName;
    private ArrayList<Session> sessions;

    public FilmSession(Integer filmId, String filmName, ArrayList<Session> sessions) {
        this.filmId = filmId;
        this.filmName = filmName;
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

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }
}
