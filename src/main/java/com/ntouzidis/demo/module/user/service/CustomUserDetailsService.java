package com.ntouzidis.demo.module.user.service;

import com.ntouzidis.demo.module.user.entity.CustomUserDetails;
import com.ntouzidis.demo.module.user.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  private final IUserService userService;

  public CustomUserDetailsService(IUserService userService) {
    this.userService = userService;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) {
    User user = userService.getOneGlobal(username);
    CustomUserDetails customUserDetail = new CustomUserDetails();
    customUserDetail.setUser(user);
    return customUserDetail;
  }
}
