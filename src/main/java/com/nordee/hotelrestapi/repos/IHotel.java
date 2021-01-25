package com.nordee.hotelrestapi.repos;

import com.nordee.hotelrestapi.entities.Hotel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IHotel extends CrudRepository<Hotel, Long> {

}