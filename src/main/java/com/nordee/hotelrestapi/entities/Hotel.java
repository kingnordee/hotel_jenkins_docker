package com.nordee.hotelrestapi.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private int occupiedCnt;
    private int numOfRooms;
//    private static final int NOT_FOUND = -1;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
    private Set<Room> rooms;

    public Hotel() {
        this.name = "NO NAME";
        this.location = "Unknown";
        this.occupiedCnt = 0;
        this.numOfRooms = 0;
        this.rooms = new HashSet<>();
    }

    public Hotel(String name, String location) {
        this.name = name;
        this.location = location;
        this.occupiedCnt = 0;
        this.numOfRooms = 0;
        this.rooms = new HashSet<>();
    }

    public Long getId(){
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getOccupiedCnt() {
        return occupiedCnt;
    }

    public void setOccupiedCnt(int occupiedCnt) {
        this.occupiedCnt += (occupiedCnt);
    }

    public int getNumOfRooms() {
        return numOfRooms;
    }
//    public int getNumOfRooms() {
//    return this.rooms.size();
//}

    public void setNumOfRooms(int numOfRooms) {
        this.numOfRooms += (numOfRooms);
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public boolean isFull(){
        return numOfRooms == rooms.size();
    }

    public boolean isEmpty() {
        return numOfRooms == 0;
    }

    public void addRoom(String bedType, char smoking, double rate) {
        Room room = new Room(this, bedType, smoking, rate);

        if(this.rooms == null) {
            this.rooms = new HashSet<>();
        }

        this.rooms.add(room);
        this.setNumOfRooms(1);
    }

    public void addReservation(String occupantName, int roomId) {
        String feedback = "Reservation was unsucessful";

        for(Room rm : rooms) {
            if(!rm.getIsOccupied() && rm.getRoomNum() == roomId) {
                rm.setOccupant(occupantName);
                rm.setOccupied(true);
                this.occupiedCnt++;
                this.numOfRooms--;
                System.out.println("Reservation made successfully!");
                return;
            }
        }
        System.out.println(feedback);
    }

    public void cancelReservation(String occupantName) {
        String feedback = "Cancellation was unsucessful";

        Room room = (this.findReservation(occupantName));

        if(room != null) {
            room.setOccupied(false);
            room.setOccupant("Not occupied");
            this.occupiedCnt--;
            System.out.println("Reservation cancelled successfully!");
            return;
        }
        System.out.println(feedback);
    }

    public Room findReservation(String occupantName) {
        String feedback = "No reservation found!";
        for(Room room : this.rooms) {
            if(room.getOccupant().equals(occupantName)) {
                System.out.println("Reservation found!");
                return room;
            }
        }
        System.out.println(feedback);
        return null;
    }

    public void printReservationList() {
        System.out.println("Current reservation list: \n");
        boolean roomsAvailable = false;
        for(Room room : this.rooms) {
            if(room != null && room.getIsOccupied()) {
                roomsAvailable = true;
                System.out.println(room + "\n");
            }
        }
        if(!roomsAvailable) System.out.println("No rooms reserved");
    }

    public void getDailySales() {
        float total = 0;
        System.out.println("Current reservation list:\n");
        for(Room room : this.rooms) {
            if(room.getIsOccupied()) total += room.getRoomRate();
        }
        System.out.println("Today's sales are: " + total);
    }

    public int occupancyPercentage() {
        return (this.occupiedCnt * 100) / this.rooms.size();
    }



    @Override
    public String toString() {
        String output = "Hotel Name: "+ this.name +
                "\n Hotel Location: "+ this.location +
                "\nAvailable Rooms: "+ this.numOfRooms;
        return output;
    }

}

