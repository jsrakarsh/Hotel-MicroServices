package com.akarsh.rating.services;


import com.akarsh.rating.entities.Rating;

import java.util.List;

public interface RatingService {

    // Create a new rating
    Rating create(Rating rating);

    // Get all ratings
    List<Rating> getRatings();

    // Get all ratings by userId
    List<Rating> getRatingsByUserId(String userId);

    // Get all ratings by hotelId
    List<Rating> getRatingsByHotelId(String hotelId);


}
