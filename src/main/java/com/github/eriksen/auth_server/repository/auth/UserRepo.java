package com.github.eriksen.auth_server.repository.auth;

import com.github.eriksen.auth_server.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * UserRepo
 */
public interface UserRepo extends MongoRepository<User, String> {

  public User findByUsername(String username);
}