package com.ntouzidis.demo.module.common.utils;

import com.ntouzidis.demo.module.user.entity.User;

import static com.ntouzidis.demo.module.common.constants.RoleConstants.*;

public class UserUtils {

  public static boolean isAdmin(User user) {
    return user.getAuthorities().stream().anyMatch(i -> i.getAuthority().equals(ROLE_ADMIN));
  }

  private UserUtils() {
  }
}
