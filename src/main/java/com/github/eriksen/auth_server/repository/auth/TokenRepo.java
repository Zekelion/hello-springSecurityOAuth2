package com.github.eriksen.auth_server.repository.auth;

import com.github.eriksen.auth_server.model.Token;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * TokenRepo
 */
public interface TokenRepo extends MongoRepository<Token, String> {

}