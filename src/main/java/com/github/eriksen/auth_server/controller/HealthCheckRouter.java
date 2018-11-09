package com.github.eriksen.auth_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthCheckRouter
 */
@RestController
public class HealthCheckRouter {

  @GetMapping("/api/v1.0/health")
  public String healthCheck() {
    return "active";
  }
}