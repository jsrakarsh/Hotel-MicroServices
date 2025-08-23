package com.akarsh.user.service.repositories;

import com.akarsh.user.service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    // As JPA provides default methods but custom methods can also be implemented here

}
