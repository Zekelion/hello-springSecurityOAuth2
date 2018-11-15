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
@Document(collection = "sys.token")
public class Token {
  @Id
  private String id;

  private String token;

  @Indexed
  private String oId;

  private Boolean revoked = false;

  private String scope;

  private String tokenType;

  private Date createdTime = new Date();

  private Date updatedTime = new Date();
}