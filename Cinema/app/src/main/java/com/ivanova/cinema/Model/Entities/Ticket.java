package com.ivanova.cinema.Model.Entities;

public class Ticket {
    private Integer id;
    private Session session;
    private Seat seat;
    private String hallName;
    private String cinemaName;

    public Ticket(Integer id, Session session, Seat seat, String hallName, String cinemaName) {
        this.id = id;
        this.session = session;
        this.seat = seat;
        this.hallName = hallName;
        this.cinemaName = cinemaName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }
}
