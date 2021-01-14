package com.nordee.hotelrestapi.services;

import javax.transaction.Transactional;

import com.nordee.hotelrestapi.entities.Hotel;
import com.nordee.hotelrestapi.entities.Room;
import com.nordee.hotelrestapi.repos.IHotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    IHotel hotelRepo;


    @Transactional
    public Hotel createHotel(String name, String location) {
        Hotel hotel = new Hotel(name, location);
        return hotelRepo.save(hotel);
    }

    @Transactional
    public Hotel createHotel(Hotel hotel) {
        Hotel hotel1 = new Hotel(hotel.getName(), hotel.getLocation());
        return hotelRepo.save(hotel1);
    }

    public Hotel createHotel() {
        Hotel hotel = new Hotel();
        hotelRepo.save(hotel);
        return hotel;
    }

    public List<Hotel> findAll(){
        return hotelRepo.findAll();
    }
    public Hotel findById(Long id){
        return hotelRepo.findById(id).get();
    }

    public void addRoom(Hotel hotel, String bedType, char smoking, double rate) {
        hotel.addRoom(bedType, smoking, rate);
        hotelRepo.save(hotel);
    }
    public void addRoom(Long hotelId, Room room) {
        Hotel hotel = findById(hotelId);
        hotel.addRoom(room.getBedType(), room.getSmoking(), room.getRoomRate());
        hotelRepo.save(hotel);
    }

    public Hotel addReservation(Hotel hotel, String occupantName, int roomId) {
        hotel.addReservation(occupantName, roomId);
        hotelRepo.save(hotel);
        return hotel;
    }

    public void cancelReservation(Hotel hotel, String occupantName) {
        hotel.cancelReservation(occupantName);
        hotelRepo.save(hotel);
    }

    public void getAllReservations(Hotel hotel) {
        hotel.printReservationList();
    }

    public void getDailySales(Hotel hotel) {
        hotel.getDailySales();
    }

    public int occupancyPercentage(Hotel hotel) {
        return hotel.occupancyPercentage();
    }


    public String display(Hotel hotel) {
        return hotel.toString();
    }
}
