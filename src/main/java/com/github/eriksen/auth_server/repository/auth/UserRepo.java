package com.github.eriksen.auth_server.repository.auth;

import com.github.eriksen.auth_server.domain.User;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * UserRepo
 */
public interface UserRepo extends MongoRepository<User, String> {
  
}