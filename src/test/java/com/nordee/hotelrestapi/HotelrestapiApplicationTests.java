package com.nordee.hotelrestapi;

import com.nordee.hotelrestapi.services.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HotelrestapiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    HotelService hotelService;

//    @Test
//    void testHotel(){
//        hotelService.createHotel("DataJPA", "IntelliJ");
//    }

}
