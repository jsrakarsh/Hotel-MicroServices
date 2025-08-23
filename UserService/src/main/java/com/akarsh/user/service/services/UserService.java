package com.akarsh.user.service.services;

import com.akarsh.user.service.entities.User;

import java.util.List;

public interface UserService {

    //Create
    User saveUser (User user);

    //Get All User
    List<User> getAllUser();

    //Get single user of given userId
    User getUser(String userId);


    //Delete User
    void deleteUser(String userId);

    //Update User
    User updateUser(String userId, User updatedUser);
}
