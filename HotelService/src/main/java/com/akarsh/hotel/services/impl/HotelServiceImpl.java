package com.akarsh.hotel.services.impl;

import com.akarsh.hotel.entities.Hotel;
import com.akarsh.hotel.exceptions.ResourceNotFoundException;
import com.akarsh.hotel.repositories.HotelRepository;
import com.akarsh.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;




    @Override
    public Hotel create(Hotel hotel) {
        String hotelId= UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel get(String hotelId) {
        return hotelRepository.findById(hotelId).orElseThrow(()-> new ResourceNotFoundException("Hotel with given Id is not available on Server :" + hotelId));
    }


    @Override
    public Hotel update(String hotelId, Hotel hotel) {
        Hotel existingHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hotel with given Id is not available on Server : " + hotelId));

        existingHotel.setName(hotel.getName());
        existingHotel.setLocation(hotel.getLocation());
        existingHotel.setAbout(hotel.getAbout());

        return hotelRepository.save(existingHotel);
    }


    @Override
    public void delete(String hotelId) {
        Hotel existingHotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Hotel with given Id is not available on Server : " + hotelId));

        hotelRepository.delete(existingHotel);
    }

}
