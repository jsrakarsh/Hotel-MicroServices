package com.akarsh.user.service.services.impl;

import com.akarsh.user.service.entities.Hotel;
import com.akarsh.user.service.entities.Rating;
import com.akarsh.user.service.entities.User;
import com.akarsh.user.service.exceptions.ResourceNotFoundException;
import com.akarsh.user.service.external.services.HotelService;
import com.akarsh.user.service.external.services.RatingService;
import com.akarsh.user.service.repositories.UserRepository;
import com.akarsh.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID. randomUUID() .toString();
        user.setUserId(randomUserId);
      return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given Id is not available on Server :" + userId));

        /* USING REST TEMPLATE

        Rating[] ratingsOfUser = restTemplate.getForObject(
                "http://RATINGSERVICE/ratings/user/" + user.getUserId(), Rating[].class);
        logger.info("Ratings fetched: {}", ratingsOfUser);

        List<Rating> ratings = Arrays.stream(ratingsOfUser) .toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity(
                    "http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = forEntity.getBody();
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }

         */

        // Using Feign Client

        //Fetch ratings of user using Feign (not RestTemplate)
        List<Rating> ratings = ratingService.getRatingsByUserId(user.getUserId());
        logger.info("Ratings fetched: {}", ratings);

        // For each rating, fetch hotel details using Feign
        List<Rating> ratingList = ratings.stream().map(rating -> {
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }





    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot delete. User with Id not found: " + userId));
        userRepository.delete(user);
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update. User with Id not found: " + userId));

        // update only required fields (you can customize this)
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAbout(updatedUser.getAbout());

        return userRepository.save(existingUser);
    }

}
