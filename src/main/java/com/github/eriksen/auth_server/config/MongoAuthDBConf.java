package com.github.eriksen.auth_server.config;

import com.mongodb.MongoClientURI;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoAuthDBConf
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.github.eriksen.auth_server.repository.auth", mongoTemplateRef = "authdbTemp")
public class MongoAuthDBConf {

  @Bean("authdbConf")
  @ConfigurationProperties(prefix = "spring.data.mongodb.authorization")
  public MongoProperties authConfig() {
    return new MongoProperties();
  }

  @Bean
  public MongoDbFactory authdbFactory(@Qualifier("authdbConf") MongoProperties mongoProperties) {
    String uri = "mongodb://";

    if (authConfig().getUsername() != null && authConfig().getPassword() != null) {
      uri += authConfig().getUsername() + ':' + authConfig().getPassword().toString() + '@';
    }
    uri += authConfig().getHost() + ':' + authConfig().getPort() + '/' + authConfig().getDatabase();

    return new SimpleMongoDbFactory(new MongoClientURI(uri));
  }

  @Bean("authdbTemp")
  public MongoTemplate authdbTemplate() {
    return new MongoTemplate(authdbFactory(authConfig()));
  }
}