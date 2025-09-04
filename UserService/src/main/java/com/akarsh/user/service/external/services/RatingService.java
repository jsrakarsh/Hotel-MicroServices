package com.akarsh.user.service.external.services;

import com.akarsh.user.service.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService  {

    @GetMapping("/ratings/user/{userId}")
    List<Rating> getRatingsByUserId(@PathVariable String userId);

@PostMapping("/ratings")
    public Rating createRating(@RequestBody Rating rating);


@PutMapping("/ratings/{ratingId}")
    public Rating updateRating(@PathVariable String ratingId ,Rating rating);

@DeleteMapping("/ratings/{ratingId}")
public void deleteRating(@PathVariable String ratingId);



}
