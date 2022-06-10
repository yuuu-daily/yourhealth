package com.example.yourhealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.yourhealth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	User findByUserId(Long id);
	
    User findByUsername(String username);
    
}
