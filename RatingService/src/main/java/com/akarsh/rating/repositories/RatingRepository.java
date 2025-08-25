package com.akarsh.rating.repositories;

import com.akarsh.rating.entities.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface RatingRepository extends MongoRepository<Rating,String> {


    // whenever we are dealing with non relational database like Mongodb then we do not extend Jpa Repository
    // instead Mongorepository is extended


    // we have to create two custom methods

    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId) ;


}
