package com.nordee.hotelrestapi.controllers;

import com.nordee.hotelrestapi.entities.Hotel;
import com.nordee.hotelrestapi.entities.Room;
import com.nordee.hotelrestapi.repos.IHotel;
import com.nordee.hotelrestapi.repos.IRoom;
import com.nordee.hotelrestapi.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "")
@RestController
public class HotelController {

    @Autowired
    HotelService hotelService;
    @Autowired
    IHotel hotelRepo;
    @Autowired
    IRoom roomRepo;

    @GetMapping("/")
    public ResponseEntity<Iterable<Hotel>> showHotel(){
        Iterable<Hotel> hotels = hotelService.findAll();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PostMapping("/createHotel")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        return
                new ResponseEntity<>(hotelService.createHotel(hotel), HttpStatus.CREATED);
    }

    @PostMapping("/addRoom/{hotelId}")
    public ResponseEntity<HttpStatus> addRoom(@PathVariable Long hotelId, @RequestBody Room room){
        try{
            hotelService.addRoom(hotelId, room);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/findRooms/{hotelId}")
    public Set<Room> findRooms(@PathVariable Long hotelId){
        try{
            Hotel hotel = hotelService.findById(hotelId);
            Set<Room> rooms = hotel.getRooms();
            Set<Room> rms = new HashSet<>();
            for(Room room : rooms){
                if (!room.getIsOccupied()) rms.add(room);
            }
            return rms;
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/findReservations")
    public List<Map<String, String>> findReservations(){
        try{
            Iterable<Room> rms = roomRepo.findAll();
            List<Map<String, String>> rooms = new ArrayList<>();
            for(Room room : rms){
                if (room.getIsOccupied()){
                    Map<String, String> rm = new HashMap<>();
                    rm.put("hotelName", room.getHotel().getName());
                    rm.put("roomNum", Integer.toString(room.getRoomNum()));
                    rm.put("bedType", room.getBedType());
                    rm.put("smoking", Character.toString(room.getSmoking()));
                    rm.put("rate", Double.toString(room.getRoomRate()));
                    rm.put("occupantName", room.getOccupant());
                    rooms.add(rm);
                }
            }
            return rooms;
        }catch (Exception e){
            return null;
        }
    }

    @GetMapping("/addReservation/{roomId}/{name}")
    public void addReservation(@PathVariable Integer roomId, @PathVariable String name){
        Room room = roomRepo.findById(roomId).get();
        hotelService.addReservation(room.getHotel(), name, roomId);
    }

    @DeleteMapping("/deleteHotel/{hotelId}")
    public ResponseEntity<HttpStatus> deleteHotel(@PathVariable Long hotelId){
        try{
            hotelRepo.deleteById(hotelId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/deleteRoom/{roomId}")
    public ResponseEntity<HttpStatus> deleteRoom(@PathVariable Integer roomId){
        try{
            roomRepo.deleteById(roomId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/deleteRooms")
    public ResponseEntity<HttpStatus> deleteRooms(){
        try {
            roomRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.GONE);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/deleteHotels")
    public ResponseEntity<HttpStatus> deleteHotels(){
        try {
            hotelRepo.deleteAll();
            return new ResponseEntity<>(HttpStatus.GONE);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @GetMapping("/cancelReservation/{roomId}")
    public ResponseEntity<HttpStatus> cancelReservation(@PathVariable Integer roomId){
        try{
            Room room = roomRepo.findById(roomId).get();
            room.setOccupant("Not Occupied");
            room.setOccupied(false);
            roomRepo.save(room);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


}
