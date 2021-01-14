package com.nordee.hotelrestapi.repos;

import com.nordee.hotelrestapi.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoom extends CrudRepository<Room, Integer> {
    @Query(value = "SELECT DISTINCT * FROM Room WHERE hotel_name = ?1", nativeQuery = true)
    List<Room> findRoomsByHotelId(@Param("hotel_id") Long hotel_id);

    List<Room> findRoomsByHotel_Name(String hotel_name);

}
