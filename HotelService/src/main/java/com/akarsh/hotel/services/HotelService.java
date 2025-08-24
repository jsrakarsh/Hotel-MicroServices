package com.akarsh.hotel.services;

import com.akarsh.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    // Create a new hotel
    Hotel create(Hotel hotel);

    // Get all hotels
    List<Hotel> getAll();

    // Get a single hotel by its hotelId
    Hotel get(String hotelId);

    // UPDATE HOTEL DETAIL
    Hotel update(String hotelId, Hotel hotel);

    //DELETE HOTEL DETAIL
    void delete(String hotelId);


}
