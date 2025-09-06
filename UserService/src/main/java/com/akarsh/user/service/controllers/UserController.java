package com.akarsh.user.service.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.akarsh.user.service.entities.User;
import com.akarsh.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    // CREATE USER
    @PostMapping
    public ResponseEntity<User> createUser (@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED). body (user1) ;

    }


    // GET SINGLE USER
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // Creating fallback method for Circuit Breaker

    // the fallback method must have the same return type and parameters as the original method, with an additional parameter for the exception
    // here the original method is getSingleUser which returns ResponseEntity<User> and takes String userId as parameter
    // so the fallback method must return ResponseEntity<User> and take String
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        logger.info("Fallback is executed because service is down: {}", ex.getMessage());

        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created as a dummy because some service is down")
                .userId("141234")
                .build();

        return new ResponseEntity<>(user, HttpStatus.OK);
    }





    //GET ALL USERS
    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    // DELETE USER
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully with ID: " + userId);
    }

    // UPDATE USER
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }


}
