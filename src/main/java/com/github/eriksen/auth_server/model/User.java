package com.github.eriksen.auth_server.model;

import java.util.Date;

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
  
  @Indexed(unique = true)
  private String username;

  private String password;

  private Boolean archived;

  private Date createdTime = new Date();

  private Date updatedTime = new Date();
}