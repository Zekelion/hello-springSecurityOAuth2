package com.github.eriksen.auth_server.config;

import com.github.eriksen.auth_server.service.security.CustomUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * SecurityConfig
 */
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${spring.security.user.name}")
  private String userName;
  @Value("${spring.security.user.password}")
  private String password;
  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Autowired
  private RedisConnectionFactory redisConnectionFactory;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Bean
  public TokenStore tokenStore() {
    RedisTokenStore redis = new RedisTokenStore(redisConnectionFactory);
    return redis;
  }

  @Bean
  public AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(customUserDetailService);
    provider.setPasswordEncoder(bCryptPasswordEncoder);
    return provider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requestMatchers().antMatchers("/login", "/oauth/authorize").and().authorizeRequests().anyRequest()
        .authenticated().and().formLogin().permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // auth.inMemoryAuthentication().withUser(userName).password(passwordEncoder().encode(password)).roles("USER");
    auth.authenticationProvider(authenticationProvider());
  }
}