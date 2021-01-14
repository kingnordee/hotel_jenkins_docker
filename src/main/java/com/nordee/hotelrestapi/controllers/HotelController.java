package com.nordee.hotelrestapi.controllers;

import com.nordee.hotelrestapi.entities.Hotel;
import com.nordee.hotelrestapi.entities.Room;
import com.nordee.hotelrestapi.repos.IRoom;
import com.nordee.hotelrestapi.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Stream;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class HotelController {

    @Autowired
    HotelService hotelService;
    @Autowired
    IRoom roomRepo;

    @GetMapping("/")
    public ResponseEntity<List<Hotel>> showHotel(){
        List<Hotel> hotels = hotelService.findAll();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
//    public List<Hotel> showHotel(){
//        return hotelService.findAll();
//    }

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

    @GetMapping("/addReservation/{roomId}/{name}")
    public void addReservation(@PathVariable Integer roomId, @PathVariable String name){
        Room room = roomRepo.findById(roomId).get();
        hotelService.addReservation(room.getHotel(), name, roomId);
    }

//    @GetMapping("/findRooms/{hotelId}")
//    public ResponseEntity<Set<Room>> findRooms(@PathVariable Long hotelId){
//        try{
//            Hotel hotel = hotelService.findById(hotelId);
//            return new ResponseEntity<>(hotel.getRooms(), HttpStatus.FOUND);
//        }catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

}
