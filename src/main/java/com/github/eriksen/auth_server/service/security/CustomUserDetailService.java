package com.github.eriksen.auth_server.service.security;

import com.github.eriksen.auth_server.model.User;
import com.github.eriksen.auth_server.repository.auth.UserRepo;
import com.github.eriksen.auth_server.service.security.support.UserDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * CustomUserDetailService
 */
@Component
public class CustomUserDetailService implements UserDetailsService {
  @Autowired
  private UserRepo userRepo;
  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      User user = userRepo.findByUsername(username);
      if (user == null) {
        throw new Exception("user not found");
      }

      Boolean archiveFlag = !user.getArchived();
      UserDetail userDetail = new UserDetail(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
          archiveFlag, archiveFlag, true, archiveFlag, AuthorityUtils.createAuthorityList("USER"));

      return userDetail;
    } catch (Exception e) {
      throw new UsernameNotFoundException(e.getMessage());
    }
  }
}