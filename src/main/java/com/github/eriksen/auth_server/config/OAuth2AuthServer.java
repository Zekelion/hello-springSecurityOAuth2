package com.github.eriksen.auth_server.config;

import com.github.eriksen.auth_server.service.security.CustomUserDetailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * OAuth2AuthServer
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServer extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private TokenStore tokenStore;
  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // authorization code + implicit with refresh_token
    clients.inMemory().withClient("clientapp").secret(bCryptPasswordEncoder.encode("123"))
        .redirectUris("http://localhost:9000/callback")
        .authorizedGrantTypes("authorization_code", "implicit", "refresh_token", "password", "client_credentials")
        .accessTokenValiditySeconds(300).scopes("read", "admin");

    // client password client credentials ( require authentication manager ),
    // directly retrieve access token from token endpoint via
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager).userDetailsService(customUserDetailService)
        .tokenStore(tokenStore);
  }
}