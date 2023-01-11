package com.ivanova.cinema.Model.Entities;

public class SeatUI {

    private Integer id;
    private Integer seatNumber;
    private Integer seatRow;
    private Double price;
    private Integer hallId;
    private Boolean isFree;

    public SeatUI(Integer id, Integer seatNumber, Integer seatRow, Double price, Integer hallId, Boolean isFree) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.seatRow = seatRow;
        this.price = price;
        this.hallId = hallId;
        this.isFree = isFree;
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

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }
}
