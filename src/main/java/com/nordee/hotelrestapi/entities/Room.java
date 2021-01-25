package com.nordee.hotelrestapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Room {


    //.IDENTITY means create id by incrementing from the  last one
    //.IDENTITY means we provide id for each instance.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomNum;

    private String bedType;
    private double rate;
    private String occupantName;
    private char smoking;
    private boolean isOccupied;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonIgnore
    private Hotel hotel;

    public Room() {
        // TODO Auto-generated constructor stub
        this.occupantName = "Not Occupied";
        this.isOccupied = false;
        this.bedType = "Not specified";
        this.smoking = '?';
//        this.hotel = hotel;
    }

    public Room(Hotel hotel, String bedType, char smoking, double rate) {
        this.bedType = bedType;
        this.smoking = smoking;
        this.rate = rate;
        this.isOccupied = false;
        this.occupantName = "Not Occupied";
        this.hotel = hotel;
    }

    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    public Hotel getHotel(){ return this.hotel; }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public double getRoomRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getOccupant() {
        return this.occupantName;
    }

    public void setOccupant(String occupantName) {
        this.occupantName = occupantName;
    }

    public char getSmoking() {
        return smoking;
    }

    public void setSmoking(char smoking) {
        this.smoking = smoking;
    }

    public boolean getIsOccupied() {
        return this.isOccupied;
    }

    public void setOccupied(boolean occupied) {
        this.isOccupied = occupied;
    }

    @Override
    public String toString() {
        return "Room Number: " + this.roomNum + "\nOccupant name: " + this.occupantName + "\nSmoking room: "
                + this.smoking + "\nBed Type: " + this.bedType + "\nRate: " + this.rate;
    }

}
