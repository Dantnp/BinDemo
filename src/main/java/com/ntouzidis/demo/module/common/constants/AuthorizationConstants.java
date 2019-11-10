package com.ntouzidis.demo.module.common.constants;

import static com.ntouzidis.demo.module.common.constants.RoleConstants.*;

public class AuthorizationConstants {

  private static final String HAS_AUTHORITY = "hasAuthority('";

  public static final String IS_AUTHENTICATED = "isAuthenticated()";

  public static final String HAS_ADMIN_AUTHORITY = HAS_AUTHORITY + ROLE_ADMIN + "')";

  private AuthorizationConstants() {
  }
}
