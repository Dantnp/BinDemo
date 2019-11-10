package com.ntouzidis.demo.module.common.constants;

import static com.ntouzidis.demo.module.common.constants.ParamConstants.ID_PARAM;

public final class ControllerPathConstants {

  public static final String ID_PATH = "/{" + ID_PARAM + "}";

  public static final String ADMIN_CONTROLLER_PATH = "/api/v1/admin";

  public static final String MEMORY_CONTROLLER_PATH = "/api/v1/memory";

  public static final String PERSON_CONTROLLER_PATH = "/api/v1/person";

  public static final String ROOT_CONTROLLER_PATH = "/api/v1/root";

  public static final String SETUP_PATH = "/setup";

  public static final String TAG_PATH = "/api/v1/tag";
  public static final String TENANT_CONTROLLER_PATH = "/api/v1/tenant";

  public static final String USER_CONTROLLER_PATH = "/api/v1/user";

  private ControllerPathConstants() {
  }
}
