package com.nordee.hotelrestapi.repos;

import com.nordee.hotelrestapi.entities.Hotel;
import com.nordee.hotelrestapi.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHotel extends JpaRepository<Hotel, Long> {

}