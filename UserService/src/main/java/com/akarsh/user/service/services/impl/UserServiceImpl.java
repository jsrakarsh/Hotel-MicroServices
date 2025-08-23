package com.akarsh.user.service.services.impl;

import com.akarsh.user.service.entities.User;
import com.akarsh.user.service.exceptions.ResourceNotFoundException;
import com.akarsh.user.service.repositories.UserRepository;
import com.akarsh.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given Id is not available on Server :" + userId));
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
