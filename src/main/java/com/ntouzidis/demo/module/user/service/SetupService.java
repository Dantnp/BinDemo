package com.ntouzidis.demo.module.user.service;

import com.ntouzidis.demo.module.auth_client.service.AuthClientService;
import com.ntouzidis.demo.module.user.dto.SetupDetails;
import com.ntouzidis.demo.module.user.entity.Tenant;
import com.ntouzidis.demo.module.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetupService {

  @Value("${root.setup.username}")
  private String adminUsername;

  @Value("${root.setup.mail}")
  private String adminEmail;

  @Value("${root.setup.pass}")
  private String adminPass;

  private final IUserService userService;
  private final TenantService tenantService;
  private final AuthClientService authClientService;

  public SetupService(
      IUserService userService,
      TenantService tenantService,
      AuthClientService authClientService
  ) {
    this.userService = userService;
    this.tenantService = tenantService;
    this.authClientService = authClientService;
  }

  @Transactional
  public SetupDetails initialSetup() {
    ClientDetails clientDetails = authClientService.createInitialClient();
    Tenant tenant = tenantService.create(AuthClientService.TEST);
    User user = userService.createAdmin(tenant, adminUsername, adminEmail, adminPass);

    SetupDetails setupDetails = new SetupDetails();
    setupDetails.setClientDetails(clientDetails);
    setupDetails.setTenant(tenant);
    setupDetails.setUser(user);

    return setupDetails;
  }
}
