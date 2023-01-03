package com.ivanova.cinema.Model.Entities;

public class Seat {
    private Integer id;
    private Integer seatNumber;
    private Integer seatRow;
    private Double price;
    private Integer hallId;

    public Seat(Integer id, Integer seatNumber, Integer seatRow, Double price, Integer hallId) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatRow = seatRow;
        this.price = price;
        this.hallId = hallId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(Integer seatRow) {
        this.seatRow = seatRow;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }
}
