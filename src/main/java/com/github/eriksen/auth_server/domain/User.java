package com.github.eriksen.auth_server.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Token
 */
@Data
@Document(collection = "sys.user")
public class User {
  @Id
  private String id;
  @Indexed
  private String username;
  private String password;
}