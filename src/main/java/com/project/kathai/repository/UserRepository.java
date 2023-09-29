package com.project.kathai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.kathai.model.User;
 
public interface UserRepository extends JpaRepository<User, Long> {

    User findByuserName(String userName);
 
}
